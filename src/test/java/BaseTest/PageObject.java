package BaseTest;

import Utils.CONSTANTS;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class PageObject {

   public static WebDriver driver= PageTest.driver;
   public static ExtentTest logger= PageTest.logger;

   public static Object getInstance(String classname) {
       driver=PageTest.driver;
       Object classInstance=null;
       try {
           Class<?> cn = Class.forName("Objects."+classname);
           Constructor con= cn.getConstructor();
           classInstance= con.newInstance();
       } catch (Exception e) {
           System.out.println("Exception in class creation");
       }
       return classInstance;
   }

   public void launchURL(String URL) throws Exception {
       driver.get(URL);
       driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
       driver.manage().window().maximize();
       logger.log(LogStatus.PASS,"URL is open "+URL);
   }

   public void waitforPageLoad() {
       try {
           ExpectedCondition<Boolean> pageLoad= new ExpectedCondition<Boolean>() {
               @NullableDecl
               public Boolean apply(@NullableDecl WebDriver webDriver) {
                   return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
               }
           };
           WebDriverWait wait = new WebDriverWait(driver, CONSTANTS.PageLoad);
           wait.until(pageLoad);
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Page is not loaded");
           Assert.fail("Wait for Page Load is failed");
       }

   }

   public void implicitwait() {
       try {
           driver.manage().timeouts().implicitlyWait(CONSTANTS.ImplicitWait, TimeUnit.SECONDS);
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Implicit Wait is failed");
           Assert.fail("Implicit Wait");
       }

   }

   public void waitForElementVisibility(WebElement element) {
       try {
           WebDriverWait wait= new WebDriverWait(driver,CONSTANTS.ExplicitWait);
           wait.until(ExpectedConditions.visibilityOf(element));
           logger.log(LogStatus.PASS,"Webelement is visible");
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Webelement is not visible");
           Assert.fail("Webelement is not visible");
       }
   }

   public void waitForElementisClickable(WebElement element) {
       try {
           WebDriverWait wait = new WebDriverWait(driver,CONSTANTS.ExplicitWait);
           wait.until(ExpectedConditions.elementToBeClickable(element));
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Webelement is not clickable");
           Assert.fail("Webelemnt is not cluckable");
       }
   }

   public void waitForElementStaleness(WebElement element) {
       try  {
           WebDriverWait wait = new WebDriverWait(driver,CONSTANTS.ExplicitWait);
           wait.until(ExpectedConditions.stalenessOf(element));
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Webelement is not visible");
           Assert.fail("Webelement is not visible");
       }
   }

   public boolean is_selected(WebElement element) {
       boolean flag= false;
       try  {
           waitForElementVisibility(element);
           flag=element.isSelected();
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Webelement is not selected");
           Assert.fail("Webelement is not selected");
       }
       return flag;
   }

   public boolean is_displayed(WebElement element) {
       boolean flag=false;
       try {
           flag=element.isDisplayed();
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Webelement is not present");
           Assert.fail("Webelement is not present");
       }
       return flag;
   }

   public boolean is_enabled(WebElement element) {
       boolean flag=false;
       try {
           flag=element.isEnabled();
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Webelement is not enabled");
           Assert.fail("Webelement is not enabled");
       }
       return flag;
   }

   public void elementClick(WebElement element) {
       try {
           waitForElementVisibility(element);
           waitForElementisClickable(element);
           element.click();
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Webelement is not clicked");
           Assert.fail("Exception in element click");
       }
   }

   public void javascriptClick(WebElement element) {
       try {
           waitForElementVisibility(element);
           waitForElementisClickable(element);
           ((JavascriptExecutor)driver).executeScript("argument[0].style.border=3px solid red;",element);
           ((JavascriptExecutor)driver).executeScript("argument[0].click();",element);
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Exception in JavaScript Click");
           Assert.fail("Exception in JavaScript Click");
       }
   }

   public void RightClick(WebElement element) {
       try  {
           waitForElementVisibility(element);
           waitForElementisClickable(element);
           Actions action = new Actions(driver);
           action.contextClick(element).build().perform();
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Exeception in Right Click");
           Assert.fail("Exception in Right Click");
       }
   }

   public void doubleClick(WebElement element) {
       try {
           waitForElementVisibility(element);
           waitForElementisClickable(element);
           Actions action = new Actions(driver);
           action.doubleClick(element).perform();
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Exception in Double Click");
           Assert.fail("Exception in Double Click");
       }
   }

   public void selectFrame_UisngString(String faremId) throws Exception {
       try {
           WebDriverWait wait = new WebDriverWait(driver,CONSTANTS.ExplicitWait);
           wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(faremId));
       } catch(Exception e) {
           logger.log(LogStatus.FAIL,"Exception in selecting Frame");
           Assert.fail("Exception in selecting Frame");
       }
   }

   public void selectFrameUsingId(int frameID) throws Exception {
       try {
           WebDriverWait wait = new WebDriverWait(driver,CONSTANTS.ExplicitWait);
           wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameID));
       } catch(Exception e) {
           logger.log(LogStatus.FAIL,"Exception in selecting Frame");
           Assert.fail("Exception in selecting Frame");
       }
   }

   public void selectFrameUsingWebElement(WebElement frame) throws Exception {
       try {
           WebDriverWait wait = new WebDriverWait(driver,CONSTANTS.ExplicitWait);
           wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
       } catch(Exception e) {
           logger.log(LogStatus.FAIL,"Exception in selecting Frame");
           Assert.fail("Exception in selecting Frame");
       }
   }

   public void setText(WebElement element, String text) {
       try {
           element.clear();
           element.sendKeys(text);
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Exception in Set Text");
           Assert.fail("Exception in Set Text");
       }
   }

   public void JavasetText(WebElement element, String text) {
       try {
           ((JavascriptExecutor)driver).executeScript("argument[0].style.border= 3 px solid red;",element);
           element.clear();
           ((JavascriptExecutor)driver).executeScript("argument[0].value=argument[1];",element,text);
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Exception in Set Text");
           Assert.fail("Exception in Set Text");
       }
   }

   public void HighLightWebelement(WebElement element) {
       try {
           ((JavascriptExecutor)driver).executeScript("argument[0].style.border= 3 px solid red;",element);
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Exception in HighLighting Element");
           Assert.fail("Exception in HighLighting Element");
       }
   }

   public void SelectWindowByTitle(String title) throws Exception {
       try {
           Set<String> handles = driver.getWindowHandles();
           for(String s : handles) {
               driver.switchTo().window(s);
               if(driver.getTitle().equals(title)) {
                   break;
               }
           }
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Exception in Windows Select");
           Assert.fail("Exception in Windows Select");
       }
   }

   public void SelectWindowByURL(String URL) {
       try {
           Set<String> handles= driver.getWindowHandles();
           for(String s : handles) {
               driver.switchTo().window(s);
               if(driver.getCurrentUrl().equals(URL)) {
                   break;
               }
           }
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Exception in Windows Select");
           Assert.fail("Exception in Windows Select");
       }
   }

   public void CloseAllWindowsExceptCurrentWindow() {
       try {
           String current = driver.getWindowHandle();
            Set<String> handles= driver.getWindowHandles();
            for(String s : handles) {
                driver.switchTo().window(s);
                if(!driver.getWindowHandle().equals(current)) {
                    driver.close();
                }
            }
            driver.switchTo().window(current);
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Exception in Windows Select");
           Assert.fail("Exception in Windows Select");
       }
   }

   public void javascriptscroll(WebElement element) {
       try {
           ((JavascriptExecutor)driver).executeScript("argument[0].scrollIntoView(true);",element);
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Exception in Scroll");
           Assert.fail("Exception in Scroll");
       }
   }

   public void SelectDropDown(WebElement element, String value) {
       try {
           Select select = new Select(element);
           List<WebElement> options = select.getOptions();
           for(WebElement option : options) {
               if(option.getText().equalsIgnoreCase(value)) {
                   option.click();
               }
           }
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Exception in Select DropDown");
           Assert.fail("Exception in Select DropDown");
       }
   }

   public String getText(WebElement element) {
       String value="";
       try {
           value=value+element.getText();
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Execption in get Text");
           Assert.fail("Exception in get Text");
       }
       return value;
   }

   public String getAttributeValue(WebElement element, String attribute) {
       String value="";
       try {
           value=value+element.getAttribute(attribute);
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Exception in get Attribute Value");
           Assert.fail("Exception in get Attribute Value");
       }
       return value;
   }

   public String getScreenshot() throws Exception {
       String combined="";
       try {
           System.out.println(PageTest.CurrentTestinExecution);
           SimpleDateFormat sdf= new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
           String timestamp=sdf.format(new Date());
           String filepath=CONSTANTS.Screenshots+"//"+PageTest.CurrentTestinExecution+timestamp+"//";
           String filename="Screenshot_"+timestamp+".png";
           combined+=filepath+filename;
           System.out.println(combined);
           File file= new File(combined);
           if(!file.exists()) {
               file.mkdir();
           }
           File temp=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
           FileUtils.copyFile(temp,file);
           logger.log(LogStatus.PASS,"Screenshot taken",logger.addScreenCapture(combined));
       } catch (Exception e) {
           logger.log(LogStatus.FAIL,"Exception in taking Screenshots");
           Assert.fail("Exception in taking Screenshot");
       }
       return combined;
   }
}

