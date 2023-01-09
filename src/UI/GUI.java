package UI;

import database.DatabaseConnectionHandler;
import model.Item;
import model.Order;
import model.ShippingOffice;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.Formatter;
import java.util.List;
import java.util.Map;

public class GUI {
    private static JFrame frame;
    private JTextArea textArea;
    private static Font titleFont = new Font("Dialog", Font.BOLD, 48);
    private JFormattedTextField insertOrder, insertStart, insertEnd, insertAccount, deleteAccount, updateItem, updateOrder, selectOrder, joinOrder;
    private NumberFormatter numberFormatter;
    private MaskFormatter dateFormatter;
    private JCheckBox projectOrder;
    private DatabaseConnectionHandler dch;

    public GUI(DatabaseConnectionHandler dch) {
        this.dch = dch;
        numberFormatter = new NumberFormatter(NumberFormat.getInstance());
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setMinimum(0);
        numberFormatter.setMaximum(Integer.MAX_VALUE);
        numberFormatter.setAllowsInvalid(false);
        dateFormatter = new MaskFormatter();
        try{dateFormatter.setMask("####-##-##");} catch (Exception e) {}
        frame = new JFrame("SML Shipping");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(1000, 750);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(createPanel());
        frame.setVisible(true);
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.insets = new Insets(40, 40, 0, 40);
        JLabel label = new JLabel("Operations");
        label.setFont(titleFont);
        panel.add(label, c);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridy = 2;
        label = new JLabel("Queries");
        label.setFont(titleFont);
        panel.add(label, c);
        c.gridy = 4;
        label = new JLabel("Output");
        label.setFont(titleFont);
        panel.add(label, c);
        c.insets = new Insets(10, 40, 15, 40);
        c.gridy = 1;
        panel.add(createOperationsPanel(), c);
        c.gridy = 3;
        panel.add(createQueriesPanel(), c);
        c.gridy = 5;
        c.insets.bottom = 40;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        panel.add(createOutputPanel(), c);
        return panel;
    }

