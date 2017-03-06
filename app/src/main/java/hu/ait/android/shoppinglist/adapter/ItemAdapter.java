package hu.ait.android.shoppinglist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.shoppinglist.MainActivity;
import hu.ait.android.shoppinglist.R;
import hu.ait.android.shoppinglist.data.Item;

/**
 * Adapter: A subclass of RecyclerView.Adapter responsible for providing views that represent items
 * in a data set.
 */

public class ItemAdapter
        extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    /* A ViewHolder describes an item view and metadata about its place within the RecyclerView.
    ViewHolders belong to the adapter. Adapters should feel free to use their own custom ViewHolder
    implementations to store data that makes binding view contents easier. Implementations should
    assume that individual item views will hold strong references to ViewHolder objects and that
    RecyclerView instances may hold strong references to extra off-screen item views for caching
    purposes.
    */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Parameters: Everything that is part of each shopping list item.
        public ImageView imgItemType;
        public TextView tvItemName;
        public TextView tvEstimatedPrice;
        public CheckBox cbPurchased;
        public Button btnDeleteItem;
        public Button btnEditItem;

        public ViewHolder(View itemView) {
            super(itemView);

            imgItemType = (ImageView) itemView.findViewById(R.id.imgItemType);
            tvEstimatedPrice = (TextView) itemView.findViewById(R.id.tvEstimatedPrice);
            tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);
            cbPurchased = (CheckBox) itemView.findViewById(R.id.cbPurchased);
            btnDeleteItem = (Button) itemView.findViewById(R.id.btnDeleteItem);
            btnEditItem = (Button) itemView.findViewById(R.id.btnEditItem);
        }
    }

    private Context context;
    private List<Item> items = new ArrayList<Item>();

    public ItemAdapter(Context context) {
        this.context = context;
        items = Item.listAll(Item.class);
    }

    /* Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an
     item. This new ViewHolder should be constructed w/ a new View that can represent the items of the
     given type. You can either create a new View manually or inflate it from an XML layout file. The
     new ViewHolder will be used to display items of the adapter using onBindViewHolder(ViewHolder,
     int, List). Since it will be re-used to display different items in the data set, it is a good
     idea to cache references to sub views of the View to avoid unnecessary findViewById(int) calls.
      */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new ViewHolder(rowView);
    }

    /*Called by RecyclerView to display the data at the specified position. Should update the contents
    * of the itemView to reflect the item at the given position. RecyclerView will not call thi method again
    * if the position of the item changes in the data set unless the item itself is invalidated or the new
    * position cannot be determined. For this reason, you should only use the position parameter while
    * acquiring the related data item inside this method and should not keep a copy of it. If you need
    * the position of an item later on (e.g. in a click listener), use getAdapterPosition() which will
    * have the updated adapter position. Override onBindViewHolder(ViewHolder, int, List) instead if
    * Adapter can handle efficient partial bind.*/
    @Override
    public void onBindViewHolder(final ViewHolder holder,
                                 final int position) {
        //UI elements from item_row.xml
        holder.imgItemType.setImageResource(
                items.get(position).getItemType().getIconId());
        holder.tvItemName.setText(items.get(position).getItemName());
        holder.tvEstimatedPrice.setText(items.get(position).getItemPrice());

        holder.btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });

        holder.btnEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).showEditItemActivity(items.get(position), position);
            }
        });

        holder.cbPurchased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = items.get(position);
                item.setPurchased(holder.cbPurchased.isChecked());

                //Save item in SugarDB.
                item.save();

                Toast.makeText(context,
                        item.getItemName() + ": " +
                                item.isPurchased(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Item item) {
        item.save();
        items.add(0, item);
        notifyDataSetChanged();
    }

    public void updateItem(int position, Item item) {
        items.set(position, item);
        item.save();
        notifyItemChanged(position);
    }

    public void removeItem(int position) {
        items.get(position).delete();
        items.remove(position);
        notifyDataSetChanged();
    }

    public void removeAllItems() {
        int size = this.items.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.items.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
        }
    }

    public int addPrices() {
        int size = this.items.size();
        int itemPriceSum = 0;
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                int currentItemPrice = Integer.parseInt(this.items.get(i).getItemPrice());
                itemPriceSum = itemPriceSum + currentItemPrice;
            }
            return itemPriceSum;
        } else {
            return itemPriceSum;
        }
    }
}