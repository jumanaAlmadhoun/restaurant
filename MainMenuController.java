/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author almadhoun
 */
public class MainMenuController implements Initializable {

    @FXML
    private Label numberMeals;
    @FXML
    private Label numberDrinks;
    @FXML
    private Pane meal;
    @FXML
    private Pane Drink;
    @FXML
    private ComboBox<String> typeM;
    @FXML
    private ComboBox<String> typeD;
    @FXML
    private TextField noMeal;
    @FXML
    private TextField nMeal;
    @FXML
    private TextField pMeal;
    @FXML
    private Label DoneM;
    @FXML
    private TableView<Meals> tableM;
    @FXML
    private TableColumn<Meals, Integer> nocolumnM;

    @FXML
    private TableColumn<Meals, String> ncolumnM;

    @FXML
    private TableColumn<Meals, String> tcolumnM;

    @FXML
    private TableColumn<Meals, Double> pcoulmnM;
    @FXML
    private TextField numD;
    @FXML
    private TextField nameD;
    @FXML
    private TextField priceD;
    @FXML
    private Label DoneD;
    @FXML
    private TableView<Drinks> tableD;
    @FXML
    private TableColumn<Drinks, Integer> noColumnD;
    @FXML
    private TableColumn<Drinks, String> ncolumnD;
    @FXML
    private TableColumn<Drinks, String> TcolumnD;
    @FXML
    private TableColumn<Drinks, Double> pcolumnD;

    ObservableList<Meals> listm;
    ObservableList<Drinks> listd;
    int indexM, indexD = -1;

    @FXML
    private TextField searchM;

    @FXML
    private TextField searchD;

    public void entred(Event e) {
        //e.getSource()-> مصدر الحركة 
        //(Button)-> كاستنق للسةرس عشان نحن هنا بنتعامل مع بوتن
        ((Button) e.getSource()).setScaleX(1.1);
        ((Button) e.getSource()).setScaleY(1.1);
        ((Button) e.getSource()).setTextFill(Color.BLUE);

    }

    public void exited(Event e) {
        ((Button) e.getSource()).setScaleX(1);
        ((Button) e.getSource()).setScaleY(1);
        String button = ((Button) e.getSource()).getText();
        switch (button) {
            case "Log out":
                ((Button) e.getSource()).setTextFill(Color.RED);
                break;
            case "Drinks":
            case "Meals":
                ((Button) e.getSource()).setTextFill(Color.BLACK);
                break;
            default:
                ((Button) e.getSource()).setTextFill(Color.WHITE);
        }

    }

    public void Drinks() {
        Drink.setVisible(true);
        meal.setVisible(false);
    }

    public void Meals() {
        Drink.setVisible(false);
        meal.setVisible(true);
    }

