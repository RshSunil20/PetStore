package stepDefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class ReportInterface {

    @Before
    public void setupChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "/home/chrome32/chromedriver.exe");
    }

    @After
    protected void tearDown() {
        driver.quit();
        driver = null;
    }

    public static WebDriver driver = new ChromeDriver();


    @Given("^user has access to the report generator application interface$")
    public void user_has_access_to_the_report_generator_application_interface() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        driver.navigate().to("My_Generate_Report_App");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }

    @When("^user inputs the Start and End date as today$")
    public void user_inputs_the_Start_and_End_date_as_today() throws Throwable {

        WebElement startDate = driver.findElement(By.id("StartDate")).click();
        startDate.clear();
        startDate.sendKeys("18-08-2019");

        WebElement endDate = driver.findElement(By.id("EndDate")).click();
        endDate.clear();
        endDate.sendKeys("18-08-2019");

    }

    @When("^user inputs the input file location with correct text file and click Generate button$")
    public void user_inputs_the_input_file_location_with_correct_text_file() throws Throwable {

        WebElement fileInput = driver.findElement(By.name("Location of input files"));
        fileInput.sendKeys("\\srv01\\inputFiles\\Canada_2017-05-12__13_24_22.txt");

        WebElement logInput = driver.findElement(By.name("Log Folder"));
        logInput.sendKeys("D:\\Logs");

        WebElement generate = driver.findElement(By.cssSelector(".btn"));
        generate.click();

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.alertIsPresent());

    }


    @Then("^Success message is displayed and Report is generated in the Report table$")
    public void success_message_is_displayed_and_Report_is_generated_in_the_Report_table() throws Throwable {


        String parentWindowHandler = driver.getWindowHandle();
        String subWindowHandler = null;

        Set<String> handles = driver.getWindowHandles();
        Iterator<String> iterator = handles.iterator();
        while (iterator.hasNext()) {
            subWindowHandler = iterator.next();
        }
        driver.switchTo().window(subWindowHandler); // switch to popup window

        Alert alert = driver.switchTo().alert();
        String message = alert.getText();

        Assert.assertEquals("Report generated succesfully", message); //Asserting success message if present else fails
        alert.accept();

        driver.switchTo().window(parentWindowHandler);
        throw new PendingException();
    }


    @And("^log file is generated in the Log folder$")
    public void log_file_is_generated_in_the_Log_folder() throws Throwable {

        String logfileName = new SimpleDateFormat("Log_yyyy-MM-dd__HH-mm-ss").format(new Date());
        File folder = new File("D:\\Logs\\");

        String fileName = folder.getName();

        if(fileName == null)
        {
            fail();
        }
        else
        {
            Assert.assertEquals(fileName, logfileName); // Compare the expected Logfile name
        }

    }

}