package model;

public class ShippingOffice {

    private int officeID;
    private int locationID;
    private int storageID;

    public ShippingOffice(int officeID, int locationID, int storageID) {
        this.officeID = officeID;
        this.locationID = locationID;
        this.storageID = storageID;
    }

    public int getOfficeID() {
        return officeID;
    }

    public int getLocationID() {
        return locationID;
    }

    public int getStorageID() {
        return storageID;
    }
}