    private JPanel createOperationsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 20, 0));

        JPanel insert = createInsertPanel();
        JPanel delete = createDeletePanel();
        JPanel update = createUpdatePanel();

        int height = Math.max(Math.max(insert.getHeight(), delete.getHeight()), update.getHeight());
        int width = Math.max(Math.max(insert.getWidth(), delete.getWidth()), update.getWidth());
        insert.setSize(width, height);
        delete.setSize(width, height);
        update.setSize(width, height);

        panel.add(insert);
        panel.add(delete);
        panel.add(update);

        return panel;
    }

    private JPanel createUpdatePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 5, 0);

        JLabel label = new JLabel("Add an item to an order.");
        panel.add(label, c);

        c.gridy = 1;
        panel.add(createUpdateSubPanel(), c);

        JButton button = new JButton("Update");
        button.setSize(200, 150);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(handleUpdate());
            }
        });
        c.gridy = 2;
        panel.add(button, c);

        return panel;
    }

    private JPanel createUpdateSubPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel label = new JLabel("Item ID: ");
        panel.add(label, c);

        c.gridx = 2;
        label = new JLabel("Order ID: ");
        panel.add(label, c);

        updateItem = createFTF(numberFormatter);
        c.gridx = 1;
        c.insets = new Insets(0, 0, 0, 20);
        panel.add(updateItem, c);

        c.gridx = 3;
        updateOrder = createFTF(numberFormatter);
        panel.add(updateOrder, c);

        return panel;
    }

    private JPanel createDeletePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 5, 0);

        JLabel label = new JLabel("Delete an account. All of the account's orders will be deleted.");
        panel.add(label, c);

        c.gridy = 1;
        deleteAccount = createFTF(null);
        panel.add(createSingleFieldNumericSubPanel("Account ID: ", deleteAccount), c);

        JButton button = new JButton("Delete");
        button.setSize(200, 150);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(handleDelete());
            }
        });
        c.gridy = 2;
        panel.add(button, c);

        return panel;
    }

    private JPanel createSingleFieldNumericSubPanel(String desc, JFormattedTextField field) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel label = new JLabel(desc);
        panel.add(label, c);

        field.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));
        c.gridx = 1;
        panel.add(field, c);

        return panel;
    }

    private JPanel createInsertPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 5, 0);

        JLabel label = new JLabel("<html><center>Create a new Order, dates can be null.<br>(Date format: YYYY-MM-DD; 9999-99-99 for null).</center></html>");
        panel.add(label, c);

        c.gridy = 1;
        panel.add(createInsertSubPanel(), c);

        JButton button = new JButton("Insert");
        button.setSize(200, 150);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(handleInsert());
            }
        });
        c.gridy = 2;
        panel.add(button, c);

        return panel;
    }

    private JPanel createInsertSubPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 5, 0);

        JLabel label = new JLabel("Order ID: ");
        panel.add(label, c);

        c.gridx = 2;
        label = new JLabel("Account ID: ");
        panel.add(label, c);

        c.gridy = 1;
        label = new JLabel("Completion Date: ");
        panel.add(label, c);

        c.gridx = 0;
        label = new JLabel("Start Date: ");
        panel.add(label, c);

        insertStart = createFTF(dateFormatter);
        c.gridx = 1;
        c.insets = new Insets(0, 0, 0, 20);
        panel.add(insertStart, c);

        c.gridx = 3;
        insertEnd = createFTF(dateFormatter);
        panel.add(insertEnd, c);

        c.gridy = 0;
        c.gridx = 1;
        insertOrder = createFTF(numberFormatter);
        panel.add(insertOrder, c);

        c.gridx = 3;
        insertAccount = createFTF(numberFormatter);
        panel.add(insertAccount, c);

        return panel;
    }

    private JPanel createQueriesPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 6, 20, 0));
        panel.add(createSelectPanel());
        panel.add(createProjectPanel());
        panel.add(createJoinPanel());
        panel.add(createAggregatePanel());
        panel.add(createGroupPanel());
        panel.add(createDividePanel());
        return panel;
    }

    private JPanel createSelectPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 5, 0);

        JLabel label = new JLabel("<html><center>Returns the item ids of all items<br>included in the given order id.</center></html>");
        panel.add(label, c);

        c.gridy = 1;
        selectOrder = createFTF(null);
        panel.add(createSingleFieldNumericSubPanel("Order ID: ", selectOrder), c);

        JButton button = new JButton("Select");
        button.setSize(200, 150);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(handleSelect());
            }
        });
        c.gridy = 2;
        panel.add(button, c);

        return panel;
    }

    private JPanel createProjectPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 5, 0);

        JLabel label = new JLabel("Returns information about each order.");
        panel.add(label, c);

        c.gridy = 1;
        projectOrder = new JCheckBox("Only return ID");
        panel.add(projectOrder, c);

        JButton button = new JButton("Project");
        button.setSize(200, 150);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(handleProject());
            }
        });
        c.gridy = 2;
        panel.add(button, c);

        return panel;
    }

    private JPanel createJoinPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 5, 0);

        JLabel label = new JLabel("<html><center>Returns the delivery destination<br>of the given order id.</center></html>");
        panel.add(label, c);

        c.gridy = 1;
        joinOrder = createFTF(null);
        panel.add(createSingleFieldNumericSubPanel("Order ID: ", joinOrder), c);

        JButton button = new JButton("Join");
        button.setSize(200, 150);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(handleJoin());
            }
        });
        c.gridy = 2;
        panel.add(button, c);

        return panel;
    }

    private JPanel createAggregatePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 5, 0);

        JLabel label = new JLabel("Returns the average total price of all orders.");
        panel.add(label, c);

        JButton button = new JButton("Aggregate");
        button.setSize(200, 150);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(handleAggregate());
            }
        });
        c.gridy = 1;
        panel.add(button, c);

        return panel;
    }

    private JPanel createGroupPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 5, 0);

        JLabel label = new JLabel("<html><center>Returns the sum of prices of<br>all orders for each account.</center></html>");
        panel.add(label, c);

        JButton button = new JButton("Group By");
        button.setSize(200, 150);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(handleGroup());
            }
        });
        c.gridy = 1;
        panel.add(button, c);

        return panel;
    }

    private JPanel createDividePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 5, 0);

        JLabel label = new JLabel("<html><center>Returns all shipping offices that ship<br>to all other shipping offices.</center></html>");
        panel.add(label, c);

        JButton button = new JButton("Divide");
        button.setSize(200, 150);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(handleDivide());
            }
        });
        c.gridy = 1;
        panel.add(button, c);

        return panel;
    }

    private JFormattedTextField createFTF(JFormattedTextField.AbstractFormatter formatter) {
        JFormattedTextField ftf = new JFormattedTextField();
        if (formatter != null) {
            try {ftf.setFormatterFactory(new DefaultFormatterFactory(formatter));} catch (Exception e) {}
        }
        ftf.setColumns(6);
        ftf.setMinimumSize(ftf.getPreferredSize());
        return ftf;
    }

    private JScrollPane createOutputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, 0);
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        panel.add(textArea, c);
        JScrollPane scroll = new JScrollPane(panel);
        return scroll;
    }

    private String handleInsert() {
        String orderID = insertOrder.getText();
        String startDate = insertStart.getText();
        String endDate = insertEnd.getText();
        String accountID = insertAccount.getText();
        if (orderID.equals("") || !startDate.matches("\\d{4}-\\d{2}-\\d{2}") || !endDate.matches("\\d{4}-\\d{2}-\\d{2}") || accountID.equals("")) {
            return "All insert fields must be specified.";
        }
        if (startDate.equals("9999-99-99")) {
            startDate = null;
        }
        if (endDate.equals("9999-99-99")) {
            endDate = null;
        }
        try {
            dch.insertOrder(Integer.parseInt(orderID), startDate, endDate, Integer.parseInt(accountID));
            insertOrder.setText("");
            insertStart.setText("");
            insertEnd.setText("");
            insertAccount.setText("");
            return "Order " + orderID + " inserted successfully.";
        } catch (Exception e) {
            System.out.println(e.toString());
            return "Insert input error. Check that Order ID is unique, dates are valid and Account ID exists.";
        }
    }

    private String handleDelete() {
        String accountID = deleteAccount.getText();
        if (accountID.equals("")) {
            return "All delete fields must be specified.";
        }
        try {
            dch.deleteAccount(Integer.parseInt(accountID));
            deleteAccount.setText("");
            return "Account " + accountID + " successfully deleted.";
        } catch (Exception e) {
            System.out.println(e.toString());
            return "Delete input error. Check that Account ID exists.";
        }
    }

    private String handleUpdate() {
        String itemID = updateItem.getText();
        String orderID = updateOrder.getText();
        if (itemID.equals("") || orderID.equals("")) {
            return "All update fields must be specified.";
        }
        try {
            dch.updateOrderItem(Integer.parseInt(orderID), Integer.parseInt(itemID));
            updateItem.setText("");
            updateOrder.setText("");
            return "Item " + itemID + " successfully added to Order " + orderID + ".";
        } catch (Exception e) {
            System.out.println(e.toString());
            return "Update input error. Check that Order ID and Account ID exist.";
        }
    }

    private String handleSelect() {
        String orderID = selectOrder.getText();
        if (orderID.equals("")) {
            return "All select fields must be specified.";
        }
        try {
            List<Item> items = dch.listAllItem(Integer.parseInt(orderID));
            if (items.isEmpty()) {
                return "No Items for given Order ID. Order " + orderID + " either has no Items, or does not exist.";
            }
            String ret = "";
            for (Item i: items) {
                ret += "Order ID: " + i.getOrderID()
                    + "    Item ID: " + i.getItemID()
                    + "    Item Type: " + i.getItemType()
                    + "    Storage ID: " + i.getStorageID()
                    + "    Warehouse ID: " + i.getWarehouseID()
                    + "    Warehouse Owner: " + i.getWarehouseOwner() + "\n";
            }
            selectOrder.setText("");
            return ret;
        } catch (Exception e) {
            System.out.println(e.toString());
            return "Select query error.";
        }
    }

    private String handleProject() {
        boolean onlyID = projectOrder.isSelected();
        try {
            List<Order> orders = dch.getOrdersAttributes(onlyID);
            if (orders.isEmpty()) {
                return "There are no Orders in the database.";
            }
            String ret = "";
            for (Order o: orders) {
                ret += "Order ID: " + o.getOrderId();
                if (!onlyID) {
                    ret += "    Total Price: " + o.getTotalPrice()
                        + "    Start Date: " + o.getStartDate()
                        + "    Completion Date: " + o.getEndDate()
                        + "    Account ID: " + o.getAccountId();
                }
                ret +="\n";
            }
            return ret;
        } catch (Exception e) {
            System.out.println(e.toString());
            return "Project query error.";
        }
    }

    private String handleJoin() {
        String orderID = joinOrder.getText();
        if (orderID.equals("")) {
            return "All join fields must be specified.";
        }
        try {
            String ret = dch.getOrderDestination(Integer.parseInt(orderID));
            if (ret.equals("")) {
                return "Delivery address was not found. Either Order " + orderID + " does not exist, or its address is undefined.";
            }
            joinOrder.setText("");
            return ret;
        } catch (Exception e) {
            System.out.println(e.toString());
            return "Join query error.";
        }
    }

    private String handleAggregate() {
        try {
            int avg = dch.getAverageTotalPrice();
            if (avg == -1) {
                return "There are no Orders in the database.";
            }
            return "Average Order Total Price: " + avg;
        } catch (Exception e) {
            System.out.println(e.toString());
            return "Aggregate query error.";
        }
    }

    private String handleGroup() {
        try {
            Map<Integer, Float> accountVals = dch.TotalPriceForAccounts();
            if (accountVals.isEmpty()) {
                return "There are no Accounts in the database.";
            }
            String ret = "";
            for (Integer i: accountVals.keySet()) {
                ret += "Account ID: " + i;
                ret += "    Sum of all Order total prices: " + accountVals.get(i) + "\n";
            }
            return ret;
        } catch (Exception e) {
            System.out.println(e.toString());
            return "Group By query error.";
        }
    }

    private String handleDivide() {
        try {
            List<ShippingOffice> offices = dch.getBestShippingOffices();
            if (offices.isEmpty()) {
                return "There are no Shipping Offices that ship to all other Shipping Offices in the database.";
            }
            String ret = "";
            for (ShippingOffice o: offices) {
                ret += "Office ID: " + o.getOfficeID()
                        + "    Location ID: " + o.getLocationID()
                        + "    Storage ID: " + o.getStorageID() + "\n";
            }
            return ret;
        } catch (Exception e) {
            System.out.println(e.toString());
            return "Divide query error.";
        }
    }
}
