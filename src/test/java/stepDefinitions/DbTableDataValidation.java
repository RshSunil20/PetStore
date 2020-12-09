package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class DbTableDataValidation {

    final String username = "system";
    final String passwd = "password";
    Connection con;
    Statement stmt;
    ResultSet rs;

    @Given("^user has validate credentials for Report database$")
    public void user_has_validate_credentials_for_Report_database() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        System.out.println("\n DB credentials are available.");

    }

    @When("^user enters valid connection details to connect to the Report DB$")
    public void user_enters_valid_connection_details_to_connect_to_the_Report_DB() throws Throwable {

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:report", username, passwd);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @When("^user sucessfully connects to the report DB$")
    public void user_sucessfully_connects_to_the_report_DB() throws Throwable {

        try {
            Statement stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @When("^user submit the query with date range to get records on the given date range$")
    public void user_submit_the_query_with_date_range_to_get_records_on_the_given_date_range() throws Throwable {

        rs = stmt.executeQuery("select * from REPORT where DATE(date)=CURDATE()");
        while (rs.next())
            System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));

    }

    @Then("^user gets the response report data for the given date range$")
    public void user_gets_the_response_report_data_for_the_given_date_range() throws Throwable {

        rs = stmt.executeQuery("select * from REPORT where DATE(date)=CURDATE()");
        int size = rs.getRow();

        //Keeping the assertions here with the expected data from the REPORT table to match
        Assert.assertEquals("expected count", "size");

        con.close();
    }

}