package hu.ait.android.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import hu.ait.android.shoppinglist.data.Item;

/**
 * Created by samanthacausey on 4/12/16.
 */
public class NewItemActivity extends AppCompatActivity {

    //From ActivityParameterDemo
    public static final String KEY_NEW_ITEM = "KEY_NEW_ITEM";
    private EditText etItemName;
    private EditText etEstimatedPrice;
    private Spinner spinItemType;
    private EditText etItemDescription;
    private CheckBox cbPurchased;
    private Item itemToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item_activity);

        if (getIntent().getSerializableExtra(MainActivity.KEY_EDIT) != null) {
            itemToEdit = (Item) getIntent().getSerializableExtra(MainActivity.KEY_EDIT);
        }

        spinItemType = (Spinner) findViewById(R.id.spinItemType);
        //Choose an item type.
        //Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinItemTypeArray, android.R.layout.simple_spinner_item);
        //Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the spinner.
        spinItemType.setAdapter(adapter);

        etItemName = (EditText) findViewById(R.id.etItemName);
        etEstimatedPrice = (EditText) findViewById(R.id.etEstimatedPrice);
        etItemDescription = (EditText) findViewById(R.id.etItemDescription);
        cbPurchased = (CheckBox) findViewById(R.id.cbPurchased);

        //Pass activity parameters to MainActivity.
        Button btnAddItem = (Button) findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });

        if (itemToEdit != null) {
            etItemName.setText(itemToEdit.getItemName());
            etEstimatedPrice.setText(itemToEdit.getItemPrice());
            etItemDescription.setText(itemToEdit.getItemDescription());
            cbPurchased.setChecked(itemToEdit.isPurchased());
            spinItemType.setSelection(itemToEdit.getItemType().getValue());
        }

        //Set up toolbar.
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mainToolbar);
    }

    private void saveItem() {
        Intent intentResult = new Intent();
        Item itemResult = null;
        if (itemToEdit != null) {
            itemResult = itemToEdit;
        }
        //There is else code here in PlacesToVisit but I don't think I need it.
        else {
            itemResult = new Item();
        }

        itemResult.setItemName(etItemName.getText().toString());
        itemResult.setItemPrice(etEstimatedPrice.getText().toString());
        itemResult.setItemDescription(etItemDescription.getText().toString());
        itemResult.setItemType(
                Item.ItemType.fromInt(spinItemType.getSelectedItemPosition()));
        itemResult.setPurchased(cbPurchased.isChecked());

        intentResult.putExtra(KEY_NEW_ITEM, itemResult);
        setResult(RESULT_OK, intentResult);
        finish();
    }
}
