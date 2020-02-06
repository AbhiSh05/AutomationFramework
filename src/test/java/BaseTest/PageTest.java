package BaseTest;

import Utils.CONSTANTS;
import Utils.CreatingEmailTemplate;
import Utils.ExcelUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PageTest {

    public static WebDriver driver;
    public static ExtentReports reports;
    public static ExtentTest logger;
    public static Date startDate;
    public static Date endDate;
    public static String ExtentReportPath;
    public static String CurrentTestinExecution;



    public void setDriver(String browser) throws Exception {
        //Setting up driver
        if(browser.equals("chrome")) {
            driver=initChrome();
        } else if (browser.equals("internetexplorer")){
            driver=initInternetExplorer();
        } else {
            //You can define other browser implementation
        }

    }

    public WebDriver initChrome() throws Exception {
        System.setProperty("webdriver.chrome.driver", CONSTANTS.ChromeDriver);
        WebDriver driver1 = new ChromeDriver();
        return driver1;
    }

    public WebDriver initInternetExplorer() throws Exception {
        System.setProperty("webdriver.ie.driver",CONSTANTS.InternetDriver);
        WebDriver driver1 = new InternetExplorerDriver();
        return driver1;
    }

    @BeforeSuite
    protected synchronized void beforeSuite() throws Exception {
        System.out.println("In Before Suite");
        //Initializing Report
        reports = new ExtentReports("reports.html",true);
        //Creating ExcelFile for Test Summary
        ExcelUtils.CreatingTestSummaryReport();
        //Initializing startDate
        startDate = new Date();
    }

    @BeforeClass
    protected synchronized void beforeclass() throws Exception {
        System.out.println("In Before Class");
    }

    @BeforeMethod
    @Parameters({"browser"})
    protected synchronized void beforeMethod(Method method,String broswer) throws Exception {
        PageTest.CurrentTestinExecution=method.getName();
        KillDriver();
        System.out.println("Starting Test");
        System.out.println("============"+method.getName()+"==================");
        logger = reports.startTest(method.getName());
        setDriver(broswer);
    }

    @AfterMethod
    protected synchronized void afterMethod(Method method, ITestResult result) throws Exception {
        String Methodresult=getResultofMethod(result);
        ExcelUtils.addingTestResultstoTestSummary(method.getName(),Methodresult);
        System.out.println("Test Script Status is "+Methodresult);
        reports.endTest(logger);
    }

    public String getResultofMethod(ITestResult result) {
        String temp;
        if(result.getStatus()==ITestResult.FAILURE) {
            temp="Fail";
        } else {
            temp="Pass";
        }
        return temp;
    }

    @AfterClass
    protected synchronized void afterClass() throws Exception {
        System.out.println("In After Class");
    }

    @AfterSuite
    protected synchronized void afterSuite() throws Exception {
        System.out.println("In After Suite");
        //Flushing out Report and creating Extent Reports
        createReport();
        endDate= new Date();
        KillDriver();
        new CreatingEmailTemplate().createEmailTemplate();
    }

    public void createReport() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        String filename= "ExtentReports"+sdf.format(new Date())+".html";
        String dir= System.getProperty("user.dir");
        String filepath=dir+"/Extent Reports/"+filename;
        reports.flush();
        reports.close();
        FileUtils.copyFile(new File("reports.html"),new File(filepath));
        ExtentReportPath=filepath;
        System.out.println("Extent Report Path is "+filepath);
    }

    public void KillDriver() {
        try {
            if(driver!=null) {
                driver.quit();
            }
            Process p = Runtime.getRuntime().exec("killDriver.bat");
            p.waitFor();
        } catch (Exception e) {
            System.out.println("Exception in Kill Driver");
        }
    }

}
