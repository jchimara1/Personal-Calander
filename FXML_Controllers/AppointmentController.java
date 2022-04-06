package FXML_Controllers;

import Database_Utils.*;
import util.*;
import Database_Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;

import java.util.TimeZone;

/**
 * Create Appointment Class
 * Justice Chimara
 */
public class AppointmentController {

    // month table view
    @FXML
    public Tab MonthlyTab;
    @FXML
    public TableView<Appointments> MonthlyTableView;
    @FXML
    public TableColumn<Appointments, Integer> MonthlyIDCol, MonthlyContactColumn, MonthlyCustomeridColumn;
    @FXML
    public TableColumn<Appointments, String> MonthlyTitleColumn, MonthlyDescriptionColumn, MonthlyLocationColumn, MonthlyTypeColumn;
    @FXML
    public TableColumn<Appointments, LocalDateTime> MonthlyStartTimeColumn, MonthlyEndtimeColumn;

    // week table view
    @FXML
    public Tab weeklyTab;
    @FXML
    public TableView<Appointments> WeeklyTableView;
    @FXML
    public TableColumn<Appointments, Integer> weeklyID, weeklycontact, weeklycustomerid;
    @FXML
    public TableColumn<Appointments, String> weeklyTitle, weeklydescription, weeklylocation, weeklytype;
    @FXML
    public TableColumn<Appointments, LocalDateTime> weeklystarttime, weeklyendtime;


    // all table view
    @FXML
    public Tab allappointmenttab;
    @FXML
    public TableView<Appointments> allappointmenttableveiw;
    @FXML
    public TableColumn<Appointments, Integer> allappointmentid, allappointmentcontact, allappointmentcustomer_id, Weekly_UserID, Month_UserID, All_UserID;
    @FXML
    public TableColumn<Appointments, String> allappointmenttitle, allapointmentdescription, allappointmentlocation, allappointmenttyype;

    @FXML
    public TableColumn<Appointments, LocalDateTime> allappointmentstarttime, allappointmentendtime;

