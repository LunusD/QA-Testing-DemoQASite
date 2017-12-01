import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DemoQASliderTest {
    private static ExtentReports report;
    private SpreadSheetReader spreadSheetReader;
    private WebDriver webDriver;
    private JavascriptExecutor js;

    @BeforeClass
    public static void init(){
        report = new ExtentReports();
        String fileName = "MyReport" + ".html";
        String filePath = System.getProperty("user.dir") + File.separatorChar + fileName;
        report.attachReporter(new ExtentHtmlReporter(filePath));
    }

    @Before
    public void setup(){
        webDriver = new ChromeDriver();

        webDriver.manage().window().maximize();
        spreadSheetReader = new SpreadSheetReader("properties.xlsx");
    }

    @After
    public void tearDown(){
        webDriver.quit();
    }

    @AfterClass
    public static void destroy(){
        report.flush();
    }

    @Test

    public void demoQASliderTest(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\AutomatedTesting\\chromedriver.exe");

        ExtentTest sliderTest = report.createTest("DemoQASliderTest");
        List<String> inputs;

        sliderTest.log(Status.INFO, "This is a test for the draggable object found on the first tab of the web page. This element should be able to move anywhere, so we will test this by comparing its " +
                "actual location after the move against our expected location and if they are identical then the test has passed.");
        sliderTest.log(Status.INFO, "Test begin. Opening website.");
        sliderTest.log(Status.DEBUG, "webDriver opening Chrome with url http://demoqa.com/draggable/");
        inputs = spreadSheetReader.readRow(4, "demoqa");
        String url = inputs.get(1);
        webDriver.navigate().to(url);
        sliderTest.log(Status.INFO, "Open.");

        SliderPage sliderPage = PageFactory.initElements(webDriver, SliderPage.class);

        int startingVal = sliderPage.getSliderPos(webDriver);
        sliderTest.log(Status.DEBUG, "Starting value for the slider is: " + startingVal);

        sliderTest.log(Status.INFO, "Moving the slider to the right, changing it's value from 2 to 3");
        sliderTest.log(Status.DEBUG, "Moving the slider 100px to the right by finding the element on the page and using a clickAndHold() method and moveByOffset()" +
                " methods. This will change the value on the slider from 2 to 3.");
        sliderPage.moveSlider(webDriver,100,0);
        int desiredValAfterMove = sliderPage.getSliderPos(webDriver);

        int actualValAfterMove = sliderPage.getSliderVal(webDriver);

        try {
            assertEquals("Expected slider value of : " + desiredValAfterMove, desiredValAfterMove, actualValAfterMove);
            sliderTest.log(Status.PASS, "assertEquals PASS: Slider values match.");
        } catch (AssertionError ae) {
            try {
                String filePath = ScreenShot.take(webDriver, "navigationError");
                sliderTest.addScreenCaptureFromPath(filePath);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            sliderTest.log(Status.FAIL, "assertEquals FAIL: Slider value mismatched.");
            throw ae;
        }

    }

}
