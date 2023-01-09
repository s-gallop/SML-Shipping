package model;

public class Item {

    private int itemID;
    private int storageID;
    private int warehouseID;
    private String warehouseOwner;
    private int orderID;
    private String itemType;

    public Item(int itemID, int storageID, int warehouseID, String warehouseOwner, int orderID, String itemType) {
      this.itemID = itemID;
      this.storageID = storageID;
      this.warehouseID = warehouseID;
      this.warehouseOwner = warehouseOwner;
      this.orderID = orderID;
      this.itemType = itemType;
    }

    public int getItemID() {
        return itemID;
    }

    public int getStorageID() {
        return storageID;
    }

    public int getWarehouseID() {
        return warehouseID;
    }

    public String getWarehouseOwner() {
        return warehouseOwner;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getItemType() {
        return itemType;
    }
}
