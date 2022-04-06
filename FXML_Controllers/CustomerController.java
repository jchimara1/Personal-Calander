package FXML_Controllers;

import Database_Utils.*;
import util.*;
import Database_Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * creates the CustomerController class
 * references customer related fmxl IDs
 */
public class CustomerController {


    @FXML public TableView<Customers> TableView_Customers;
    @FXML public TableColumn<Customers, Integer> Customer_idColumn, Customer_DivisionColumn;
    @FXML public TableColumn<Customers, String> Customer_NameColumn, Customer_AddressColumn, Customer_ZipCode, Customer_Phone;
    @FXML public Button Add_Customer, customerUpdateButton, customerResetButton, customerDeleteButton;
    @FXML public TextField customerIDTextField, customerNameTextField, customerAddressTextField, customerPostalCodeTextField, customerPhoneNumberTextField;
    @FXML public ComboBox<String> Country_Selection, Division_Selection;
    private boolean Update_c = false;
    private int ID_Chosen = 0;
    int ButtonChosen = -1;

    /**
     * Initializes all Customer Related FXMLids
     * initializes method to gets the contacts
     * @throws SQLException if unable to initialize
     */
    public void initialize() throws SQLException {
        Customer_idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        Customer_NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        Customer_AddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        Customer_DivisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        Customer_ZipCode.setCellValueFactory(new PropertyValueFactory<>("zipcode"));
        Customer_Phone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        ObservableList<Country> DBCountries = DatabaseCountries.getDBcountries();
        ObservableList<String> ListofCountries = FXCollections.observableArrayList();
        for (Country country : DBCountries) {
            ListofCountries.add(country.getCountry());
        }
        Country_Selection.setItems(ListofCountries);
        Country_Selection.setEditable(true);
        Country_Selection.getEditor().setEditable(false);
        ObservableList<Division> divisions = DatabaseDivisions.getDBDivisions();
        ObservableList<String> ListOfDivisions = FXCollections.observableArrayList();

        // lambda #1
        divisions.forEach(firstLevelDivision -> ListOfDivisions.add(firstLevelDivision.getDivisionName()));
        setCustomerOptionAddAndUpdateButtonActions();

        Division_Selection.setItems(ListOfDivisions);
        Division_Selection.setItems(ListOfDivisions);
        Division_Selection.setEditable(true);
        Division_Selection.getEditor().setEditable(false);
        DisplayCustomerInfo();
    }

