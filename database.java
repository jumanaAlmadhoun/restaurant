package restaurant;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

public class database {

    public static Connection conect() throws ClassNotFoundException, SQLException {

        //1-load mySql Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2-create connection
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant?useSSL=false", "root", "");
        return con;
    }

    public static int count(String col, String table) throws ClassNotFoundException, SQLException {
        Connection con = conect();
        PreparedStatement ps = con.prepareStatement("SELECT  Count(" + col + ") \n"
                + "FROM  " + table);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Integer.parseInt(rs.getString(1));
        }

        return 0;

    }

    //just to check -not used- 
    public static void select() throws SQLException, ClassNotFoundException {
        Connection con = conect();
        Statement stat = con.createStatement();
        ResultSet rs;
        rs = stat.executeQuery("select * from restaurant.drinks ");
        if (rs != null) {
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getDouble(4));
            }
        }

    }

    public static boolean Insert(String table, int id, String name, String type, double price) throws ClassNotFoundException, SQLException {
        Connection con = conect();

        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO " + table + " VALUES (" + id + ",'" + name + "','" + type + "'," + price + ")");
            //if ps.execute(); == true -> no action so we use !
            return !ps.execute();
        } catch (SQLException e) {
            if (table.equalsIgnoreCase("restaurant.drinks")) {
                JOptionPane.showMessageDialog(null, "The number or name of the drink already exists");
            } else {
                JOptionPane.showMessageDialog(null, "The number or name of the meal already exists");
            }

        }
        return true;
    }

    public static ObservableList<Meals> getMeals() throws ClassNotFoundException, SQLException {
        ObservableList<Meals> list;
        try (Connection con = conect()) {
            list = FXCollections.observableArrayList();
            PreparedStatement ps = con.prepareStatement("select * from restaurant.meals");
            ResultSet rs;
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Meals(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4)));
            }
        }
        return list;
    }

    public static ObservableList<Drinks> getDrinks() throws ClassNotFoundException, SQLException {
        ObservableList<Drinks> list;
        try (Connection con = conect()) {
            list = FXCollections.observableArrayList();
            PreparedStatement ps = con.prepareStatement("select * from restaurant.drinks");
            ResultSet rs;
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Drinks(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4)));
            }
        }
        return list;
    }

    public static boolean update(String table, String where, String name, String type, double price) throws ClassNotFoundException, SQLException {
        try (Connection con = conect()) {
            String sql;
            if (table.equalsIgnoreCase("restaurant.meals")) {
                sql = "update " + table + " set mealName = '" + name + "' ,mealType = '" + type + "' ,mealPrice =" + price + where;
            } else {
                sql = "update " + table + " set drinkName = '" + name + "' ,drinkType = '" + type + "' ,drinkPrice =" + price + where;
            }
            PreparedStatement ps = con.prepareStatement(sql);
            return !ps.execute();

        } catch (SQLException e) {
            if (table.equalsIgnoreCase("restaurant.drinks")) {
                JOptionPane.showMessageDialog(null, "The number or name of the drink already exists");
            } else {
                JOptionPane.showMessageDialog(null, "The number or name of the meal already exists");
            }

        }

        return false;

    }

    public static boolean delete(String table, int id) throws ClassNotFoundException, SQLException {
        try (Connection con = conect()) {
            String sql;
            if (table.equalsIgnoreCase("restaurant.meals")) {
                sql = " DELETE FROM restaurant.meals WHERE mealID=" + id;

            } else {
                sql = " DELETE FROM restaurant.drinks WHERE drinkID=" + id;
            }
            PreparedStatement ps = con.prepareStatement(sql);
            return !ps.execute();
        }
    }

    public static boolean clear(String table) throws ClassNotFoundException, SQLException {
        try (Connection con = conect()) {
            String sql;
            if (table.equalsIgnoreCase("restaurant.meals")) {
                sql = " DELETE FROM restaurant.meals";
            } else {
                sql = " DELETE FROM restaurant.drinks";
            }
            PreparedStatement ps = con.prepareStatement(sql);
            return !ps.execute();
        }

    }

}