    @FXML
    Button AddAppointment, UpdateAppointment, DeleteAppointment,  ResetFields;
    @FXML
    TextField title,  Description, LocationID, id, type, Customer_id, user_id;
    @FXML
    DatePicker StartDate, EndDate;
    @FXML
    ComboBox<String>  Contactoptions;
    @FXML
    ComboBox<LocalTime> Starttime, EndTime;
    /**
     * initializes tableView columns and populates comboBox options (times in local time)
     * populates tables with appointment data
     * Contains Lambda Expression 1 : Disables the past dates in StartDate picker control
     * Contains Lambda Expression 2 : Disables the past dates in EndDate picker control
     * Both Lambda expressions help prevent logical errors because why would you schedule appointments  in the past
     * @throws SQLException if exception has occurred
     */
    public void initialize() throws SQLException {
ObservableList<LocalTime>startTimeList=FXCollections.observableArrayList();
ZoneId easternZid = ZoneId.of("America/New_York");
ZonedDateTime estZDT = ZonedDateTime.of(LocalDate.now(),LocalTime.of(8,0), easternZid);
ZoneId osZid = ZoneId.systemDefault();
ZonedDateTime osZDT = ZonedDateTime.ofInstant(estZDT.toInstant(), osZid);

LocalTime startTime = osZDT.toLocalTime();
startTimeList.add(startTime);
//System.out.println(osZDT.toLocalTime());
for(int i = 0; i < 15; i++){
//System.out.println(startTime);
startTime = startTime.plusHours(1);
startTimeList.add(startTime);
}
        // Lambda Expression 1 : Disables the past dates in StartDate  picker control
        StartDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
                if (date.isBefore(LocalDate.now())) {
                    setStyle("-fx-background-color: #808080;");
                }
            }
        });

        // Lambda Expression 2 : Disables the past dates in EndDate picker control
        EndDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
                if (date.isBefore(LocalDate.now())) {
                    setStyle("-fx-background-color: #808080;");
                }
            }
        });


        MonthlyIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        MonthlyTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        MonthlyDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        MonthlyLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        MonthlyContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        MonthlyTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        MonthlyStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        MonthlyEndtimeColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        MonthlyCustomeridColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    //    Month_UserID.setCellValueFactory(new PropertyValueFactory<>("userID"));

        weeklyID.setCellValueFactory(new PropertyValueFactory<>("id"));
        weeklyTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        weeklydescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        weeklylocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        weeklycontact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        weeklytype.setCellValueFactory(new PropertyValueFactory<>("type"));
        weeklystarttime.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        weeklyendtime.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        weeklycustomerid.setCellValueFactory(new PropertyValueFactory<>("customerId"));
     //   Weekly_UserID.setCellValueFactory(new PropertyValueFactory<>("userID"));

        allappointmentid.setCellValueFactory(new PropertyValueFactory<>("id"));
        allappointmenttitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        allapointmentdescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        allappointmentlocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        allappointmentcontact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        allappointmenttyype.setCellValueFactory(new PropertyValueFactory<>("type"));
        allappointmentstarttime.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        allappointmentendtime.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        allappointmentcustomer_id.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        All_UserID.setCellValueFactory(new PropertyValueFactory<>("userID"));

        ObservableList<Contacts> DBContactslist = DatabaseContacts.getDBContacts();
        ObservableList<String> AllDBContacts = FXCollections.observableArrayList();
        DBContactslist.forEach(contacts -> AllDBContacts.add(contacts.getName()));
        Contactoptions.setItems(AllDBContacts);
        Contactoptions.setEditable(true);
        Contactoptions.getEditor().setEditable(false);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        ObservableList<String> Avaliabletimes = FXCollections.observableArrayList();
        LocalTime startlocaldatetime = LocalTime.MIN.plusHours(8);
        LocalTime endlocaldatetime = LocalTime.MAX.minusHours(1).minusMinutes(59);
        while (startlocaldatetime.isBefore(endlocaldatetime)) {
            Avaliabletimes.add(formatter.format(startlocaldatetime));
            startlocaldatetime = startlocaldatetime.plusMinutes(30);
        }


        Starttime.setItems(startTimeList);

        Starttime.setEditable(true);
        Starttime.getEditor().setEditable(false);
        EndTime.setItems(startTimeList);
        EndTime.setEditable(true);
        EndTime.getEditor().setEditable(false);
        DisplayDatatotableview();
    }
    /**
     *  method to produce an error message
     *  when Adding an appointment for a customer
     * @param message needed
     */
    private void errorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Editing Appointment for Customer");
        alert.setHeaderText("something just happened what is it?");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Populates data to the Respective tableview meant to be used after commiting a change to the database
     *
     * @throws SQLException if error occurs
     */
    public void DisplayDatatotableview() throws SQLException {
        ObservableList<Appointments> listofDBAppointment = DatabaseAppointments.getDBAppointments();
        ObservableList<Appointments> AllMonthAppointments = FXCollections.observableArrayList();
        ObservableList<Appointments> ALlWeekAppointments = FXCollections.observableArrayList();

        LocalDateTime StartofMonth = LocalDateTime.now().minusSeconds(1);
        LocalDateTime EndofMonth = LocalDateTime.now().plusMonths(1);

        LocalDateTime StartofWeek = LocalDateTime.now().minusSeconds(1);
        LocalDateTime EndofWeek = LocalDateTime.now().plusWeeks(1);

        for (Appointments currappointment : listofDBAppointment) {
            if (currappointment.getEndDate().isAfter(StartofMonth) && currappointment.getEndDate().isBefore(EndofMonth)) {
                AllMonthAppointments.add(currappointment);
            }
            if (currappointment.getEndDate().isAfter(StartofWeek) && currappointment.getEndDate().isBefore(EndofWeek)) {
                ALlWeekAppointments.add(currappointment);
            }
        }
        MonthlyTableView.setItems(AllMonthAppointments);
        WeeklyTableView.setItems(ALlWeekAppointments);
        allappointmenttableveiw.setItems(listofDBAppointment);
    }

    /**
     * Displays corresponding data after an appointment is chosen
     * this method also disables the regular add button that is avaliable when someone choses an appointment
     * @throws SQLException if unable to display appointment data
     */
    public void DisplayAppointmentData() throws SQLException {
        Appointments getappointment = null;
        if (MonthlyTab.isSelected()) {
            getappointment = MonthlyTableView.getSelectionModel().getSelectedItem();
        }
        if (weeklyTab.isSelected()) {
            getappointment = WeeklyTableView.getSelectionModel().getSelectedItem();
        }
        if (allappointmenttab.isSelected()) {
            getappointment = allappointmenttableveiw.getSelectionModel().getSelectedItem();
        }
        if (getappointment != null) {
            id.setText(String.valueOf((getappointment.getId())));
            title.setText(getappointment.getTitle());
            Description.setText(getappointment.getDescription());
            LocationID.setText(getappointment.getLocation());
            int showcontactid = getappointment.getContactId();
            String showcontact = "";
            ObservableList<Contacts> contactsObservableList = DatabaseContacts.getDBContacts();
            for (Contacts contact : contactsObservableList) {
                if (showcontactid == contact.getId()) {
                    showcontact = contact.getName();

                }
                Contactoptions.setValue(showcontact);
                type.setText(getappointment.getType());
                StartDate.setValue(getappointment.getStartDate().toLocalDate());
                EndDate.setValue(getappointment.getEndDate().toLocalDate());
                Starttime.setValue(getappointment.getStartDate().toLocalTime());
                EndTime.setValue(getappointment.getEndDate().toLocalTime());
                Customer_id.setText(String.valueOf(getappointment.getCustomerId()));
                user_id.setText(String.valueOf(getappointment.getUserID()));
                AddAppointment.setDisable(true);
                UpdateAppointment.setDisable(false);
                DeleteAppointment.setDisable(false);
                ResetFields.setDisable(false);
            }
        }

    }

    /**
     * Resets Fields that to blank after being filled.
     */
    @FXML
    private void ClearAppointmentFields() {
        MonthlyTableView.getSelectionModel().clearSelection();
        title.clear();
        Description.clear();
        LocationID.clear();
        id.clear();
        type.clear();
        StartDate.setValue(null);
        EndDate.setValue(null);
        Starttime.setValue(null);
        EndTime.setValue(null);
        Customer_id.clear();
        user_id.clear();
        Contactoptions.setValue("");
        AddAppointment.setDisable(false);
        UpdateAppointment.setDisable(true);
        DeleteAppointment.setDisable(true);
        ResetFields.setDisable(true);
    }

    /**
     *
     * method to validate data that is entered
     * checking start Date, start time, end Date, and endtime, title, Description, LocationID, Type, contact and customerID
     * @return true if correct or false
     * @throws ParseException if error occurs
     */
    private boolean validData() throws ParseException {

        String msg = "";
        if (title.getText().isEmpty())
            msg = "Appointment title is mandatory";
        else if (Description.getText().isEmpty())
            msg = "Appointment description is mandatory";
        else if (LocationID.getText().isEmpty())
            msg = "Appointment location is mandatory";
        else if (type.getText().isEmpty())
            msg = "Appointment type is mandatory";

            //checking start Date
        else if (Starttime.getValue() == null)
            msg = "Appointment start time is mandatory";
            //check end here

            //checking start time here
        else if (StartDate.getValue() == null)
            msg = "Appointment start date is mandatory";
            //check end here
            //checking start time here
        else if (EndDate.getValue() == null)
            msg = "Appointment start date is mandatory";

            //checking end date here
        else if (EndTime.getValue()==null)
            msg = "Appointment end time is mandatory";

            //check end here
        else if (Customer_id.getText().isEmpty())
            msg = "Appointment customer is mandatory";
        else if (Contactoptions.getSelectionModel().getSelectedItem() == null)
            msg = "Appointment contact is mandatory";



        if (msg.isEmpty()) return true;
        errorMessage(msg);
        return false;}

    /**
     * Method to commit appointments to the database
     * it check Appointment DateTime line, check Appointment Timeline
     * Method Also creates customized error message for all possible errors
     * verifies that there is a matching foreign key for customer ID and user ID for all appointments
     * verify that all boxes have inputs
     * verify that appointment falls within business operation hours/days while preventing overlapping appointments
     * creates a dynamic combo box that changes the available start time and end time to prevent user from entering incorrect appointment Times
     * @param actionEvent add Appointment
     * @throws Exception if add appointment is violated in anyway
     */
    public void addAppointment(ActionEvent actionEvent) throws Exception {

        //
        if (validData()) {
            //
            ObservableList<Customers> getDBcustomerdata = DatabaseCustomers.getDBcustomers();
            ObservableList<Integer> listofCustomerIds = FXCollections.observableArrayList();

            for (Customers CurrCustomer : getDBcustomerdata) {
                listofCustomerIds.add(CurrCustomer.getId());
            }
            int i = 0;
            if (i == 0)
                try {
                    Integer.parseInt(Customer_id.getText());
                } catch (NumberFormatException e) {
                    Customer_id.clear();
                    errorMessage("Incorrect Format for the Customer Text Field ");

                }
            if (i == 0)
                try {
                    if (!listofCustomerIds.contains(Integer.parseInt(Customer_id.getText()))) {
                        errorMessage("Please Input an Existing Customer ID");
                        return;
                    }
                } catch (NumberFormatException e) {
                    errorMessage("Re-enter Customer ID ");
                }
            ObservableList<Users> GetDBUsers = DatabaseUsers.getDBUsers();
            ObservableList<Integer> listofUserId = FXCollections.observableArrayList();
            for (Users user : GetDBUsers) {
                listofUserId.add(user.getId());
            }


            int GetNewestAppointment = 0;
            ObservableList<Appointments> allAppointmentsList = DatabaseAppointments.getDBAppointments();
            for (Appointments getappointment : allAppointmentsList) {
                GetNewestAppointment = getappointment.getId();
            }
            int appointmentIDToAdd = GetNewestAppointment + 1;
            String newtitle = title.getText();
            String newdescription = Description.getText();
            String newlocation = LocationID.getText();
            String newtype = type.getText();
            String newcontact = Contactoptions.getValue();
            int newcontactid = 0;
            ObservableList<Contacts> getDBContacts = DatabaseContacts.getDBContacts();
            for (Contacts getcontact : getDBContacts) {
                if (newcontact.equals(getcontact.getName())) {
                    newcontactid = getcontact.getId();
                }
            }
            LocalDate startdate = StartDate.getValue();
            LocalDate enddate = EndDate.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String st = String.valueOf(Starttime.getValue());
            String et = String.valueOf(EndTime.getValue());
            LocalTime starttime = LocalTime.parse(st, formatter);
            LocalTime endtime = LocalTime.parse(et, formatter);
            LocalDateTime startLocalDateTimeToAdd = LocalDateTime.of(startdate, starttime);
            LocalDateTime endLocalDateTimeToAdd = LocalDateTime.of(enddate, endtime);
            ZoneId localZone1 = ZoneId.of(TimeZone.getDefault().getID());



            ZonedDateTime startLocaldt_tozoneDt = ZonedDateTime.of(startLocalDateTimeToAdd, ZoneId.systemDefault());

            ZonedDateTime startLocaldt_tozoneDtEST = startLocaldt_tozoneDt.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalTime getappointmentstarttime = startLocaldt_tozoneDtEST.toLocalTime();

           if (getappointmentstarttime.getHour()<= 8 || getappointmentstarttime.getHour() >= 22 )
           {
               errorMessage("Time is outside of business hours \n Please schedule between 08:00 and 22:00 of the whatever Timezome your are located in. ");
               return;
           }



            DayOfWeek getappointmentstartday = startLocaldt_tozoneDtEST.toLocalDate().getDayOfWeek();
            int getappointmentstartnumber = getappointmentstartday.getValue();

            ZonedDateTime endtimetozonetime = ZonedDateTime.of(endLocalDateTimeToAdd, ZoneId.systemDefault());
            ZonedDateTime endzonetimeto_est = endtimetozonetime.withZoneSameInstant(ZoneId.of("America/New_York"));


            LocalTime endtimetoadd = endzonetimeto_est.toLocalTime();

            if (endtimetoadd.getHour() < 8 || endtimetoadd.getHour() > 22)
            {
                errorMessage("Time is outside of business hours \n Please schedule between 08:00 and 22:00 of the whatever Timezome your are located in. ");
                return;
            }


            DayOfWeek enddaytoadd = endzonetimeto_est.toLocalDate().getDayOfWeek();
            int getAppointmentEndValue = enddaytoadd.getValue();

           LocalTime startWorkHours = LocalTime.of(8, 0, 0);
           LocalTime endWorkHours = LocalTime.of(22, 0, 0);
            int Monday = DayOfWeek.MONDAY.getValue();
            int Friday = DayOfWeek.FRIDAY.getValue();


            if (getappointmentstartnumber < Monday || getappointmentstartnumber > Friday ||
                    getAppointmentEndValue < Monday || getAppointmentEndValue > Friday) {
                errorMessage("Day is outside of business hours\n Please schedule between Monday and Friday");
                return;
            }

            Timestamp timeOfCreation = Timestamp.valueOf(LocalDateTime.now());
            String Admin = "admin";
            Timestamp UpdateTime = Timestamp.valueOf(LocalDateTime.now());
            String AdminUpdate = "admin";


            int CustomerIDint = 0;
            try {
                CustomerIDint = Integer.parseInt(Customer_id.getText());
            } catch (NumberFormatException e) {
                errorMessage(" Incorrect Format for the Customer or User Text Field \n appointment for customer ");
            }



            ObservableList<Appointments> OberservableAppointmentsList = DatabaseAppointments.getDBAppointments();
            for (Appointments appointment : OberservableAppointmentsList) {
                LocalDateTime startTimesToCheck = appointment.getStartDate();
                LocalDateTime endTimesToCheck = appointment.getEndDate();
                int customerIDsToCheck = appointment.getCustomerId();
                if (CustomerIDint == customerIDsToCheck &&
                        (startLocalDateTimeToAdd.isEqual(startTimesToCheck) ||
                                startLocalDateTimeToAdd.isAfter(startTimesToCheck)) &&
                        (startLocalDateTimeToAdd.isEqual(endTimesToCheck) ||
                                startLocalDateTimeToAdd.isBefore(endTimesToCheck))) {
                    errorMessage(" Start time overlaps with existing \n appointment for customer");
                    return;
                }
                if (CustomerIDint == customerIDsToCheck &&
                        (endLocalDateTimeToAdd.isEqual(startTimesToCheck) ||
                                endLocalDateTimeToAdd.isAfter(startTimesToCheck)) &&
                        (endLocalDateTimeToAdd.isEqual(endTimesToCheck) ||
                                endLocalDateTimeToAdd.isBefore(endTimesToCheck))) {
                    errorMessage(" End time overlaps with existing \n appointment for customer");
                    return;
                }
                if (StartDate.getValue().isAfter(EndDate.getValue())) {
                    errorMessage("Appointment Starts after End Date \n re-enter Start or end date");
                    return;
                }
                LocalTime j = starttime;
                LocalTime k = endtime;
                if (j.isAfter(k)) {
                    errorMessage("Appointment Starts after End Date \n re-enter Start or end date");
                    return;
                }

            }
            int userIDToAdd = 0;
            try {
                userIDToAdd = Integer.parseInt(user_id.getText());
            } catch (NumberFormatException e) {
                errorMessage(" Incorrect Format for the User Text Field ");
            }


            if (userIDToAdd == 1 || userIDToAdd == 2) {
                String insertStatement = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, " +
                        "Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                DBQuery.setPreparedStatement(DatabaseConnection.getConnection(), insertStatement);
                PreparedStatement add = DBQuery.getPreparedStatement();
                add.setInt(1, appointmentIDToAdd);
                add.setString(2, newtitle);
                add.setString(3, newdescription);
                add.setString(4, newlocation);
                add.setString(5, newtype);
                add.setTimestamp(6, Timestamp.valueOf(startLocalDateTimeToAdd));
                add.setTimestamp(7, Timestamp.valueOf(endLocalDateTimeToAdd));
                add.setTimestamp(8, timeOfCreation);
                add.setString(9, Admin);
                add.setTimestamp(10, UpdateTime);
                add.setString(11, AdminUpdate);
                add.setInt(12, CustomerIDint);
                add.setInt(13, userIDToAdd);
                add.setInt(14, newcontactid);
                try {
                    add.execute();
                    DisplayDatatotableview();
                    displayAppointmentFields("Appointment Name: " + newtitle + " Appointment ID: "+ appointmentIDToAdd+ " Appointment Type: " + newtype + " added to database");
                    System.out.println("Appointment Name: " + newtitle + " Appointment ID: "+ appointmentIDToAdd+ " Appointment Type: " + newtype + " added to database");
                    ClearAppointmentFields();
                } catch (Exception e) {
                    errorMessage("Must have a vaild customer ID \n appointment for customer");
                }

            } else {
                errorMessage("User ID Doesn't Exist ");
                return;
            }


        }
    }

    /**
     * Method to commit appointments to the database
     * it checks Appointment DateTime line, check Appointment Timeline
     * Method Also creates customized error message for all possible errors
     * verifies that there is a matching foreign key for customer ID and user ID for all appointments
     * verify that all boxes have inputs
     * verify that appointment falls within business operation hours/days while preventing overlapping appointments
     * creates a dynamic combo box that changes the available start time and end time to prevent user from entering incorrect appointment Times
     * @param actionEvent upadte appointment
     * @throws SQLException if update statement for appointment is violated
     */
    public void updateAppointment(ActionEvent actionEvent) throws SQLException {// verify that there is a matching foreign key for customer ID and user ID
        ObservableList<Customers> allCustomerData = DatabaseCustomers.getDBcustomers();
        ObservableList<Integer> allCustomerIDs = FXCollections.observableArrayList();
        for (Customers customer : allCustomerData) {
            allCustomerIDs.add(customer.getId());
        }
        int i = 0;
        if (i == 0)
            try {
                Integer.parseInt(Customer_id.getText());
            } catch (NumberFormatException e) {
                Customer_id.clear();
                errorMessage("Incorrect Format for the Customer Text Field ");

            }
        if (i == 0)
            try {
                if (!allCustomerIDs.contains(Integer.parseInt(Customer_id.getText()))) {
                    errorMessage("Please Input an Existing Customer ID");
                    return;
                }
            } catch (NumberFormatException e) {
                errorMessage("Re-enter Customer ID ");
            }



    ObservableList<Users> DBuserData = DatabaseUsers.getDBUsers();
        ObservableList<Integer> ListofDBUsers = FXCollections.observableArrayList();
        for (Users user : DBuserData) {
            ListofDBUsers.add(user.getId());
        }
        int UpdateUser = 0;
        try {
            UpdateUser = Integer.parseInt(user_id.getText());
        } catch (NumberFormatException e) {
            errorMessage(" Incorrect Format for the User Text Field ");
        }


    int appointmentIDToUpdate = Integer.parseInt(id.getText());
    String titleToUpdate = title.getText();
    String descriptionToUpdate = Description.getText();
    String locationToUpdate = LocationID.getText();
    String typeToUpdate = type.getText();
    String contactNameToUpdate = Contactoptions.getValue();
    int contactIDToUpdate = 0;
    ObservableList<Contacts> contactsObservableList = DatabaseContacts.getDBContacts();
            for (Contacts contact : contactsObservableList) {
        if (contactNameToUpdate.equals(contact.getName())) {
            contactIDToUpdate = contact.getId();
        }
    }
    LocalDate start_date = StartDate.getValue();
    LocalDate end_date = EndDate.getValue();
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    String st = String.valueOf(Starttime.getValue());
    String et = String.valueOf(EndTime.getValue());
    LocalTime start_time = LocalTime.parse(st, timeFormatter);

    LocalTime end_time = LocalTime.parse(et, timeFormatter);

    LocalDateTime start_time_Update = LocalDateTime.of(start_date, start_time);
    LocalDateTime end_time_Update = LocalDateTime.of(end_date, end_time);
    ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());






    ZonedDateTime start_timeTo_zonetime = ZonedDateTime.of(start_time_Update, ZoneId.systemDefault());
    ZonedDateTime zonetime_est = start_timeTo_zonetime.withZoneSameInstant(ZoneId.of("America/New_York"));
    LocalTime Appointment_time = zonetime_est.toLocalTime();

        if (Appointment_time.getHour() < 8 || Appointment_time.getHour() > 22 )
        {
            errorMessage("Start Time is outside of business hours \n  Please schedule between 08:00 and 22:00 EST.");
            return;
        }
    DayOfWeek Appointment_day = zonetime_est.toLocalDate().getDayOfWeek();
    int CheckAppointment_day = Appointment_day.getValue();

    ZonedDateTime end_timeTo_zoneTime = ZonedDateTime.of(end_time_Update, ZoneId.systemDefault());
    ZonedDateTime zoneTimeTo_est = end_timeTo_zoneTime.withZoneSameInstant(ZoneId.of("America/New_York"));
    LocalTime end_timeTo_check = zoneTimeTo_est.toLocalTime();

        if (end_timeTo_check.getHour() < 8 || end_timeTo_check.getHour() > 22 )
        {
            errorMessage("End Time is outside of business hours \n Please schedule between 08:00 and 22:00 EST.");
            return;
        }

    DayOfWeek end_dayToCheck = zoneTimeTo_est.toLocalDate().getDayOfWeek();
    int endAppointmentDayToCheckInt = end_dayToCheck.getValue();

    LocalTime StartWorkHours = LocalTime.of(8, 0, 0);
    LocalTime EndWorkHours = LocalTime.of(22, 0, 0);
    int Monday1 = DayOfWeek.MONDAY.getValue();
    int Friday1 = DayOfWeek.FRIDAY.getValue();

            if (CheckAppointment_day < Monday1 || CheckAppointment_day > Friday1 ||
    endAppointmentDayToCheckInt < Monday1 || endAppointmentDayToCheckInt > Friday1) {
        errorMessage("Day is outside of business hours Please schedule between Monday and Friday");
        return;
    }
    Timestamp lastUpdateToUpdate = Timestamp.valueOf(LocalDateTime.now());
    String lastUpdatedByToUpdate = "admin";
    int customerIDToUpdate = 0;
    if (customerIDToUpdate == 0)
            try {
                customerIDToUpdate = Integer.parseInt(Customer_id.getText());
            }catch (NumberFormatException e) {
                errorMessage(" Incorrect Format for the Customer or User Text Field \n appointment for customer ");
            }
    // overlapping appointments -- requirement 3D
    ObservableList<Appointments> allAppointments = DatabaseAppointments.getDBAppointments();
    int CurrAppointment_id = 0;
            if (MonthlyTab.isSelected()) {
        CurrAppointment_id = MonthlyTableView.getSelectionModel().getSelectedItem().getId();
    }
            if (weeklyTab.isSelected()) {
        CurrAppointment_id = WeeklyTableView.getSelectionModel().getSelectedItem().getId();

    }
            if (allappointmenttab.isSelected()) {
        CurrAppointment_id = allappointmenttableveiw.getSelectionModel().getSelectedItem().getId();
    }
            for (Appointments appointment : allAppointments) {
        LocalDateTime start_Times = appointment.getStartDate();
        LocalDateTime End_times = appointment.getEndDate();
        int Check_C_IDs = appointment.getCustomerId();
        if ((appointment.getId() != CurrAppointment_id) &&
                (customerIDToUpdate == Check_C_IDs) &&
                (start_time_Update.isEqual(start_Times) ||
                        start_time_Update.isAfter(start_Times)) &&
                (start_time_Update.isEqual(End_times) ||
                        start_time_Update.isBefore(End_times))) {
            errorMessage("New start time overlaps with existing appointment for customer");
            return;
        }
        if ((appointment.getId() != CurrAppointment_id) &&
                (customerIDToUpdate == Check_C_IDs) &&
                (end_time_Update.isEqual(start_Times) ||
                        end_time_Update.isAfter(start_Times)) &&
                (end_time_Update.isEqual(End_times) ||
                        end_time_Update.isBefore(End_times))) {
           errorMessage("New end time overlaps with existing appointment for customer \n Appointment Not Updated\"");

            return;
        }
    }
        if (UpdateUser == 1 || UpdateUser == 2)
            try {
                UpdateUser = Integer.parseInt(user_id.getText());
                String sql_update = "UPDATE appointments SET Appointment_ID = ?, Title = ?, Description = ?, " +
                        "Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, " +
                        "Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
                DBQuery.setPreparedStatement(DatabaseConnection.getConnection(), sql_update);
                PreparedStatement preStatement = DBQuery.getPreparedStatement();
                preStatement.setInt(1, appointmentIDToUpdate);
                preStatement.setString(2, titleToUpdate);
                preStatement.setString(3, descriptionToUpdate);
                preStatement.setString(4, locationToUpdate);
                preStatement.setString(5, typeToUpdate);
                preStatement.setTimestamp(6, Timestamp.valueOf(start_time_Update));
                preStatement.setTimestamp(7, Timestamp.valueOf(end_time_Update));
                preStatement.setTimestamp(8, lastUpdateToUpdate);
                preStatement.setString(9, lastUpdatedByToUpdate);
                preStatement.setInt(10, customerIDToUpdate);
                preStatement.setInt(11, UpdateUser);
                preStatement.setInt(12, contactIDToUpdate);
                preStatement.setInt(13, appointmentIDToUpdate);
                preStatement.execute();
                DisplayDatatotableview();
                System.out.println("Appointment Name: " + titleToUpdate + " Appointment ID: "+ appointmentIDToUpdate+ " Appointment Type: " + typeToUpdate + " was updated in database ");
                displayAppointmentFields("Appointment Name: " + titleToUpdate + " Appointment ID: "+ appointmentIDToUpdate+ " Appointment Type: " + typeToUpdate + " was updated in database ");
                ClearAppointmentFields();
            }catch (Exception e) {
                errorMessage("Must have a vaild customer ID \n appointment for customer");
            }
     else {
        errorMessage("User ID Doesn't Exist ");
        return;
    }

    }
    public void displayAppointmentFields(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Adding Appointment for Customer");
        alert.setHeaderText(" Appointment has been added or updated");
        alert.setContentText(message);
        alert.showAndWait();}

    public void deleteAppointment(ActionEvent actionEvent) throws SQLException {
        String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";
        DBQuery.setPreparedStatement(DatabaseConnection.getConnection(), deleteStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        int Appointment_ToDelete = 0;
        String Typeof_appointment = "";
        if (MonthlyTab.isSelected()) {
            Appointment_ToDelete = MonthlyTableView.getSelectionModel().getSelectedItem().getId();
            Typeof_appointment = MonthlyTableView.getSelectionModel().getSelectedItem().getType();
        }
        if (weeklyTab.isSelected()) {
            Appointment_ToDelete = WeeklyTableView.getSelectionModel().getSelectedItem().getId();
            Typeof_appointment = WeeklyTableView.getSelectionModel().getSelectedItem().getType();
        }
        if (allappointmenttab.isSelected()) {
            Appointment_ToDelete = allappointmenttableveiw.getSelectionModel().getSelectedItem().getId();
            Typeof_appointment = allappointmenttableveiw.getSelectionModel().getSelectedItem().getType();
        }
        ps.setInt(1, Appointment_ToDelete);
        ps.execute();
        DisplayDatatotableview();
        System.out.println("Appointment ID: " + Appointment_ToDelete +" Appointment Type: "+Typeof_appointment + " deleted from database");
        errorMessage("Appointment ID: " + Appointment_ToDelete +" Appointment Type: "+Typeof_appointment + " deleted from database");
        ClearAppointmentFields();
    }
    /**
     * Customer controller method to move between scenes
     * @param event customer
     */
    public void Customer(ActionEvent event) {
        ((((Button) event.getSource()).getScene().getWindow())).hide();
        new LoadScene().loadPage(new Stage(), FxmlNames.CUSTOMER, FxmlNames.CUSTOMER_TITLE);
    }
    /**
     * Report controller method to move between scenes
     * @param event reports
     */
    public void Reportsloader(ActionEvent event) {
        ((((Button) event.getSource()).getScene().getWindow())).hide();
        new LoadScene().loadPage(new Stage(), FxmlNames.REPORTS, FxmlNames.REPORTS_TITLE);
    }

}

