package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Driver {
    private static WebDriver driver;
    private static int timeout = 5;

    private Driver() {
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = ConfigReader.getProperty("browser");
            if ("chrome".equals(browser)) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            } else if ("firefox".equals(browser)) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            } else if ("ie".equals(browser)) {
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
            } else if ("safari".equals(browser)) {
                WebDriverManager.getInstance(SafariDriver.class).setup();
                driver = new SafariDriver();
            } else if ("chrome-headless".equals(browser)) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver((ChromeOptions)(new ChromeOptions()).setHeadless(true));
            }
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
        driver.manage().window().maximize();
        return driver;
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }

    }

    public static void waitAndClick(WebElement element, int timeout) {
        int i = 0;

        while(i < timeout) {
            try {
                element.click();
                return;
            } catch (WebDriverException var4) {
                wait(1);
                ++i;
            }
        }

    }

    public static void waitAndClick(WebElement element) {
        int i = 0;

        while(i < timeout) {
            try {
                element.click();
                return;
            } catch (WebDriverException var3) {
                wait(1);
                ++i;
            }
        }

    }

    public static void waitAndSendText(WebElement element, String text, int timeout) {
        int i = 0;

        while(i < timeout) {
            try {
                element.sendKeys(new CharSequence[]{text});
                return;
            } catch (WebDriverException var5) {
                wait(1);
                ++i;
            }
        }

    }

    public static void waitAndSendText(WebElement element, String text) {
        int i = 0;

        while(i < timeout) {
            try {
                element.sendKeys(new CharSequence[]{text});
                return;
            } catch (WebDriverException var4) {
                wait(1);
                ++i;
            }
        }

    }

    public static void waitAndSendTextWithDefaultTime(WebElement element, String text) {
        int i = 0;

        while(i < timeout) {
            try {
                element.sendKeys(new CharSequence[]{text});
                return;
            } catch (WebDriverException var4) {
                wait(1);
                ++i;
            }
        }

    }

    public static String waitAndGetText(WebElement element, int timeout) {
        String text = "";
        int i = 0;

        while(i < timeout) {
            try {
                text = element.getText();
                return text;
            } catch (WebDriverException var5) {
                wait(1);
                ++i;
            }
        }

        return null;
    }

    public static void wait2(int sec) {
        try {
            Thread.sleep((long)(1000 * sec));
        } catch (NoSuchElementException var2) {
            var2.printStackTrace();
        } catch (TimeoutException var3) {
            var3.printStackTrace();
        } catch (StaleElementReferenceException var4) {
            var4.printStackTrace();
        } catch (ElementClickInterceptedException var5) {
            var5.printStackTrace();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public static void waitAndClickElement(WebElement element, int seconds) {
        int i = 0;

        while(i < seconds) {
            try {
                element.click();
                break;
            } catch (Exception var4) {
                wait2(1);
                ++i;
            }
        }

    }

    public static void wait(int secs) {
        try {
            Thread.sleep((long)(1000 * secs));
        } catch (InterruptedException var2) {
            var2.printStackTrace();
        } catch (TimeoutException var3) {
            var3.printStackTrace();
        } catch (NoSuchElementException var4) {
            var4.printStackTrace();
        } catch (StaleElementReferenceException var5) {
            var5.printStackTrace();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds((long)timeout));
        return (WebElement)wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds((long)timeout));
        return (WebElement)wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static Boolean waitForInVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds((long)timeout));
        return (Boolean)wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds((long)timeout));
        return (WebElement)wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds((long)timeout));
        return (WebElement)wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };

        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds((long)timeout));
            wait.until(expectation);
        } catch (Exception error) {
            error.printStackTrace();
        }

    }

    public static void executeJScommand(WebElement element, String command) {
        JavascriptExecutor jse = (JavascriptExecutor)getDriver();
        jse.executeScript(command, new Object[]{element});
    }

    public static void selectAnItemFromDropdown(WebElement item, String selectableItem) {
        wait(5);
        Select select = new Select(item);

        for(int i = 0; i < select.getOptions().size(); ++i) {
            if (((WebElement)select.getOptions().get(i)).getText().equalsIgnoreCase(selectableItem)) {
                ((WebElement)select.getOptions().get(i)).click();
                break;
            }
        }

    }

    public static void clickWithJS(WebElement element) {
        ((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView(true);", new Object[]{element});
        ((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", new Object[]{element});
    }

    public static void clickWithJSAsList(List<WebElement> elements) {
        Iterator var1 = elements.iterator();

        while(var1.hasNext()) {
            WebElement each = (WebElement)var1.next();
            ((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView(true);", new Object[]{waitForVisibility((WebElement)each, 5)});
            ((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", new Object[]{each});
        }

    }

    public static void doubleClick(WebElement element) {
        (new Actions(getDriver())).doubleClick(element).build().perform();
    }

    public static void selectByVisibleText(WebElement element, String text) {
        Select objSelect = new Select(element);
        objSelect.selectByVisibleText(text);
    }

    public static void selectByIndex(WebElement element, int index) {
        Select objSelect = new Select(element);
        objSelect.selectByIndex(index);
    }

    public static void selectByValue(WebElement element, String value) {
        Select objSelect = new Select(element);
        List<WebElement> elementCount = objSelect.getOptions();
        objSelect.selectByValue(value);
        System.out.println("number of elements: " + elementCount.size());
    }

    public static void sleep(int timeOut) {
        try {
            Thread.sleep((long)timeOut);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public static void waitAndClickLocationText(WebElement element, String value) {
        getDriver().findElement(By.xpath("//*[text()='" + value + "']")).click();
    }
}