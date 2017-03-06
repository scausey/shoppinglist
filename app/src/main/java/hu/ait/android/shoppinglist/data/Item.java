package hu.ait.android.shoppinglist.data;

import java.io.Serializable;
import com.orm.SugarRecord;
import hu.ait.android.shoppinglist.R;

public class Item extends SugarRecord implements Serializable{

    public enum ItemType {
        FOOD(0, R.drawable.food_icon),
        TOILETRY(1, R.drawable.toiletry_icon),
        CLOTHING(2, R.drawable.clothing_icon);

        private int value;
        private int iconId;

        private ItemType(int value, int iconId) {
            this.value = value;
            this.iconId = iconId;
        }

        public int getValue() {
            return value;
        }

        public int getIconId() {
            return iconId;
        }

        public static ItemType fromInt(int value) {
            for (ItemType i : ItemType.values()) {
                if (i.value == value) {
                    return i;
                }
            }
            return FOOD;
        }
    }

    private String itemName;
    private String itemPrice;
    private String itemDescription;
    private ItemType itemType;
    private boolean purchased;

    //Empty constructor for SugarDB.
    public Item() {
    }

    public Item(ItemType itemType, String itemName, String itemPrice, String itemDescription, boolean purchased) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
        this.purchased = purchased;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}