    public void InsertMeal() throws ClassNotFoundException, SQLException {
        try {
            int ID = Integer.parseInt(noMeal.getText());
            String name = nMeal.getText();
            String type = typeM.getSelectionModel().getSelectedItem();
            double price = Double.parseDouble(pMeal.getText());
            if (name == null) {
                return;
            }

            if (database.Insert("restaurant.meals", ID, name, type, price)) {
                DoneM.setText("Added successfully!");
                tableM.setItems(database.getMeals());
                DoneM.setVisible(true);
                numberMeals.setText((Integer.parseInt(numberMeals.getText()) + 1) + "");
                clearM();
            }

        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {

        }

    }

    public void InsertDrink() throws ClassNotFoundException, SQLException {
        try {
            int ID = Integer.parseInt(numD.getText());
            String name = nameD.getText();
            String type = typeD.getSelectionModel().getSelectedItem();
            double price = Double.parseDouble(priceD.getText());

            if (database.Insert("restaurant.drinks", ID, name, type, price)) {
                tableD.setItems(database.getDrinks());
                DoneD.setText("Added successfully!");
                DoneD.setVisible(true);
                numberDrinks.setText((Integer.parseInt(numberDrinks.getText()) + 1) + "");
                clearD();
            }

        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {

        }

    }

    public void getSelectedMeals() {
        //to get the number of row that we select in the table 
        indexM = tableM.getSelectionModel().getFocusedIndex();
        if (indexM < 0) {
            return;
        }
        noMeal.setText(nocolumnM.getCellData(indexM).toString());
        nMeal.setText(ncolumnM.getCellData(indexM));
        typeM.getSelectionModel().select(tcolumnM.getCellData(indexM));
        pMeal.setText(pcoulmnM.getCellData(indexM) + "");
    }

    public void updateMeals() throws ClassNotFoundException, SQLException {
        try {
            int ID = Integer.parseInt(noMeal.getText());
            String name = nMeal.getText();
            String type = typeM.getSelectionModel().getSelectedItem();
            double price = Double.parseDouble(pMeal.getText());

            if (database.update("restaurant.meals", " where mealID =" + ID, name, type, price)) {
                DoneM.setText("Updated successfully!");
                tableM.setItems(database.getMeals());
                clearM();
            }

        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {

        }

    }

    public void updatDrinks() throws ClassNotFoundException, SQLException {
        try {
            int ID = Integer.parseInt(numD.getText());
            String name = nameD.getText();
            String type = typeD.getSelectionModel().getSelectedItem();
            double price = Double.parseDouble(priceD.getText());
            if (name == null) {
                return;
            }
            if (database.update("restaurant.drinks", " where drinkID =" + ID, name, type, price)) {
                DoneD.setText("Updated successfully!");
                tableD.setItems(database.getDrinks());
                clearD();
            }

        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {

        }

    }

    public void clearD() {
        numD.clear();
        nameD.clear();
        typeD.getSelectionModel().select(-1);
        priceD.clear();
    }

    public void clearM() {
        noMeal.clear();
        nMeal.clear();
        typeM.getSelectionModel().select(-1);
        pMeal.clear();
    }

    public void getSelectiomDrinks() {
        indexD = tableD.getSelectionModel().getFocusedIndex();
        //to avoid index error 
        if (indexD < 0) {
            return;
        }
        numD.setText(noColumnD.getCellData(indexD).toString());
        nameD.setText(ncolumnD.getCellData(indexD));
        typeD.getSelectionModel().select(TcolumnD.getCellData(indexD));
        priceD.setText(pcolumnD.getCellData(indexD) + "");
    }

    public void deleteMeals() {
        try {
            //to get the number of row that we select in the table 
            indexM = tableM.getSelectionModel().getFocusedIndex();
            if (indexM < 0) {
                return;
            }
            int ID = nocolumnM.getCellData(indexM);
            if (database.delete("restaurant.meals", ID)) {
                DoneM.setText("Deleted successfully!");
                tableM.setItems(database.getMeals());
                indexM = -1;
                numberMeals.setText((Integer.parseInt(numberMeals.getText()) - 1) + "");
                clearM();
            }

        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {

        }

    }

    public void deleteDrinks() {
        try {
            //to get the number of row that we select in the table 
            indexD = tableD.getSelectionModel().getFocusedIndex();
            if (indexD < 0) {
                return;
            }
            int ID = noColumnD.getCellData(indexD);
            if (database.delete("restaurant.drinks", ID)) {
                DoneD.setText("Deleted successfully!");
                tableD.setItems(database.getDrinks());
                indexD = -1;
                numberDrinks.setText((Integer.parseInt(numberDrinks.getText()) - 1) + "");
                clearD();
            }

        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {

        }

    }

    public void searchMelas() {
        searchM.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observale) {
                if (searchM.textProperty().get().isEmpty()) {
                    tableM.setItems(listm);
                    return;
                }
                ObservableList<Meals> itemes = FXCollections.observableArrayList();
                ObservableList<TableColumn<Meals, ?>> column = tableM.getColumns();
                for (int row = 0; row < listm.size(); row++) {
                    for (int col = 0; col < column.size(); col++) {
                        TableColumn colVar = column.get(col);
                        String cellValue = colVar.getCellData(listm.get(row)).toString();
                        cellValue = cellValue.toLowerCase();
                        if (cellValue.contains(searchM.getText().toLowerCase()) && cellValue.startsWith(searchM.getText().toLowerCase())) {
                            itemes.add(listm.get(row));
                            break;

                        }
                    }
                }
                tableM.setItems(itemes);
            }
        });
    }

    public void searchDrinks() {
        searchD.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observale) {
                if (searchD.textProperty().get().isEmpty()) {
                    tableD.setItems(listd);
                    return;
                }
                ObservableList<Drinks> itemes = FXCollections.observableArrayList();
                ObservableList<TableColumn<Drinks, ?>> column = tableD.getColumns();
                for (int row = 0; row < listd.size(); row++) {
                    for (int col = 0; col < column.size(); col++) {
                        TableColumn colVar = column.get(col);
                        String cellValue = colVar.getCellData(listd.get(row)).toString();
                        cellValue = cellValue.toLowerCase();
                        if (cellValue.contains(searchD.getText().toLowerCase()) && cellValue.startsWith(searchD.getText().toLowerCase())) {
                            itemes.add(listd.get(row));
                            break;

                        }
                    }
                }
                tableD.setItems(itemes);
            }
        });

    }

    public void clearALLMeals() throws ClassNotFoundException, SQLException {
        if (database.clear("restaurant.meals")) {
            DoneM.setText("Deleted successfully!");
            numberMeals.setText("0");
            tableM.setItems(database.getMeals());
        }
    }

    public void clearALLDrinks() throws ClassNotFoundException, SQLException {
        if (database.clear("restaurant.drinks")) {
            DoneD.setText("Deleted successfully!");
            numberDrinks.setText("0");
            tableD.setItems(database.getDrinks());
        }
    }

    public void logOut(Event e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("sign in");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nocolumnM.setCellValueFactory(new PropertyValueFactory<>("id"));
        ncolumnM.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcolumnM.setCellValueFactory(new PropertyValueFactory<>("type"));
        pcoulmnM.setCellValueFactory(new PropertyValueFactory<>("price"));

        noColumnD.setCellValueFactory(new PropertyValueFactory<>("id"));
        ncolumnD.setCellValueFactory(new PropertyValueFactory<>("name"));
        TcolumnD.setCellValueFactory(new PropertyValueFactory<>("type"));
        pcolumnD.setCellValueFactory(new PropertyValueFactory<>("price"));

        try {
            listm = database.getMeals();
            listd = database.getDrinks();
            tableM.setItems(listm);
            tableD.setItems(listd);
            numberDrinks.setText(database.count("drinkID", "restaurant.drinks") + "");

            numberMeals.setText(database.count("mealID", "restaurant.meals") + "");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList listM = FXCollections.observableArrayList("Breakfast", "Main dishes", "Side orders", "Desserts");
        typeM.setItems(listM);
        ObservableList listD = FXCollections.observableArrayList("Cold", "Hot");
        typeD.setItems(listD);
    }

}
