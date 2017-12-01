import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class SliderPage {

    @FindBy(css = "#slider-range-max > div")
    WebElement sliderAfterSeletor;

    @FindBy(css = "#slider-range-max > span")
    WebElement sliderSelector;

    @FindBy(id = "amount1")
    WebElement sliderVal;

    public int getSliderPos(WebDriver webDriver){
        String sliderWidthRemaining = sliderAfterSeletor.getAttribute("style");
        sliderWidthRemaining = sliderWidthRemaining.replace("width: ", "");
        sliderWidthRemaining = sliderWidthRemaining.substring(0, sliderWidthRemaining.length()-2);
        double sliderRemaining = Double.parseDouble(sliderWidthRemaining);

        double sliderCovered = 100 - sliderRemaining;
        double sliderVal = sliderCovered / 10;

        int sliderShown = (int)Math.ceil(sliderVal);

        if(sliderShown == 0){
            sliderShown=1;
        }

        return sliderShown;
    }

    public void moveSlider(WebDriver webDriver, int x, int y){
        Actions builder = new Actions(webDriver);

        builder.moveToElement(sliderSelector).clickAndHold().moveByOffset(x, y).release().perform();

    }

    public int getSliderVal(WebDriver webDriver){
        int val = Integer.parseInt(sliderVal.getAttribute("value"));

        return val;
    }


}
