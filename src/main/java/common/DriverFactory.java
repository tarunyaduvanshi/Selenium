package common;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class DriverFactory {
    private static DriverFactory factory = new DriverFactory();
    private String path;

    /*** Private Constructor */
    private DriverFactory() {
    }

    /*** Gets Singleton instance */
    public static DriverFactory getInstance() {
        return factory;
    }

    /*** Builds a web driver instance based on the the configured browser and the OS **/
    public WebDriver buildWebDriver() {
        WebDriver webDriver;
        String OSNAme = System.getProperty("os.name").toLowerCase();
        switch (GyanSetuContex.SINGLETON.getEntryAsString("browserName")) {
            case "Firefox":
                webDriver = makeFirefoxDriver(OSNAme); // Firefox runs on all OSs
                break;
            case "Chrome":
                webDriver = makeChromeDriver(OSNAme); // Chrome runs on all OSs
                break;
            case "IE":
                webDriver = makeIEDriver(); // IE runs on Windows Only
                break;
            case "Edge":
                webDriver = makeEdgeDriver(); // Edge runs on Windows Only
                break;
            case "Safari":
                webDriver = makeSafariDriver(); // Safari runs on OSX Only
                break;
            case "HTMLUnit":
                webDriver = makeHTMLUnitDriver(); // Safari runs on OSX Only
                break;
            default:
                throw new RuntimeException("Browser not supported");
        }
        return webDriver;
    }

    private WebDriver makeIEDriver() {

        /* Supported Command Line Options - https://docs.microsoft.com/en-us/previous-versions/windows/internet-explorer/ie-developer/general-info/hh826025(v=vs.85)
            https://github.com/SeleniumHQ/selenium/wiki/InternetExplorerDriver */

        InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
        internetExplorerOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        internetExplorerOptions.setCapability("ignoreZoomSetting", true);
        internetExplorerOptions.setCapability("nativeEvents", false);
        try {
            File driver = File.createTempFile("driver", ".tmp");
            FileUtils.copyInputStreamToFile(this.getClass().getClassLoader().getResourceAsStream("drivers/win/MicrosoftWebDriver.exe"), driver);
            System.setProperty("webdriver.ie.driver", driver.getPath());
            driver.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new InternetExplorerDriver(internetExplorerOptions);
    }

    private WebDriver makeEdgeDriver() {

        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        edgeOptions.setCapability("ignoreZoomSetting", true);
        edgeOptions.setCapability("nativeEvents", false);
        try {
            File driver = File.createTempFile("driver", ".tmp");
            FileUtils.copyInputStreamToFile(this.getClass().getClassLoader().getResourceAsStream("drivers/win/MicrosoftWebDriver.exe"), driver);
            System.setProperty("webdriver.edge.driver", driver.getPath());
            driver.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new EdgeDriver(edgeOptions);
    }

    private WebDriver makeChromeDriver(String OSName) {
        /* Full List of Arguments available here - https://peter.sh/experiments/chromium-command-line-switches/
        List of Capabilities Supported available here - http://chromedriver.chromium.org/capabilities
        Currently we are not using any Capabilities */

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--auto-select-desktop-capture-source=Entire screen");
        chromeOptions.addArguments("--use-fake-ui-for-media-stream");
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--disable-timezone-tracking-option");

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("plugins/hkjemkcbndldepdbnbdnibeppofoooio_main.crx");
        try {
            File crx = File.createTempFile("crx", "", new File(System.getProperty("user.dir")));
            File driver = File.createTempFile("driver", "", new File(System.getProperty("user.dir")));
            FileUtils.copyInputStreamToFile(in, crx);
            chromeOptions.addExtensions(crx);

            if (OSName.contains("win")) {
                FileUtils.copyInputStreamToFile(this.getClass().getClassLoader().getResourceAsStream("drivers/win/chromedriver.exe"), driver);
            } else if (OSName.contains("mac")) {
                FileUtils.copyInputStreamToFile(this.getClass().getClassLoader().getResourceAsStream("drivers/mac/chromedriver"), driver);
                driver.setExecutable(true, false);
            } else {
                chromeOptions.addArguments("--headless");
                FileUtils.copyInputStreamToFile(this.getClass().getClassLoader().getResourceAsStream("drivers/unix/chromedriver"), driver);
                driver.setExecutable(true, false);
            }
            System.setProperty("webdriver.chrome.driver", driver.getPath());
            crx.deleteOnExit();
            driver.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ChromeDriver(chromeOptions);
    }

    private WebDriver makeFirefoxDriver(String OSName) {

        /* Full List of Preference available here - http://kb.mozillazine.org/About:config_entries and http://kb.mozillazine.org/Category:Preferences
            List of Capabilities available here - https://github.com/mozilla/geckodriver
            List of Arguments - http://kb.mozillazine.org/Command_line_arguments   */

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setAcceptInsecureCerts(true);
        firefoxOptions.addPreference("security.enable_java", true);
        firefoxOptions.addPreference("plugin.state.java", 2);
        firefoxOptions.addPreference("media.navigator.permission.disabled", true);
        firefoxOptions.addPreference("security.insecure_password.ui.enabled", false);
        firefoxOptions.addPreference("security.insecure_field_warning.contextual.enabled", false);
        firefoxOptions.addPreference("media.navigator.streams.fake", false);
        firefoxOptions.addPreference("auto-select-desktop-capture-source='Entire screen' ", true);

        try {
            File driver = File.createTempFile("driver", "", new File(System.getProperty("user.dir")));
            if (OSName.contains("win"))
                FileUtils.copyInputStreamToFile(this.getClass().getClassLoader().getResourceAsStream("drivers/win/geckodriver.exe"), driver);
            else if (OSName.contains("mac")) {
                FileUtils.copyInputStreamToFile(this.getClass().getClassLoader().getResourceAsStream("drivers/mac/geckodriver"), driver);
                driver.setExecutable(true, false);
            } else {
                driver.setExecutable(true, false);
                FileUtils.copyInputStreamToFile(this.getClass().getClassLoader().getResourceAsStream("drivers/unix/geckodriver"), driver);
            }
            System.setProperty("webdriver.gecko.driver", driver.getPath());
            driver.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Disable GeckoDriver logs to be printed to console
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        return new FirefoxDriver(firefoxOptions);
    }

    private WebDriver makeSafariDriver() {
        return new SafariDriver();
    }

    private WebDriver makeHTMLUnitDriver() {
        return new HtmlUnitDriver();
    }
}
