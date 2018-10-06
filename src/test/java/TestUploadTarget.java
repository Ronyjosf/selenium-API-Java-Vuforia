// Selenium - chrome driver ( with POM example )
import com.oracle.tools.packager.Log;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.MalformedURLException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUploadTarget {
    static WebDriver driver;

    // POM example:
    By loginEmail = By.id("login_email");

    @BeforeClass
    public static void init() throws MalformedURLException {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/chromedriver");
        driver = new ChromeDriver();


    }

    boolean isTargetExists(String name)
    {
        if (driver.findElements(By.partialLinkText("cylinder")).size()!=0)
            return true;
        return false;
    }
    public WebElement waitForElement(String by, String name) throws InterruptedException {
        WebElement element = null;
        int maxCount = 10;
        int count = 0;
        while (count < maxCount) {
            try {
                switch (by) {
                    case "id":
                        element= driver.findElement(By.id(name));
                        count = maxCount;
                    break;
                    case "partialLinkText":
                        element= driver.findElement(By.partialLinkText(name));
                        count = maxCount;
                    break;
                    case "class":
                        element= driver.findElement(By.className(name));
                        count = maxCount;
                    break;

                }

            } catch (NoSuchElementException e) {
                Thread.sleep(1000);
            }
            count++;
        }
        if (element!=null)
            return element;
        throw new NoSuchElementException("couldn't find element: "+name);
    }
    @Test
    public void aUploadFile(){
        Log.debug("uploadFile");
    }

    @Test
    public void bLaunchWeb(){
        driver.get("https://developer.vuforia.com/license-manager");
    }

    @Test
    public void cLogIn(){
        Log.debug("##### method name : " + Thread.currentThread().getStackTrace()[2].getMethodName() + " starting ");
        WebElement login = driver.findElement(By.id("vuforiaLogin"));
        login.click();

//        WebElement loginEmail = driver.findElement(By.id("login_email"));
//        loginEmail.sendKeys("rgalamidi@gmail.com");

        // working with POM
        driver.findElement(loginEmail).sendKeys("rgalamidi@gmail.com");


        WebElement loginPasw = driver.findElement(By.id("login_password"));
        loginPasw.sendKeys("Moopy100");
        Log.debug("##### method name : " + Thread.currentThread().getStackTrace()[2].getMethodName() + " starting ");

        WebElement LoginBtn = driver.findElement(By.id("login"));
        LoginBtn.click();

        driver.findElements(By.className("userNameInHeader"));
        if (driver.findElements(By.className("userNameInHeader")).size()==0)
            throw new NoSuchElementException("couldn't find element");
    }
    @Test
    public void dAddTarget() throws InterruptedException {
        WebElement targetManager = null;
        targetManager = waitForElement("partialLinkText", "Target");
        if (targetManager!=null)
            targetManager.click();
        else throw new NoSuchElementException("couldn't find element");

        try {
            waitForElement("partialLinkText", "DB").click();
        }catch (NotFoundException e){}
//        driver.findElement(By.partialLinkText("DB")).click();

        // delete all
        if (isTargetExists("cylinder")){
            driver.findElement(By.id("selectall")).click();
            driver.findElement(By.cssSelector("a.selection-action-delete")).click();
            driver.findElement(By.id("deleteDeviceDB")).click();
        }
        try {
            waitForElement("id", "addDeviceTargetUserView").click();
        }catch (Exception e) {
            Log.debug(e);
        }

        // add r
        WebElement targetImgFile = driver.findElement(By.id("targetImgFile"));
        targetImgFile.sendKeys("/Users/Rony/Downloads/cylinder.jpg");
        WebElement targetDimension = driver.findElement(By.id("targetDimension"));
        if (targetDimension.isDisplayed())
            targetDimension.sendKeys("5");
        if (waitForElement("id", "AddDeviceTargetBtn").isDisplayed())
            waitForElement("id", "AddDeviceTargetBtn").click();

        // verify new target exists ( look for the class target-status, since refresh by code doesn't refresh )
        if (waitForElement("class", "target-status")==null)
            throw new NoSuchElementException("add target fail!");
//        if (!isTargetExists("cylinder")) {
//        }


    }
    @Test
    public void eAgetStatus(){

    }

    @AfterClass
    public static void tearDown(){
//        driver.close();
        try {
            Thread.sleep(5000); // just to see something
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}
