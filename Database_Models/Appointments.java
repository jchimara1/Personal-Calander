package Database_Models;
import java.time.LocalDateTime;

/**
 * Creates the Appointment Database Model
 */
public class Appointments {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    public int customerId;
    public int userID;
    public int contactId;

    /**
     *
     * @param id the appointment ID to set
     * @param title the appointment title to set
     * @param description the appointment description to set
     * @param location the appointment location to set
     * @param type the appointment type to set
     * @param startDate the appointment startDate LocalDateTime to set
     * @param endDate the appointment endDate LocalDateTime to set
     * @param customerId the customer ID to set
     * @param userID the user ID to set
     * @param contactId the contact ID to set
     */
    public Appointments(int id, String title, String description,
                        String location, String type, LocalDateTime startDate, LocalDateTime endDate, int customerId,
                        int userID, int contactId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerId = customerId;
        this.userID = userID;
        this.contactId = contactId;
    }

    /**
     *
     * @return the id
     */
    public int getId() {
        return id;
    }


    /**
     *
     * @return the appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return the appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return the appointment location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @return the appointment type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return the startDate LocalDateTime
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     *
     * @return the endDate LocalDateTime
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     *
     * @return the customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     *
     * @return the user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     *
     * @return the contact ID
     */
    public int getContactId() {
        return contactId;
    }

}