    /**
     * Display appointments that appear within Fifthteen mintues of logging in.
     * @throws SQLException if displays appointment violated in anyway
     */
    public void alertAppointments() throws SQLException {
        int currentUserID = 1;
        LocalDateTime loginTime = LocalDateTime.now();
        LocalDateTime BottomWindowLogin = LocalDateTime.now().minusMinutes(15);
        LocalDateTime TopWindowLogin = LocalDateTime.now().plusMinutes(15);
        LocalDateTime CheckStart_time;
        LocalDateTime CheckEnd_time;
        // where i left off
        int Apointment_ID = 0;
        LocalDateTime Start_Time = null;
        LocalDateTime End_Time = null;
        boolean AppointmentIn15 = false;
        boolean Appointment_Now = false;
        ObservableList<Appointments> DBAppointments = DatabaseAppointments.getDBAppointments();
        for (Appointments currAppointment : DBAppointments) {
            CheckStart_time = currAppointment.getStartDate();
            CheckEnd_time = currAppointment.getEndDate();
            int Check_DBUsers = currAppointment.getUserID();
            if ((Check_DBUsers == currentUserID) &&
                    (CheckStart_time.isAfter(BottomWindowLogin) || CheckStart_time.isEqual(BottomWindowLogin)) &&
                            (CheckStart_time.isBefore(TopWindowLogin) || (CheckStart_time.isEqual(TopWindowLogin)))) {
                Apointment_ID = currAppointment.getId();
                Start_Time = CheckStart_time;
                End_Time = CheckEnd_time;
                AppointmentIn15 = true;
            }
            else if ((Check_DBUsers == currentUserID) &&
                    (loginTime.isAfter(CheckStart_time) || loginTime.isEqual(CheckStart_time)) &&
                            (loginTime.isBefore(CheckEnd_time) || (loginTime.isEqual(CheckEnd_time)))) {
                System.out.println(loginTime);
                System.out.println(CheckStart_time);
                System.out.println(CheckEnd_time);
                Apointment_ID = currAppointment.getId();
                Start_Time = CheckStart_time;
                End_Time = CheckEnd_time;
                Appointment_Now = true;
            }
        }
        if (AppointmentIn15) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("AppointmentController");
            alert.setHeaderText("There is an Upcoming AppointmentController within 15 minutes");
            alert.setContentText("AppointmentController ID : "+ Apointment_ID +"\n"+"Start Date : "+ Start_Time.toLocalDate().toString()+ Start_Time.toLocalTime() + "\n"+" End Date : "+  End_Time.toLocalDate() + End_Time.toLocalTime() );
            alert.showAndWait();
        }
        else if (Appointment_Now) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("AppointmentController");
            alert.setHeaderText("a customer has an appointment");
            alert.setContentText("AppointmentController ID : "+ Apointment_ID +"\n"+"Date : "+ Start_Time.toLocalDate().toString()+ Start_Time.toLocalTime());
            alert.showAndWait();
        }
        else {
           errorMessage("No upcoming appointments");
        }

        }


    /**
     *Populate Customer Information to tableView
     * @throws SQLException if exception has occurred
     */
    public void DisplayCustomerInfo() throws SQLException {
        ObservableList<Customers> DBCustomers = DatabaseCustomers.getDBcustomers();
        TableView_Customers.setItems(DBCustomers);
    }

    /**
     * Displays a Welcome message and asks is there an appointment coming up then verifies weather there is or isn't.
     * @param message String message required
     */
    private void errorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Welcome to the my Scheduling Database");
        alert.setHeaderText("is there an upcoming appointment ?");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a message wheater there has been a invaild entry
     * @param message string message required
     */
    private void errorMessage2(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Adding Customer");
        alert.setHeaderText("Invalid Data");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays A Customers Data to the Table View and disables the Add button if a customer has already been selected.
     * @throws SQLException if error occurs
     */
    public void DisplayCustomerDataToTableview() throws SQLException {
        Customers Current_customer = TableView_Customers.getSelectionModel().getSelectedItem();
        if (Current_customer != null) {
            ObservableList<Country> DBCountries = DatabaseCountries.getDBcountries();
            ObservableList<Division> divisions = DatabaseDivisions.getDBDivisions();
            ObservableList<String> AllDivision_Names = FXCollections.observableArrayList();
            for (Division Current_Division : divisions) {
                AllDivision_Names.add(Current_Division.getDivisionName());
            }
            Division_Selection.setItems(AllDivision_Names);
            customerIDTextField.setText(String.valueOf((Current_customer.getId())));
            customerNameTextField.setText(Current_customer.getName());
            customerAddressTextField.setText(Current_customer.getAddress());
            customerPostalCodeTextField.setText(Current_customer.getZipcode());
            customerPhoneNumberTextField.setText(Current_customer.getPhone());
            int set_CurrentDivision = Current_customer.getDivisionID();
            String set_DivisionName = "";
            int set_CountryID;
            String set_CountryName = "";
            for (Division DivisionLevel : divisions) {
                if (set_CurrentDivision == DivisionLevel.getId()) {
                    set_DivisionName = DivisionLevel.getDivisionName();
                    set_CountryID = DivisionLevel.getCountry_ID();
                    for (Country Current_Country : DBCountries) {
                        if (set_CountryID == Current_Country.getId()) {
                            set_CountryName = Current_Country.getCountry();
                        }
                    }
                }
            }
            Division_Selection.setValue(set_DivisionName);
            Country_Selection.setValue(set_CountryName);
            Add_Customer.setDisable(true);
            customerUpdateButton.setDisable(false);
            customerDeleteButton.setDisable(false);
            customerResetButton.setDisable(false);
        }
    }

    /**
     * Populates all Divisions to the corresponding ComboBox
     * @throws SQLException if method to Populate is violated in anyway
     */
    public void Divisions_toCombobox() throws SQLException {
        ObservableList<Division> DB_Division = DatabaseDivisions.getDBDivisions();
        ObservableList<String> US1 = FXCollections.observableArrayList();
        ObservableList<String> UK2 = FXCollections.observableArrayList();
        ObservableList<String> Canada3 = FXCollections.observableArrayList();
        for (Division Current_Division : DB_Division) {
            if (Current_Division.getCountry_ID() == 1) {
                US1.add(Current_Division.getDivisionName());
            }
            else if (Current_Division.getCountry_ID() == 2) {
                UK2.add(Current_Division.getDivisionName());
            }
            else if (Current_Division.getCountry_ID() == 3) {
                Canada3.add(Current_Division.getDivisionName());
            }
        }
        String Current_Country = Country_Selection.getSelectionModel().getSelectedItem();
        if (Current_Country.equals("U.S")) {
            Division_Selection.setItems(US1);
        }
        else if (Current_Country.equals("UK")) {
            Division_Selection.setItems(UK2);
        }
        else if (Current_Country.equals("Canada")) {
            Division_Selection.setItems(Canada3);
        }
    }

    /**
     *
     *  method to reset fields in the customerview
     *
     */
    public void ClearCustomerFields() {
        TableView_Customers.getSelectionModel().clearSelection();
        customerIDTextField.clear();
        customerIDTextField.setDisable(true);
        customerNameTextField.clear();
        customerAddressTextField.clear();
        customerPostalCodeTextField.clear();
        customerPhoneNumberTextField.clear();
        Country_Selection.setValue("");
        Division_Selection.setValue("");
        Add_Customer.setDisable(false);
        customerUpdateButton.setDisable(true);
        customerDeleteButton.setDisable(true);
        customerResetButton.setDisable(true);
    }

    /**
     * Displays Actions done to the Console
     */
    public void Display_Action() {
        if (ButtonChosen == 1) { System.out.println( "Customer" + " added to database"); }
        if (ButtonChosen == 2) { System.out.println("Customer updated in database"); }
        ButtonChosen = -1;
    }

    /**
     *
     * Lambda 3 sets an action upon a button being clicked
     */
    public void setCustomerOptionAddAndUpdateButtonActions() {
        Add_Customer.setOnAction(e -> { ButtonChosen = 1;try { addCustomer();
        } catch (SQLException throwables) { throwables.printStackTrace();}});
        customerUpdateButton.setOnAction(e -> { ButtonChosen = 2;try { updateCustomer();
        } catch (SQLException throwables) { throwables.printStackTrace();}});
    }

    /**
     * method to validate that the customer inputs data constant with sql parameters
     * method to check if data input is empty or not as well
     * prompt an error message
     * @return true if vaild and returns a message if false
     */
    private boolean validData(){
        String msg = "";
        if (customerNameTextField.getText().isEmpty())
            msg = "Customer name";
        else if(customerAddressTextField.getText().isEmpty())
            msg = "Customer Address";
        else if(customerPostalCodeTextField.getText().isEmpty())
            msg = "Customer Zipcode";
        else if(customerPhoneNumberTextField.getText().isEmpty())
            msg = "Cutomer phone number";
        else if(Country_Selection.getSelectionModel().getSelectedItem() == null)
            msg = "Customer country";
        else if(Division_Selection.getSelectionModel().getSelectedItem() == null)
            msg = "Customer division";

        if(msg.isEmpty())return true;
        msg += " is mandatory";
        errorMessage2(msg);
        return false;
    }

    /**
     * Method to Add Customer and implement logical and functionality checks.
     * @throws SQLException if Method to Add Customer is violated
     */
    public void addCustomer() throws SQLException {
        if (validData()) {
            int lastID = 0;
            ObservableList<Customers> allCustomersList = DatabaseCustomers.getDBcustomers();
            for (Customers customer : allCustomersList) {
                lastID = customer.getId();
            }
            int idToAdd = lastID + 1;
            String Name = customerNameTextField.getText();
            String Address = customerAddressTextField.getText();
            String Zipcode = customerPostalCodeTextField.getText();
            String Phone = customerPhoneNumberTextField.getText();
            int Division_id = 0;
            String Current_Division = Division_Selection.getSelectionModel().getSelectedItem();
            ObservableList<Division> DBDivisions = DatabaseDivisions.getDBDivisions();
            for (Division Current_Division2 : DBDivisions) {
                if (Current_Division.equals(Current_Division2.getDivisionName())) {
                    Division_id = Current_Division2.getId();
                }
            }
            LocalDateTime Current_Time = LocalDateTime.now();
            String Admin = "admin";
            Timestamp Curr_UpdateTime = Timestamp.valueOf(LocalDateTime.now());
            String UpdateAdmin = "admin";
            String insertStatement = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, " +
                    "Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?)";
            DBQuery.setPreparedStatement(DatabaseConnection.getConnection(), insertStatement);
            PreparedStatement preStatement = DBQuery.getPreparedStatement();
            preStatement.setInt(1, idToAdd);
            preStatement.setString(2, Name);
            preStatement.setString(3, Address);
            preStatement.setString(4, Zipcode);
            preStatement.setString(5, Phone);
            preStatement.setTimestamp(6, Timestamp.valueOf(Current_Time));
            preStatement.setString(7, Admin);
            preStatement.setTimestamp(8, Curr_UpdateTime);
            preStatement.setString(9, UpdateAdmin);
            preStatement.setInt(10, Division_id);
            preStatement.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Adding Customer");
            alert.setHeaderText("Customer Added");
            alert.setContentText("Congrats you added a customer.");
            alert.showAndWait();
            DisplayCustomerInfo();
            Display_Action();
            ClearCustomerFields();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Adding Customer");
            alert.setHeaderText("Invalid Data");
            alert.setContentText("Customer Data is Incomplete");
            alert.showAndWait();
        }
    }
    /**
     * Method to Update Customer and implement logical and functionality checks.
     * @throws SQLException if Method to Update Customer
     */
    public void updateCustomer() throws SQLException {
        int Customer_id = Integer.parseInt(customerIDTextField.getText());
        String Customer_Name = customerNameTextField.getText();
        String Customer_Address = customerAddressTextField.getText();
        String Customer_Zipcode = customerPostalCodeTextField.getText();
        String Customer_Phone = customerPhoneNumberTextField.getText();
        String Customer_Division = Division_Selection.getValue();
        int Division_id = 0;
        ObservableList<Division> DB_Division = DatabaseDivisions.getDBDivisions();
        for (Division Current_Division : DB_Division) {
            if (Customer_Division.equals(Current_Division.getDivisionName())) {
                Division_id = Current_Division.getId();
            }
        }
        Timestamp Update_Time = Timestamp.valueOf(LocalDateTime.now());
        String Admin2 = "admin";
        String SQL_Update = "UPDATE customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, " +
                "Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, " +
                "Division_ID = ? WHERE Customer_ID = ?";
        DBQuery.setPreparedStatement(DatabaseConnection.getConnection(), SQL_Update);
        PreparedStatement prepareupdate = DBQuery.getPreparedStatement();
        prepareupdate.setInt(1, Customer_id);
        prepareupdate.setString(2, Customer_Name);
        prepareupdate.setString(3, Customer_Address);
        prepareupdate.setString(4, Customer_Zipcode);
        prepareupdate.setString(5, Customer_Phone);
        prepareupdate.setTimestamp(6, Update_Time);
        prepareupdate.setString(7, Admin2);
        prepareupdate.setInt(8, Division_id);
        prepareupdate.setInt(9, Customer_id);
        prepareupdate.execute();
        DisplayCustomerInfo();
        Display_Action();
        ClearCustomerFields();
    }

    /**
     * method to delete Customer from the Database
     * @param event delete Customer
     * @throws SQLException delete Customer function violated in some way
     */
   public void RemoveCustomer(ActionEvent event) throws SQLException {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       Update_c = false;
       Customers customer = TableView_Customers.getSelectionModel().getSelectedItem();
       if (customer != null) ID_Chosen = customer.getId();
       else {

           alert.setTitle("Alert");
           alert.setHeaderText("Invalid Selection");
           alert.setContentText("No Customer is selected");
           alert.showAndWait();
           return;
       }
       DatabaseConnection.getConnection().createStatement().executeUpdate(Queries.deleteCustomer(ID_Chosen));
       Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
       alert2.setTitle("Customer Deletion");
       alert2.setHeaderText("Customer:" + ID_Chosen +  "is deleted along with all of their appointments");
       alert2.setContentText("Customer delete is completed");
       alert2.showAndWait();
               DisplayCustomerInfo();
               ClearCustomerFields();

           }



    /**
     * Method to move to Appointment View method
     * @param event appointment
     */
    public void Appointment(ActionEvent event) {
        ((((Button) event.getSource()).getScene().getWindow())).hide();
        new LoadScene().loadPage(new Stage(), FxmlNames.APPOINTMENT, FxmlNames.APPOINTMENT_TITLE);
    }


    /**
     * Method to move to Reports View method
     * @param event Reports
     */
    public void Reports(ActionEvent event) {
        ((((Button) event.getSource()).getScene().getWindow())).hide();
        new LoadScene().loadPage(new Stage(), FxmlNames.REPORTS, FxmlNames.REPORTS_TITLE);
    }



}
