package Objects;

import BaseTest.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Google_Home extends PageObject {
    public Google_Home() {
        PageFactory.initElements(driver,this);
    }

    //================================X-PATHS===========================================================================
    @FindBy(xpath = "gdksjgdakjsd")
    public WebElement Example;

    //===============================METHODS============================================================================

    public void launch_Google_HomePage(String URL) throws Exception {
        launchURL(URL);
    }
}
