package database;
// notes: this class has been inspired by https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
//        by the CPSC 304 team at UBC and Jessica Wong


import java.sql.*;
import java.sql.Date;
import java.util.*;

import model.Item;
import model.Order;
import model.ShippingOffice;
import util.PrintablePreparedStatement;


public class DatabaseConnectionHandler {

    private static final String url = "";
    private static String dbUser = "";
    private static String dbPassword = "";
    Connection c;

    // constructor
    public DatabaseConnectionHandler() {
        try {
            // register the driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            // create db connection
            c = DriverManager.getConnection(url, dbUser, dbPassword);

            System.out.println("Database is connected!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // REQUIRES: startDate and endDate to be in the format "YYYY-MM-DD"
    //           eg. startDate = "2022-04-01", endDate = "2022-04-02"
    public void insertOrder(int orderID, String startDate, String endDate, int accountID) throws SQLException {
        java.sql.Date sqlStart = null, sqlEnd = null;
        if (startDate != null) {
            sqlStart = Date.valueOf(startDate);
        }
        if (endDate != null) {
            sqlEnd = Date.valueOf(endDate);
        }

        java.sql.Date sqlDate = new java.sql.Date(2001,04,10);


        String query = "INSERT INTO `order` VALUES (?, ?, ?, ?, ?);";

        PrintablePreparedStatement ps = new PrintablePreparedStatement(c.prepareStatement(query), query, false);

        ps.setInt(1, orderID);
        ps.setInt(2, 0);
        if (sqlStart == null) {
            ps.setNull(3, Types.NULL);
        } else {
            ps.setDate(3, sqlStart);
        }
        if (sqlEnd == null) {
            ps.setNull(4, Types.NULL);
        } else {
            ps.setDate(4, sqlEnd);
        }
        ps.setInt(5, accountID);

        ps.executeUpdate();
        //c.commit();

    }

    // deletes account given by usr_accountID
    public void deleteAccount(int usr_accountID) throws SQLException {
        String query = "DELETE from Account where accountID = ?;";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(c.prepareStatement(query), query, false);

        ps.setInt(1, usr_accountID);

        ps.executeUpdate();
        //c.commit();
    }

    // adds an Item to an Order
    // MODIFIES: Item, Order
    public void updateOrderItem(int usr_orderID, int usr_itemID) throws SQLException {
        String query = "select OrderID from Item where ItemID = ?;";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(c.prepareStatement(query), query, false);

        ps.setInt(1, usr_itemID);

        ResultSet rs = ps.executeQuery();
        int prevOrderID = -1;
        while (rs.next()) {
            prevOrderID = rs.getInt(1);
        }

        String opperation = "UPDATE Item SET OrderID = ? WHERE ItemID = ?;";
        ps = new PrintablePreparedStatement(c.prepareStatement(opperation), opperation, false);

        ps.setInt(1, usr_orderID);
        ps.setInt(2, usr_itemID);

        ps.executeUpdate();
        //c.commit();

        int itemPrice = getItemPrice(usr_itemID);

        if (prevOrderID != -1) {
            addPriceToOrder(prevOrderID, itemPrice * (-1));
        }
        addPriceToOrder(usr_orderID, itemPrice);
    }

    // given itemID, produce price of the item
    private int getItemPrice(int itemID) throws SQLException {
        String query = "SELECT ItemType from Item WHERE ItemID = ?;";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(c.prepareStatement(query), query, false);

        ps.setInt(1, itemID);

        String itemType = "";

        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            itemType = resultSet.getString(1);
        }


        int retVal = getItemTypePrice(itemType);


        resultSet.close();
        ps.close();

        return retVal;
    }

    // given ItemType, produce price of the item type
    private int getItemTypePrice(String itemType) throws SQLException {
        String query = "SELECT Price from ItemType WHERE ItemType = ?;";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(c.prepareStatement(query), query, false);

        ps.setString(1, itemType);

        ResultSet resultSet = ps.executeQuery();

        int retVal = 0;

        while (resultSet.next()) {
            retVal = resultSet.getInt(1);
        }



        resultSet.close();
        ps.close();

        return retVal;
    }


    // updates Order to include an additional price, itemPrice
    private void addPriceToOrder(int orderID, int itemPrice) throws SQLException {
        int newTotalPrice = getTotalPrice(orderID) + itemPrice; // stub

        String query = "UPDATE `order` SET TotalPrice = ? WHERE OrderID = ?;";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(c.prepareStatement(query), query, false);

        ps.setInt(1, newTotalPrice);
        ps.setInt(2, orderID);

        ps.executeUpdate();
        //c.commit();
    }

    // produces the total price (cost) of an Order
    private int getTotalPrice(int orderID) throws SQLException {
        String query = "SELECT TotalPrice from `order` WHERE OrderID = ?;";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(c.prepareStatement(query), query, false);

        ps.setInt(1, orderID);

        int retVal = 0;

        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            retVal = resultSet.getInt(1);
        }

        resultSet.close();
        ps.close();

        return retVal;
    }

    // Find all items in a given order specified by orderID
    public List<Item> listAllItem(int orderID) throws SQLException {
        ArrayList<Item> listOfItem = new ArrayList<Item>();

        String query = "SELECT * from Item where OrderID = ?";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(c.prepareStatement(query), query, false);

        ps.setInt(1, orderID);

        ResultSet resultSet = ps.executeQuery();

        while(resultSet.next()) {
            Item item = new Item(resultSet.getInt("ItemID"),
                    resultSet.getInt("StorageID"),
                    resultSet.getInt("WarehouseID"),
                    resultSet.getString("WarehouseOwner"),
                    resultSet.getInt("OrderID"),
                    resultSet.getString("ItemType"));

            listOfItem.add(item);
        }

        resultSet.close();
        ps.close();

        return listOfItem;
    }

    public List<Order> getOrdersAttributes(boolean onlyID) throws SQLException {
        String query = onlyID ? "select OrderID from slm.Order;" : "select * from slm.Order;";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(c.prepareStatement(query), query, false);
        ResultSet rs = ps.executeQuery();
        ArrayList<Order> orders = new ArrayList<>();
        while (rs.next()) {
            if (onlyID) {
                orders.add(new Order(rs.getInt("OrderID")));
            } else {
                int orderID = rs.getInt("OrderID");
                double totalPrice = rs.getDouble("TotalPrice");
                Date startDate = rs.getDate("StartDate");
                Date endDate = rs.getDate("CompletionDate");
                int accountID = rs.getInt("AccountID");
                orders.add(new Order(orderID, totalPrice, startDate, endDate, accountID));
            }
        }
        rs.close();
        ps.close();
        return orders;
    }

    // produces order destination in the format:
    // "1234: Fake County, Fake Region, Fake City, Fake Address"
    public String getOrderDestination(int orderID) throws SQLException {
        String query = "select O.OrderID, L.Country, L.Region, L.City, L.Address " +
                "from `Order` O, Account A, Receiver R, Location L " +
                "where O.OrderID = ? and O.AccountID = A.AccountID and A.ReceiverID = R.ReceiverID and R.LocationID = L.LocationID;";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(c.prepareStatement(query), query, false);

        ps.setInt(1, orderID);

        ResultSet resultSet = ps.executeQuery();

        String retVal = "";

        while (resultSet.next()) {
            retVal = "Order ID: " + orderID + "    " + "Delivery Address: " +  // orderID
                    resultSet.getString(2) + ", " + // Country
                    resultSet.getString(3) + ", " + // Region
                    resultSet.getString(4) + ", " + // City
                    resultSet.getString(5); // Address
        }


        resultSet.close();
        ps.close();

        return retVal;

    }

    public int getAverageTotalPrice() throws SQLException {
        String query = "select avg(TotalPrice) from slm.Order;";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(c.prepareStatement(query), query, false);
        ResultSet rs = ps.executeQuery();
        int avg = -1;
        while (rs.next()) {
            avg = rs.getInt(1);
        }
        rs.close();
        ps.close();
        return avg;
    }

    public Map<Integer, Float> TotalPriceForAccounts() throws SQLException {

        Map<Integer, Float> retVal = new HashMap();

        String query = "SELECT SUM(TotalPrice), AccountID FROM slm.Order GROUP BY AccountID";

        PrintablePreparedStatement ps = new PrintablePreparedStatement(c.prepareStatement(query), query, false);

        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            retVal.put(resultSet.getInt(2), resultSet.getFloat(1));
        }

        System.out.println(retVal);

        return retVal;
    }

    // finds shipping offices that ship to all other shipping offices
    public List<ShippingOffice> getBestShippingOffices() throws SQLException {

        String query = "select * from ShippingOffice S where not exists (" +
                " select OfficeID from ShippingOffice S1 where S.OfficeID <> S1.OfficeID and not exists (" +
                " select R.DestOfficeID" +
                " from ShippingRoute R " +
                " where S.OfficeID = R.SourceOfficeID and S1.OfficeID = R.DestOfficeID));";

        PrintablePreparedStatement ps = new PrintablePreparedStatement(c.prepareStatement(query), query, false);

        ResultSet resultSet = ps.executeQuery();

        List<ShippingOffice> retVal = new ArrayList<>();

        while (resultSet.next()) {
            int officeID = resultSet.getInt("OfficeID");
            int locationID = resultSet.getInt("LocationID");
            int storageID = resultSet.getInt("StorageID");

            retVal.add(new ShippingOffice(officeID, locationID, storageID));
        }

        resultSet.close();
        ps.close();

        return retVal;

    }





}