import common.GyanSetuContex;
import common.WebBrowser;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    // Creates an Instance of a Browser
    protected WebBrowser browser;


    // Runs Before a test Class and loads Class Properties
    @BeforeClass
    public void beforeClass() {
        browser = new WebBrowser();
        GyanSetuContex.SINGLETON.setEntry("browser", browser);
    }


}
