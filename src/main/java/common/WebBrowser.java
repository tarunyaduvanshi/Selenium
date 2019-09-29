package common;


import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class WebBrowser {

    private final WebDriver driver;
    private final int IMPLICIT_WAIT_TIME = 10; // Seconds
    private final int EXPLICIT_WAIT_TIME = 10; // Seconds
    private final String startingWindow;
    private ArrayList<String> windows = new ArrayList<String>(); // List of All Open Windows
    private Alert alert;
    private String myWindowHandle;

    /*** Default Constructor **/
    public WebBrowser() {
        driver = DriverFactory.getInstance().buildWebDriver();
        setImpicitWait(IMPLICIT_WAIT_TIME);
        maximizeScreen();
        startingWindow = getCurrentWindowHandle();
        windows.add(getCurrentWindowHandle());
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void maximizeScreen() {
        driver.manage().window().maximize();
    }

    public WebPage navigateToUrl(String url, String pageClassName) throws InterruptedException {
        driver.get(url);
        Thread.sleep(3000); // TODO Why is this sleep needed?
        return makeWebPage(pageClassName);
    }

    public String executeJSAndGetResponse(String jsCommand) {
        JavascriptExecutor jexec = (JavascriptExecutor) driver;
        return (String) jexec.executeScript(jsCommand);

    }

    /**
     * Build an instance corresponding to Mettl's web page. Use this method if you have already
     * navigated to the page.
     *
     * @param pageClassName
     * @return
     */
    public WebPage makeWebPage(String pageClassName) {
        try {
            Class<WebPage> pageClass = (Class<WebPage>) Class.forName(pageClassName);
            WebPage page = PageFactory.initElements(driver, pageClass);
            page.setBrowser(this);
            return page;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to create Page instance for " + pageClassName);
        }
    }

    public void setImpicitWait(Integer timeInSeconds) {
        driver.manage().timeouts().implicitlyWait((int) timeInSeconds, TimeUnit.SECONDS);
    }


    public String getCurrentWindowHandle() {
        return driver.getWindowHandle();
    }


}
