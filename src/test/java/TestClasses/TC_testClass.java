package TestClasses;

import BaseTest.PageObject;
import BaseTest.PageTest;
import Objects.Google_Home;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TC_testClass extends PageTest {

    private Google_Home google_home;

    void initialize() throws Exception {
        google_home=(Google_Home) PageObject.getInstance("Google_Home");
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        initialize();
    }

    @Test
    public void First_Test() throws Exception {
        System.out.println("First Test Case");
        google_home.launchURL("https://www.google.co.in/?gws_rd=ssl");
    }

}
