package hu.ait.android.shoppinglist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import hu.ait.android.shoppinglist.adapter.ItemAdapter;
import hu.ait.android.shoppinglist.data.Item;

public class MainActivity extends AppCompatActivity {

    //Getting info from activities.
    public static final int REQUEST_CODE_NEW_ITEM = 101;
    //Editing info.
    public static final int REQUEST_EDIT_ITEM = 102;
    public static final String KEY_EDIT = "KEY_EDIT";

    private ItemAdapter itemRecyclerAdapter = new ItemAdapter(this);

    private Item itemToEditHolder;
    private int itemToEditPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        final ItemAdapter shoppingItemRecyclerAdapter = new ItemAdapter(this);
        final RecyclerView recyclerView =
                (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        //RecyclerView layout manager.
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemRecyclerAdapter);

        //Update balance.
//        TextView tvNumBalance = (TextView) findViewById(R.id.tvNumBalance);
//        tvNumBalance.setText(String.valueOf(itemRecyclerAdapter.addPrices()));
    }

    private void showNewItemActivity() {
        Intent intentStart = new Intent(MainActivity.this,
                NewItemActivity.class);
        startActivityForResult(intentStart, REQUEST_CODE_NEW_ITEM);
    }

    public void showEditItemActivity(Item itemToEdit, int position) {
        Intent intentStart = new Intent(MainActivity.this,
                NewItemActivity.class);
        itemToEditHolder = itemToEdit;
        itemToEditPosition = position;

        intentStart.putExtra(KEY_EDIT, itemToEdit);
        startActivityForResult(intentStart, REQUEST_EDIT_ITEM);
    }

    /*Called when an activity you launched exits, giving you the requestCode you started it with (allows
    you to identify who this result came from), the resultCode it returned (returned by child activity
    through setResult()), and any additional data from it. The resultCode will be RESULT_CANCELED
    if the activity explicitly returned that, didn't return any result, or crashed during this operation.
    */
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == REQUEST_CODE_NEW_ITEM) {
                    Item newItem = (Item) data.getSerializableExtra(
                            NewItemActivity.KEY_NEW_ITEM);

                    itemRecyclerAdapter.addItem(newItem);
                    //Update balance.
                    TextView tvNumBalance = (TextView) findViewById(R.id.tvNumBalance);
                    tvNumBalance.setText(String.valueOf(itemRecyclerAdapter.addPrices()));
                    Toast.makeText(MainActivity.this, "Item added!",
                            Toast.LENGTH_SHORT).show();
                }
                //Statement for editing.
                else if (requestCode == REQUEST_EDIT_ITEM) {
                    Item itemTemp = (Item) data.getSerializableExtra(
                            NewItemActivity.KEY_NEW_ITEM);

                    itemToEditHolder.setItemType(itemTemp.getItemType());
                    itemToEditHolder.setItemName(itemTemp.getItemName());
                    itemToEditHolder.setItemPrice(itemTemp.getItemPrice());
                    itemToEditHolder.setItemDescription(itemTemp.getItemDescription());
                    itemToEditHolder.setPurchased(itemTemp.isPurchased());

                    if (itemToEditPosition != -1) {
                        itemRecyclerAdapter.updateItem(itemToEditPosition, itemToEditHolder);
                        itemToEditPosition = -1;
                    }
                    else {
                        itemRecyclerAdapter.notifyDataSetChanged();
                    }
                    Toast.makeText(MainActivity.this, "Item edited!",
                            Toast.LENGTH_SHORT).show();

                    //Update balance.
                    TextView tvNumBalance = (TextView) findViewById(R.id.tvNumBalance);
                    tvNumBalance.setText(String.valueOf(itemRecyclerAdapter.addPrices()));
                }
                break;
            case RESULT_CANCELED:
                Toast.makeText(MainActivity.this, "Item adding canceled!",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addItem:
                //Navigate to NewItemActivity.
                //Intent objects used to pass parameters to other activities.
                showNewItemActivity();
                break;
            case R.id.action_deleteAll:
                itemRecyclerAdapter.removeAllItems();
                TextView tvNumBalance = (TextView) findViewById(R.id.tvNumBalance);
                tvNumBalance.setText("0");
                break;
            default:
                break;
        }
        return true;
    }
}