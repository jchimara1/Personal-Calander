package Reports;
/**
 *  class for the report by type
 *  created by justice chimara
 */
public class ReportByType {
    public String appointmentType;
    public int appointmentTotal;

    /**
     *
     * @param appointmentType Setter
     * @param appointmentTotal Setter
     */
    public ReportByType(String appointmentType, int appointmentTotal) {
        this.appointmentType = appointmentType;
        this.appointmentTotal = appointmentTotal;
    }

    /**
     *
     * getters for appointment type
     * @return Appointment type
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     *
     * getters for appointment total
     * @return AppointmentTotal
     */
    public int getAppointmentTotal() {
        return appointmentTotal;
    }

}
