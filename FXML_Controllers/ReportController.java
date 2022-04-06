package FXML_Controllers;

import Database_Utils.*;
import Database_Models.*;
import Reports.ReportByMonth;
import Reports.ReportByType;
import Reports.ReportByDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import util.FxmlNames;
import util.LoadScene;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Creates the reportController
 */
public class ReportController {
    @FXML
    private PieChart PieChart;
    @FXML TableView<ReportByMonth> Monthly_Customer_Appointment;
    @FXML TableColumn<ReportByMonth, String> Customer_Appointment_Month_C;
    @FXML TableColumn<ReportByMonth, Integer> Customer_Appointment_Total_C;


private HashMap<Integer, Integer> Division_Total;
    @FXML private TableView<Report> Month_Report;
    @FXML private TableColumn<Report, Integer> Type_A;
    @FXML private TableColumn<Report, String> Month_A, Total_Appointment;

    @FXML Tab Appointment_CustomerTab;

    @FXML Tab Customer_Division_LevelTab;
    @FXML TableView<ReportByDivision> Customer_Division_TableView;
    @FXML TableColumn<ReportByDivision, String> DivisionLevel;
    @FXML TableColumn<ReportByDivision, ArrayList<String>> CustomerTableColumn;
    private ArrayList<Division> divisions;
    private ArrayList<Customers> customers;

    @FXML Tab FliterAppointmentTab;
    @FXML TableView<Appointments> FliterAppointmentTableView;
    @FXML TableColumn<Appointments, Integer> FliterAppointmentID;
    @FXML TableColumn<Appointments, String> FliterAppointmentType, FliterAppointmentDescription, FliterAppointmentCustomer;
    @FXML TableColumn<Appointments, LocalDateTime> FliterStart_Time, Fliter_EndTime;
    @FXML TableColumn<Appointments, Integer> FliterAppointmentCustomerID;
    @FXML ComboBox<String> FliterByContact;

    @FXML TableView<ReportByType> Appointment_CustomerTableView;
    @FXML TableColumn<ReportByType, String> Appointment_CustomerType;
    @FXML TableColumn<ReportByType, Integer> C_A_Type_Total;




