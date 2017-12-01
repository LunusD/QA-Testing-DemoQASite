import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class DraggablePage {

    @FindBy(css = "#ui-id-1")
    WebElement defaultTab;

    @FindBy(css = "#ui-id-2")
    WebElement constrainTab;

    @FindBy(css = "#ui-id-3")
    WebElement cursorTab;

    @FindBy(css = "#ui-id-4")
    WebElement eventsTab;

    @FindBy(css = "#ui-id-5")
    WebElement dragAndSortTab;

    WebElement draggableElement;
    WebElement constrainVertElement;
    WebElement constrainHorElement;
    WebElement constrainBoxElement;
    WebElement constrainParentElement;

    WebElement cursorCentre;
    WebElement cursorOffset;
    WebElement cursonBottom;


    public void clickDefault(){
        defaultTab.click();
    }

    public void clickConstrain(){
        constrainTab.click();
    }

    public void clickCursor(){
        cursorTab.click();
    }

    public void clickEvents(){
        eventsTab.click();
    }

    public void clickDragAndSort(){
        dragAndSortTab.click();
    }

    public void defaultDragAndDrop(WebDriver defaultWebDriver){
        Actions builder = new Actions(defaultWebDriver);

        if(defaultTab.isDisplayed()){
            draggableElement = defaultWebDriver.findElement(By.id("draggable"));
        }
        builder.moveByOffset(draggableElement.getLocation().getX() + 10,draggableElement.getLocation().getY() + 10).clickAndHold().moveByOffset(200, 100).release().perform();

    }

    public Point getDefaultDraggablePosition(WebDriver defaultWebDriver){
        if(defaultTab.isDisplayed()){
            draggableElement = defaultWebDriver.findElement(By.id("draggable"));
        }

        Point pos = draggableElement.getLocation();
        return pos;
    }

    public void constrainVertDrag(WebDriver constrainWebDriver){
        Actions builder = new Actions(constrainWebDriver);

        if(constrainTab.isDisplayed()){
            constrainVertElement = constrainWebDriver.findElement(By.id("draggabl"));
        }

        builder.moveToElement(constrainVertElement).clickAndHold().moveByOffset(30, 100).release().perform();
    }

    public Point getConstrainVertDraggablePosition(WebDriver constrainWebDriver){
        if(constrainTab.isDisplayed()){
            constrainVertElement = constrainWebDriver.findElement(By.id("draggabl"));
        }

        Point pos = constrainVertElement.getLocation();
        return pos;
    }

    public void constrainHorDrag(WebDriver constrainWebDriver){
        Actions builder = new Actions(constrainWebDriver);

        if(constrainTab.isDisplayed()){
            constrainHorElement = constrainWebDriver.findElement(By.id("draggabl2"));
        }

        builder.moveToElement(constrainHorElement).clickAndHold().moveByOffset(100, 30).release().perform();
    }

    public Point getConstrainHorDraggablePosition(WebDriver constrainWebDriver){
        if(constrainTab.isDisplayed()){
            constrainHorElement = constrainWebDriver.findElement(By.id("draggabl2"));
        }

        Point pos = constrainHorElement.getLocation();
        return pos;
    }

    public void constrainBoxDrag(WebDriver constrainWebDriver){
        Actions builder = new Actions(constrainWebDriver);

        if(constrainTab.isDisplayed()){
            constrainBoxElement = constrainWebDriver.findElement(By.id("draggabl3"));
        }

        builder.moveToElement(constrainBoxElement).clickAndHold().moveByOffset(-100, -20).release().perform();
    }

    public Point getConstrainBoxDraggablePosition(WebDriver constrainWebDriver){
        if(constrainTab.isDisplayed()){
            constrainBoxElement = constrainWebDriver.findElement(By.id("draggabl3"));
        }

        Point pos = constrainBoxElement.getLocation();
        return pos;
    }

    public void constrainParentDrag(WebDriver constrainWebDriver){
        Actions builder = new Actions(constrainWebDriver);

        if(constrainTab.isDisplayed()){
            constrainParentElement = constrainWebDriver.findElement(By.id("draggabl5"));
        }

        builder.moveToElement(constrainParentElement).clickAndHold().moveByOffset(10, 10).release().perform();
    }

    public Point getConstrainParentDraggablePosition(WebDriver constrainWebDriver){
        if(constrainTab.isDisplayed()){
            constrainBoxElement = constrainWebDriver.findElement(By.id("draggabl5"));
        }

        Point pos = constrainBoxElement.getLocation();
        return pos;
    }

    public void cursorCentreDrag(WebDriver cursorWebDriver){
        Actions builder = new Actions(cursorWebDriver);

        if(cursorTab.isDisplayed()){
            cursorCentre = cursorWebDriver.findElement(By.id("drag"));
        }

        builder.moveToElement(cursorCentre).clickAndHold().moveByOffset(120,120).release().perform();
    }

    public Point getCentreCursorDraggablePosition(WebDriver cursorWebDriver){
        if(cursorTab.isDisplayed()){
            cursorCentre = cursorWebDriver.findElement(By.id("drag"));
        }

        Point pos = cursorCentre.getLocation();
        return pos;
    }

}
