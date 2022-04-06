package Reports;

/** class for the report by month view
 * Created by justiceChimara
 */
public class ReportByMonth {
    public String appointmentMonth;
    public int appointmentTotal;


    public ReportByMonth(String appointmentMonth, int appointmentTotal) {
        this.appointmentMonth = appointmentMonth;
        this.appointmentTotal = appointmentTotal;
    }

    /** getters for the report by month tableview
     *
     * @return AppointmentMonth
     */
    public String getAppointmentMonth() {
        return appointmentMonth;
    }

    /**
     *
     * @return the appointment total
     */
    public int getAppointmentTotal() {
        return appointmentTotal;
    }
}