    /**
     *
     * @throws SQLException if exception has occurred
     */
    public void initialize() throws SQLException {
        customers = new ArrayList<>();
        divisions = new ArrayList<>();
     //  PieChart.setTitle("Division Breakdown");
        Division_Total = new HashMap<>();
        Month_Report.setItems(util.Database_reports.Appt_by_monthtype());
        Type_A.setCellValueFactory(new PropertyValueFactory<>("Type"));
        Month_A.setCellValueFactory(new PropertyValueFactory<>("Month"));
        Total_Appointment.setCellValueFactory(new PropertyValueFactory<>("Count"));
        //contactLabel.setVisible(false);
        FliterByContact.setVisible(false);
        FliterByContact.setEditable(true);
        FliterByContact.getEditor().setEditable(false);

        // report 1 -- table view 2 -- appointment type
        Appointment_CustomerType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        C_A_Type_Total.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));

        // report 1 -- table view 2 -- appointment month
        Customer_Appointment_Month_C.setCellValueFactory(new PropertyValueFactory<>("appointmentMonth"));
        Customer_Appointment_Total_C.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));

        // report 2 -- appointment by contact
        FliterAppointmentID.setCellValueFactory(new PropertyValueFactory<>("id"));
        FliterAppointmentCustomer.setCellValueFactory(new PropertyValueFactory<>("title"));
        FliterAppointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        FliterAppointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        FliterStart_Time.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        Fliter_EndTime.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        FliterAppointmentCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        // report 3 -- customers by first-level division
        DivisionLevel.setCellValueFactory(new PropertyValueFactory<>("firstLevelDivision"));
        CustomerTableColumn.setCellValueFactory(new PropertyValueFactory<>("customerList"));
    }

    public void DisplayTypeAndTotalsToTableview() throws SQLException {
        String Set_Type;
        int Set_Total;
        ObservableList<Appointments> DBAppointment = DatabaseAppointments.getDBAppointments();
        ObservableList<String> Type_OfAppointment = FXCollections.observableArrayList();
        DBAppointment.forEach(appointments -> Type_OfAppointment.add(appointments.getType()));
        ObservableList<String> Correct_TypeNumber = FXCollections.observableArrayList();
        for (Appointments Current_Appointment : DBAppointment) {
            String type = Current_Appointment.getType();
            if (!Correct_TypeNumber.contains(type)) {
                Correct_TypeNumber.add(type);
            }
        }
        ObservableList<ReportByType> ReportOfType = FXCollections.observableArrayList();
        for (String Type : Correct_TypeNumber) {
            int Count = Collections.frequency(Type_OfAppointment, Type);
            Set_Type = Type;
            Set_Total = Count;
            ReportByType TI = new ReportByType(Set_Type, Set_Total);
            ReportOfType.add(TI);
        }
        Appointment_CustomerTableView.setItems(ReportOfType);
    }

    /**
     * gets a list of all appointments without repeats
     * displays the data to tableview
     * @throws SQLException if violated
     */
    public void DisplayAppointmentMonthsWithTotal() throws SQLException {
        String Set_Month;
        int Set_Total;
        ObservableList<Appointments> DBAppointments = DatabaseAppointments.getDBAppointments();
        ObservableList<Month> Months_Appointment = FXCollections.observableArrayList();
        for (Appointments Current_Appointment : DBAppointments) {
            Month Current_Month = Current_Appointment.getStartDate().getMonth();
            Months_Appointment.add(Current_Month);
        }
        ObservableList<Month> appointmentMonthsWithoutDuplicates = FXCollections.observableArrayList();
        for (Month Current_Month : Months_Appointment) {
            if (!appointmentMonthsWithoutDuplicates.contains(Current_Month)) {
                appointmentMonthsWithoutDuplicates.add(Current_Month);
            }
        }
        ObservableList<ReportByMonth> reportByMonths = FXCollections.observableArrayList();
        for (Month Curr_Month : appointmentMonthsWithoutDuplicates) {
            int total = Collections.frequency(Months_Appointment, Curr_Month);
            Set_Month = Curr_Month.name();
            Set_Total = total;
            ReportByMonth monthInstance = new ReportByMonth(Set_Month, Set_Total);
            reportByMonths.add(monthInstance);
        }
        Monthly_Customer_Appointment.setItems(reportByMonths);
    }

    /**
     * Takes two methods and combines them
     * @throws SQLException if violated
     */
    public void DisplayTotalAppointmentCount() throws SQLException {
        DisplayTypeAndTotalsToTableview();
        DisplayAppointmentMonthsWithTotal();
    }


    /**
     * Method to show report that filters by Contacts
     *
     *
     * @throws SQLException if exception has occurred
     */
    public void DisplayFliterContacts() throws SQLException {

        FliterByContact.setVisible(true);
        ObservableList<Contacts> allContacts = DatabaseContacts.getDBContacts();
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for (Contacts contacts : allContacts) {
            String contactName = contacts.getName();
            contactNames.add(contactName);
        }
        FliterByContact.setItems(contactNames);
    }

    /**
     * gets selected contact name from combo box
     * matches selected name with contact ID from all contact data
     * gets selected contact appointment data from all appointment data
     *
     * @throws SQLException if exception has occurred
     */
    public void ShowFliterContact() throws SQLException {
        int Current_ContactId = 0;
        Appointments Current_appointmentData;
        String Current_ContactName = FliterByContact.getSelectionModel().getSelectedItem();
        if (Current_ContactName != null) {

            ObservableList<Contacts> DBContacts = DatabaseContacts.getDBContacts();
            for (Contacts Current_Contact : DBContacts) {
                if (Current_ContactName.equals(Current_Contact.getName())) {
                    Current_ContactId = Current_Contact.getId();
                }
            }
            ObservableList<Appointments> DBAppointments = DatabaseAppointments.getDBAppointments();
            ObservableList<Appointments> GetAppointmentsForCurrContact = FXCollections.observableArrayList();
            for (Appointments Current_Appointment : DBAppointments) {
                if (Current_ContactId == Current_Appointment.getContactId()) {
                    Current_appointmentData = Current_Appointment;
                    GetAppointmentsForCurrContact.add(Current_appointmentData);
                }
            }
            FliterAppointmentTableView.setItems(GetAppointmentsForCurrContact);
        }

    }



    /**
     * Matches A Customer To their corresponding Division Level
     * report for division level
     *
     * @throws SQLException if exception has occurred
     */
    public void DisplayCustomerDivisons() throws SQLException {
        String Set_Divisions;
        ArrayList<String> Set_CustomerList;
        ReportByDivision Current_record;
        String LastDivision_Name = "";

        ObservableList<Division> DBDivision = DatabaseDivisions.getDBDivisions();
        ObservableList<Customers> Listof_DBCustomers = DatabaseCustomers.getDBcustomers();
        ObservableList<ReportByDivision> ArrayGetSet = FXCollections.observableArrayList();

        for (Division Current_division : DBDivision) {
            Set_CustomerList = new ArrayList<>();
            for (Customers Current_Customer : Listof_DBCustomers) {
                if (Current_division.getId() == Current_Customer.divisionID) {
                    Set_Divisions = Current_division.getDivisionName();
                    Set_CustomerList.add(Current_Customer.getName());
                    Current_record = new ReportByDivision(Set_Divisions, Set_CustomerList);
                    if (!Current_record.getFirstLevelDivision().equals(LastDivision_Name)) {
                        ArrayGetSet.add(Current_record);
                        LastDivision_Name = Current_record.getFirstLevelDivision();
                    }
                }
            }
        }
        Customer_Division_TableView.setItems(ArrayGetSet);
    }

    /**
     * Attempt to make a pie chart
     * @throws SQLException if violated
     */
    private void fetchCustomers() throws SQLException {
        for (Customers customer : customers)
            Division_Total.merge(customer.getDivisionID(), 1, Integer::sum);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Integer key : Division_Total.keySet()) {
            Predicate<Division> predicate = division -> division.getId() == key;
            pieChartData.add(new PieChart.Data(divisions.stream().filter(predicate).collect(Collectors.toList()).get(0).getDivisionName(), Division_Total.get(key)));
        }

        PieChart.getData().addAll(pieChartData);
    }

    /**
     * Method to move to Appointment View method
     * @param event Appointment
     */
    public void To_Appointment(ActionEvent event) {
        ((((Button) event.getSource()).getScene().getWindow())).hide();
        new LoadScene().loadPage(new Stage(), FxmlNames.APPOINTMENT, FxmlNames.APPOINTMENT_TITLE);
    }
    /**
     * Customer controller method to move between scenes
     * @param event customer
     */
    public void Customer(ActionEvent event) {
        ((((Button) event.getSource()).getScene().getWindow())).hide();
        new LoadScene().loadPage(new Stage(), FxmlNames.CUSTOMER, FxmlNames.CUSTOMER_TITLE);
    }

}
