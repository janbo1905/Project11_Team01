package utilities;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReusableMethods {

    public static String getScreenshot(String name) throws IOException{
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);

        String target = System.getProperty("user.dir") + "/test-output/Screenshots/" + date + ".png";
        File finalDestination = new File(target);

        FileUtils.copyFile(source,finalDestination);
        return target;
    }



    /*
    This method captures sscreenshot of the entire page
     */
    public void takeScreenshotOfTheEntirePage() throws IOException {
//        1. TakeScreenShot class with getScreenShotAs method to capture the screenshot
        File image = ((TakesScreenshot)Driver.getDriver()).getScreenshotAs(OutputType.FILE);
//        2. Create a path to save the image
//        Create a date for giving dynamic name otherwise the screenshots overrides
        String now = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());//getting local date in this format
//                     CURRENT PROJECT FOLDER         foldername   subfoldername imagename
        String path = System.getProperty("user.dir")+"/test-output/Screenshots/"+now+"image.png";
//        3. Save the image in the path as a file
        FileUtils.copyFile(image,new File(path));
//        FileUtils.copyFile(FILE,FILE PATH); COPY FILE TO THAT FILE PATH
    }

    /*
    This method captures screenshot of specific elements
    this method accepts an elements and saves the screenshot of that element in the test-output folder
     */
    public void takeScreenshotOfThisElement(WebElement element) throws IOException {
        File image = element.getScreenshotAs(OutputType.FILE);
        String now = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String path = System.getProperty("user.dir")+"/test-output/ElementScreenshot/"+now+"image.png";
        FileUtils.copyFile(image,new File(path));
    }
    /*
    This method captures the image and returns the path of that image
    RETURN TYPE : static String
    return new File(path).getAbsolutePath();
     */
    public static String takeScreenshotOfTheEntirePageAsString() throws IOException {
        //        1. TakeScreenShot class with getScreenShotAs method to capture the screenshot
        File image = ((TakesScreenshot)Driver.getDriver()).getScreenshotAs(OutputType.FILE);
        //        2. Create a path to save the image
        //        Create a date for giving dynamic name otherwise the screenshots overrides
        String now = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());//getting local date in this format
        //                     CURRENT PROJECT FOLDER         foldername   subfoldername imagename
        String path = System.getProperty("user.dir")+"/test-output/Reports/Screenshots/"+now+"image.png";
        //        3. Save the image in the path as a file
        FileUtils.copyFile(image,new File(path));

        //        GETTING THE ABSOLUTE PATH OF THE IMAGE PATH THAT IS STRING
        return new File(path).getAbsolutePath();
    }

    /*
   JAVASCRIPT EXECUTOR METHODS
    @param WebElement
    scrolls into that element
     */
    public static void scrollIntoViewJS(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);",element);
    }
    /*
    scroll all the way down
     */
    public static void scrollAllTheWayDownJS(){
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
    }
    /*
    scroll all the way up
     */
    public static void scrollAllTheWayUpJS(){
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("window.scrollTo(0,-document.body.scrollHeight)");
    }
    /*
    @param WebElement
    clicks on that element
     */
    public static void clickByJS(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].click();",element);
    }
    //    EXPLICITLY WAIT FOR ELEMENT TO BE VISIBLE, SCROLL INTO THE ELEMENT, THEN CLICK BY JS
    public static void clickWithTimeoutByJS(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", waitForVisibility(element,5));
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", element);
    }
    public static void clickWithTimeOut(WebElement element, int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.click();
                return;
            } catch (WebDriverException e) {
                waitFor(1);
            }
        }
    }
    /*
    @param String id of teh webelement that we want to locate
    locating element using javascript executor
    and returns that WebElement
    Note that this is NOT common and we should use 8 locators that we learned in selenium
     */
    public WebElement locateElementsByJS(String idOfElement){
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        return ((WebElement)js.executeScript("return document.getElementById('"+idOfElement+"')"));
    }
    /*
    @param1 WebElement, @param2 String
    type the string in that web element
     */
    public static void setValueByJS(WebElement inputElement,String text){
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].setAttribute('value','"+text+"')",inputElement);
    }
    /*
    param : Id of the the element
     */
    public static String getValueByJS(String idOfElement){
        JavascriptExecutor js = (JavascriptExecutor)Driver.getDriver();
        String value=js.executeScript("return document.getElementById('"+idOfElement+"').value").toString();
        System.out.println(value);
        return value;
//        How you get the value of an input box?
//        We can js executor.
//        How?
//        I can get the element using js executor, and get teh value of the element.
//        For example, I can get the element by id, and use value attribute to get the value of in an input
//        I have to do this, cause getText in this case does not return teh text in an input
//        EG: getValueByJS("hotelDates")
    }
    /*   HARD WAIT:
      @param : second
    */
    public static void waitFor(int seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /*
    SELENIUM WAIT REUSABLE METHODS
     */
    //    DYNAMIC SELENIUM WAITS:
    //===============Explicit Wait==============//
    public static WebElement waitForVisibility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    public static WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public static WebElement waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    public static WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));

    }

    public static String getTextWithTimeout(WebElement element, int timeout) {
        String text="";
        for (int i = 0; i < timeout; i++) {
            try {
                text = element.getText();
                return text;
            } catch (WebDriverException e) {
                waitFor(1);
            }
        }
        return null;
    }

    /*
    Custom method to wait to type in an input
     */
    public static void sendKeysWithTimeout(WebElement element,String text,int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.sendKeys(text);
                return;
            } catch (WebDriverException e) {
                waitFor(1);
            }
        }
    }


    //    This can be used when a new page opens
    public static void waitForPageToLoad(long timeout) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            System.out.println("Waiting for page to load...");
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
            wait.until(expectation);
        } catch (Throwable error) {
            System.out.println(
                    "Timeout waiting for Page Load Request to complete after " + timeout + " seconds");
        }
    }
    //======Fluent Wait====
    // params : xpath of teh element , max timeout in seconds, polling in second
    public static WebElement fluentWait(String xpath, int withTimeout, int pollingEvery) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(withTimeout))//Wait 3 second each time
                .pollingEvery(Duration.ofSeconds(pollingEvery))//Check for the element every 1 second
                .withMessage("Ignoring No Such Element Exception")
                .ignoring(NoSuchElementException.class);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return element;
    }


    //    RADIO BUTTON
    public void clickRadioByIndex(int index){
        int numOfRadio =Driver.getDriver().findElements(By.xpath("//input[@type='radio']")).size();
        for (int i=0;i<numOfRadio;i++){
            if (!Driver.getDriver().findElements(By.xpath("//input[@type='radio']")).get(index).isSelected()) {
                Driver.getDriver().findElements(By.xpath("//input[@type='radio']")).get(index).click();
            }
        }
    }

    //    CHECKBOX BUTTON
    public void clickCheckboxByIndex(int index){
        int numOfRadio =Driver.getDriver().findElements(By.xpath("//input[@type='checkbox']")).size();
        try{
            for (int i=0;i<numOfRadio;i++){
                if (!Driver.getDriver().findElements(By.xpath("//input[@type='checkbox']")).get(index).isSelected()) {
                    Driver.getDriver().findElements(By.xpath("//input[@type='checkbox']")).get(index).click();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //    DROPDOWN
//    USE THIS ONE TO SELECT FROM A DROPDOWN
    public static void selectByVisibleText(WebElement element, String text){
        Select select =new Select(element);
        for (int i =0;i<select.getOptions().size();i++){
            if(select.getOptions().get(i).getText().equalsIgnoreCase(text)){
                select.getOptions().get(i).click();
                break;
            }
        }
    }

    public static void selectByIndex(WebElement element, int index){
        Select objSelect =new Select(element);
        objSelect.selectByIndex(index);
    }

    public static void selectByValue(WebElement element, String value) {
        Select objSelect = new Select(element);
        objSelect.selectByValue(value);
    }

    public static void selectDropdownByValue(WebElement element,String textOfDropdown){
        List<WebElement> options = element.findElements(By.tagName("option"));
        for (WebElement option : options){
            System.out.println(option.getText());
            if (option.getText().equals(textOfDropdown)){
                option.click();
                break;
            }
        }
    }
    //  DROPDOWN
    /**
     * Selects a random value from a dropdown list and returns the selected Web Element
     * @param select
     * @return
     */
    public static WebElement selectRandomTextFromDropdown(Select select) {
        Random random = new Random();
        List<WebElement> list = select.getOptions();
        int optionIndex = 1 + random.nextInt(list.size() - 1);
        select.selectByIndex(optionIndex);
        return select.getFirstSelectedOption();
    }

    //    DROPDOWN: accepts dropdown element and returns all selected element texts as an arraylist
    public ArrayList<String> getDropdownSelectedOptions(WebElement element) throws Exception {
        if (element!=null){
            Select list = new Select(element);
            ArrayList<WebElement> allSelectedOptions = (ArrayList<WebElement>) list.getAllSelectedOptions();
            ArrayList<String> result = new ArrayList<String>();
            for (WebElement eachSelected : allSelectedOptions){
                result.add(eachSelected.getText());
            }
            return result;
        }else {
            throw new Exception("No element is returned");
        }
    }

    //    VERIFY ELEMENT IS DISPLAYED
    /**
     * Verifies whether the element is displayed on page
     * fails if the element is not found or not displayed
     *
     * @param element
     */
    public static void verifyElementDisplayed(WebElement element) {
        try {
            assertTrue("Element is not visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            org.junit.Assert.fail("Element is not found: " + element);
        }
    }

    /**
     * Verifies whether the element matching the provided locator is displayed on page
     * fails if the element matching the provided locator is not found or not displayed
     *
     * @param by
     */
    public static void verifyElementDisplayed(By by) {
        try {
            assertTrue("Element not visible: " + by, Driver.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException e) {
            org.junit.Assert.fail("Element not found: " + by);
        }
    }

    //VERIFY ELEMENT IS NOT DISPLAYED
    /**
     * Verifies whether the element matching the provided locator is NOT displayed on page
     * fails if the element matching the provided locator is not found or not displayed
     *
     * @param by
     */
    public static void verifyElementNotDisplayed(By by) {
        try {
            assertFalse("Element should not be visible: " + by, Driver.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }
    /**
     * Verifies whether the element matching the provided WebElement is NOT displayed on page
     * fails if the element matching the WebElement is not found or not displayed
     * @paramWebElement
     */
    public static void verifyElementNotDisplayed(WebElement element) {
        try {
            assertFalse("Element should not be visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    //    ALERT
    public void acceptAlert() throws InterruptedException {
        Driver.getDriver().switchTo().alert().accept();
    }
    public void dismissAlert() throws InterruptedException {
        Driver.getDriver().switchTo().alert().accept();
    }
    //    IFRAME
    public static void switchIframeByWebElement(String xpath){
        WebElement iframeElement = Driver.getDriver().findElement(By.xpath(xpath));
        Driver.getDriver().switchTo().frame(iframeElement);
    }
    //    IFRAME
    public static void switchIframeByIndex(int index){
        Driver.getDriver().switchTo().frame(index);
    }
    //    MULTIPLE WINDOW !!!
    public static void switchToWindowByTitle(String targetTitle) {
        String origin = Driver.getDriver().getWindowHandle();
        for (String childWindow : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(childWindow);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                System.out.println("Switched to the window : "+targetTitle);
                return;
            }
        }
        Driver.getDriver().switchTo().window(origin);
    }
    //    windowNumber starts at (0)
    public static void switchToWindow(int windowNumber){
        List<String> list = new ArrayList<>(Driver.getDriver().getWindowHandles());
        Driver.getDriver().switchTo().window(list.get(windowNumber));
    }
    //    ACTIONS_RIGHT CLICK
    public static void rightClickOnElementActions(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        actions.contextClick(element).perform();
    }
    //ACTIONS_DOUBLE CLICK
    public static void doubleClick(WebElement element) {
        new Actions(Driver.getDriver()).doubleClick(element).build().perform();
    }
    //    ACTIONS_HOVER_OVER
    public static void hoverOverOnElementActions(WebElement element) {
        //        Actions actions = new Actions(driver);
        new Actions(Driver.getDriver()).moveToElement(element).perform();
    }
    //    ACTIONS_SCROLL_DOWN
    public static void scrollDownActions() {
        //        Actions actions = new Actions(driver);
        new Actions(Driver.getDriver()).sendKeys(Keys.PAGE_DOWN).perform();
    }
    //    ACTIONS_SCROLL_UP
    public static void scrollUpActions() {
        //        Actions actions = new Actions(driver);
        new Actions(Driver.getDriver()).sendKeys(Keys.PAGE_UP).perform();
    }
    //    ACTIONS_SCROLL_RIGHT
    public static void scrollRightActions(){
        new Actions(Driver.getDriver()).sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT).perform();
    }
    //    ACTIONS_SCROLL_LEFT
    public static void scrollLeftActions(){
        new Actions(Driver.getDriver()).sendKeys(Keys.ARROW_LEFT).sendKeys(Keys.ARROW_LEFT).perform();
    }
    //    ACTIONS_DRAG_AND_DROP
    public static void dragAndDropActions(WebElement source, WebElement target) {
        //        Actions actions = new Actions(driver);
        new Actions(Driver.getDriver()).dragAndDrop(source,target).perform();
    }
    //    ACTIONS_DRAG_AND_DROP_BY
    public static void dragAndDropActions(WebElement source, int x, int y) {
        //        Actions actions = new Actions(driver);
        new Actions(Driver.getDriver()).dragAndDropBy(source,x,y).perform();
    }



}