package Reports;

import java.util.ArrayList;

/**
 * creates the Report By Division Model
 */
public class ReportByDivision {
    public String firstLevelDivision;
    public ArrayList<String> customerList;

    /**
     *
     * @param firstLevelDivision Division setter
     * @param customerList customerList setter
     */
    public ReportByDivision(String firstLevelDivision, ArrayList<String> customerList) {
        this.firstLevelDivision = firstLevelDivision;
        this.customerList = customerList;
    }

    /**
     *
     * @return getter for the  Division Level
     */
    public String getFirstLevelDivision() {
        return firstLevelDivision;
    }


    public ArrayList<String> getCustomerList() {
        return customerList;
    }
}
