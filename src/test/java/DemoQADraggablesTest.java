import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

    public class DemoQADraggablesTest {

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
        public void demoQADefaultDraggableTest() {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\AutomatedTesting\\chromedriver.exe");

            ExtentTest defaultDraggableTest = report.createTest("DemoQADefaultDraggableTest");
            List<String> inputs;

            defaultDraggableTest.log(Status.INFO, "This is a test for the draggable object found on the first tab of the web page. This element should be able to move anywhere, so we will test this by comparing its " +
                    "actual location after the move against our expected location and if they are identical then the test has passed.");
            defaultDraggableTest.log(Status.INFO, "Test begin. Opening website.");
            defaultDraggableTest.log(Status.DEBUG, "webDriver opening Chrome with url http://demoqa.com/draggable/");
            inputs = spreadSheetReader.readRow(0, "demoqa");
            String url = inputs.get(1);
            webDriver.navigate().to(url);
            defaultDraggableTest.log(Status.INFO, "Open.");


            DraggablePage draggablePage = PageFactory.initElements(webDriver, DraggablePage.class);

            Point defaultDraggableStartingPoint = draggablePage.getDefaultDraggablePosition(webDriver);

            defaultDraggableTest.log(Status.INFO, "Find the draggable element on the first tab and moves it to the right by 200 and down by 100");
            defaultDraggableTest.log(Status.DEBUG, "Finds the draggable element on the first tab by invoking the FindElement(By.id()) method on the webDriver and assigns it to a WebElement. " +
                    "Then creates an Actions object to handle the mouse movements on this page. Calling the moveByOffset() method on this page we move our mouse over the draggable object" +
                    "and then call the clickAndHold() method and moveByOffset() method again in order to drag the element before finally releasing with the release() method and the perform() method.");
            draggablePage.defaultDragAndDrop(webDriver);

            Point expectedPointVal = defaultDraggableStartingPoint.moveBy(200, 100);
            Point actualPointVal = draggablePage.getDefaultDraggablePosition(webDriver);

            try {
                assertEquals("Expected Point of " + expectedPointVal.getX() + "," + expectedPointVal.getY(), expectedPointVal, actualPointVal);
                defaultDraggableTest.log(Status.PASS, "assertEquals PASS: Draggable element successfully moved to desired location.");
            } catch (AssertionError ae) {
                try {
                    String filePath = ScreenShot.take(webDriver, "navigationError");
                    defaultDraggableTest.addScreenCaptureFromPath(filePath);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                defaultDraggableTest.log(Status.FAIL, "assertEquals FAIL: Draggable element found at " + actualPointVal.toString() + ". Desired location is " + expectedPointVal.toString());
                throw ae;
            }
        }

        @Test
        public void demoQAConstraintVerticalDraggableTest() {

            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\AutomatedTesting\\chromedriver.exe");

            ExtentTest constraintVertDraggableTest = report.createTest("DemoQAConstraintVerDraggableTest");
            List<String> inputs;

            constraintVertDraggableTest.log(Status.INFO, "This is a test to move the draggable element that can only move vertically. We will test this by passing in a movement command to move it 30px to the right and 100px " +
                    "down. Then we will check the location the object has moved to, to our expected location that will only be moved to the right. If it has moved horizontally in any way the test will fail, as the element should be " +
                    "unable to move in this direction.");
            constraintVertDraggableTest.log(Status.INFO, "Test begin. Opening website.");
            constraintVertDraggableTest.log(Status.DEBUG, "webDriver opening Chrome with url http://demoqa.com/draggable/");
            inputs = spreadSheetReader.readRow(0, "demoqa");
            String url = inputs.get(1);
            webDriver.navigate().to(url);
            constraintVertDraggableTest.log(Status.INFO, "Open.");

            constraintVertDraggableTest.log(Status.INFO, "Navigating to the second tab.");
            constraintVertDraggableTest.log(Status.DEBUG, "Finding element of \"Constrain Movement\" tab on the page using a Find.By(id) annotation and clicking it to switch visible element on the page.");

            DraggablePage draggablePage = PageFactory.initElements(webDriver, DraggablePage.class);
            draggablePage.clickConstrain();
            if (draggablePage.constrainTab.isDisplayed()) {
                constraintVertDraggableTest.log(Status.INFO, "Tab changed");
            } else {
                constraintVertDraggableTest.log(Status.ERROR, "Tab not changed, attempting JavaScript script");
                if (webDriver instanceof JavascriptExecutor) {
                    js = (JavascriptExecutor) webDriver;
                }
                js.executeScript("$('#tabs a[href=\"#tabs-3\"]').click()");
                if (draggablePage.constrainTab.isDisplayed()) {
                    constraintVertDraggableTest.log(Status.INFO, "Tab changed");
                } else {
                    constraintVertDraggableTest.log(Status.FATAL, "Unable to open constrain tab, either by finding element or running the JavaScript code associated.");
                }
            }

            Point vertDraggableStartingPoint = draggablePage.getConstrainVertDraggablePosition(webDriver);

            constraintVertDraggableTest.log(Status.INFO, "Attempting to move the vertical draggable element by 30px to the right and 100px down. The movement to the right should not occur.");
            constraintVertDraggableTest.log(Status.DEBUG, "Finds the vertical draggable element on the second tab by invoking the FindElement(By.id()) method on the webDriver and assigns it to a WebElement. " +
                    "Then creates an Actions object to handle the mouse movements on this page. Calling the moveToElement() method on this page we move our mouse over the draggable object" +
                    "and then call the clickAndHold() method and moveByOffset() method again in order to drag the element before finally releasing with the release() method and the perform() method.");
            draggablePage.constrainVertDrag(webDriver);

            Point expectedVertDraggablePosition = vertDraggableStartingPoint.moveBy(0, 100);
            Point actualVertDraggablePosition = draggablePage.getConstrainVertDraggablePosition(webDriver);

            try {
                assertEquals("Expected Point of " + expectedVertDraggablePosition.getX() + "," + expectedVertDraggablePosition.getY(), expectedVertDraggablePosition, actualVertDraggablePosition);
                constraintVertDraggableTest.log(Status.PASS, "assertEquals PASS: Draggable element successfully moved to desired location.");
            } catch (AssertionError ae) {
                try {
                    String filePath = ScreenShot.take(webDriver, "navigationError");
                    constraintVertDraggableTest.addScreenCaptureFromPath(filePath);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                constraintVertDraggableTest.log(Status.FAIL, "assertEquals FAIL: Draggable element found at " + actualVertDraggablePosition.toString() + ". Desired location is " + expectedVertDraggablePosition.toString());
                throw ae;
            }
        }

        @Test
        public void demoQAConstraintHorDraggableTest(){

            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\AutomatedTesting\\chromedriver.exe");

            ExtentTest constraintHorDraggableTest = report.createTest("DemoQAConstraintHorDraggableTest");
            List<String> inputs;

            constraintHorDraggableTest.log(Status.INFO, "This is a test to move the draggable element that can only move horizontally. We will test this by passing in a movement command to move it 100px to the right and 30px " +
                    "down. Then we will check the location the object has moved to, to our expected location that will only be moved to the right. If it has moved vertically in any way the test will fail, as the element should be " +
                    "unable to move in this direction.");
            constraintHorDraggableTest.log(Status.INFO, "Test begin. Opening website.");
            constraintHorDraggableTest.log(Status.DEBUG, "webDriver opening Chrome with url http://demoqa.com/draggable/");
            inputs = spreadSheetReader.readRow(0, "demoqa");
            String url = inputs.get(1);
            webDriver.navigate().to(url);
            constraintHorDraggableTest.log(Status.INFO, "Open.");

            constraintHorDraggableTest.log(Status.INFO, "Navigating to the second tab.");
            constraintHorDraggableTest.log(Status.DEBUG, "Finding element of \"Constrain Movement\" tab on the page using a Find.By(id) annotation and clicking it to switch visible element on the page.");

            DraggablePage draggablePage = PageFactory.initElements(webDriver, DraggablePage.class);

            constraintHorDraggableTest.log(Status.INFO, "Navigating to the second tab.");
            constraintHorDraggableTest.log(Status.DEBUG, "Finding element of \"Constrain Movement\" tab on the page using a Find.By(id) annotation and clicking it to switch visible element on the page.");
            draggablePage.clickConstrain();
            if (draggablePage.constrainTab.isDisplayed()) {
                constraintHorDraggableTest.log(Status.INFO, "Tab changed");
            } else {
                constraintHorDraggableTest.log(Status.ERROR, "Tab not changed, attempting JavaScript script");
                if (webDriver instanceof JavascriptExecutor) {
                    js = (JavascriptExecutor) webDriver;
                }
                js.executeScript("$('#tabs a[href=\"#tabs-3\"]').click()");
                if (draggablePage.constrainTab.isDisplayed()) {
                    constraintHorDraggableTest.log(Status.INFO, "Tab changed");
                } else {
                    constraintHorDraggableTest.log(Status.FATAL, "Unable to open constrain tab, either by finding element or running the JavaScript code associated.");
                }
            }

            Point constraintHorDraggableStartingPoint = draggablePage.getConstrainHorDraggablePosition(webDriver);

            constraintHorDraggableTest.log(Status.INFO, "Attempting to move horizontal draggable element on the page by 100px to the right and 30px down. The downwards drag should not be completed.");
            constraintHorDraggableTest.log(Status.DEBUG, "Finds the horizontal draggable element on the second tab by invoking the FindElement(By.id()) method on the webDriver and assigns it to a WebElement. " +
                    "Then creates an Actions object to handle the mouse movements on this page. Calling the moveToElement() method on this page we move our mouse over the draggable object" +
                    "and then call the clickAndHold() method and moveByOffset() method again in order to drag the element before finally releasing with the release() method and the perform() method.");
            draggablePage.constrainHorDrag(webDriver);

            Point expectedHorDraggablePosition = constraintHorDraggableStartingPoint.moveBy(100,0);
            Point actualHorDraggablePosition = draggablePage.getConstrainHorDraggablePosition(webDriver);

            try{
                assertEquals("Expected Point of " + expectedHorDraggablePosition.getX() + "," + expectedHorDraggablePosition.getY(), expectedHorDraggablePosition, actualHorDraggablePosition);
                constraintHorDraggableTest.log(Status.PASS, "assertEquals PASS: Draggable element successfully moved to desired location.");
            } catch (AssertionError ae) {
                try {
                    String filePath = ScreenShot.take(webDriver, "navigationError");
                    constraintHorDraggableTest.addScreenCaptureFromPath(filePath);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                constraintHorDraggableTest.log(Status.FAIL, "assertEquals FAIL: Draggable element found at " + actualHorDraggablePosition.toString() + ". Desired location is " + expectedHorDraggablePosition.toString());
                throw ae;
            }
        }

        @Test
        public void demoQAConstraintBoxDraggableTest(){
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\AutomatedTesting\\chromedriver.exe");

            ExtentTest constraintBoxDraggableTest = report.createTest("DemoQAConstraintBoxDraggableTest");
            List<String> inputs;

            constraintBoxDraggableTest.log(Status.INFO, "This is a test to move the draggable object that is constrained within a box on the page. This should be able to move anywhere around the box, so long as it does " +
                    "not go past the borders of the margin defined by the box. To test this we are moving the element 100px to the left and 100px up and comparing our expected location to the actual location of the element. " +
                    "Since the element should be unable to leave the confines of the box there should be no movement.");
            constraintBoxDraggableTest.log(Status.INFO, "Test begin. Opening website.");
            constraintBoxDraggableTest.log(Status.DEBUG, "webDriver opening Chrome with url http://demoqa.com/draggable/");
            inputs = spreadSheetReader.readRow(0, "demoqa");
            String url = inputs.get(1);
            webDriver.navigate().to(url);
            constraintBoxDraggableTest.log(Status.INFO, "Open.");

            constraintBoxDraggableTest.log(Status.INFO, "Navigating to the second tab.");
            constraintBoxDraggableTest.log(Status.DEBUG, "Finding element of \"Constrain Movement\" tab on the page using a Find.By(id) annotation and clicking it to switch visible element on the page.");

            DraggablePage draggablePage = PageFactory.initElements(webDriver, DraggablePage.class);

            constraintBoxDraggableTest.log(Status.INFO, "Navigating to the second tab.");
            constraintBoxDraggableTest.log(Status.DEBUG, "Finding element of \"Constrain Movement\" tab on the page using a Find.By(id) annotation and clicking it to switch visible element on the page.");
            draggablePage.clickConstrain();
            if (draggablePage.constrainTab.isDisplayed()) {
                constraintBoxDraggableTest.log(Status.INFO, "Tab changed");
            } else {
                constraintBoxDraggableTest.log(Status.ERROR, "Tab not changed, attempting JavaScript script");
                if (webDriver instanceof JavascriptExecutor) {
                    js = (JavascriptExecutor) webDriver;
                }
                js.executeScript("$('#tabs a[href=\"#tabs-3\"]').click()");
                if (draggablePage.constrainTab.isDisplayed()) {
                    constraintBoxDraggableTest.log(Status.INFO, "Tab changed");
                } else {
                    constraintBoxDraggableTest.log(Status.FATAL, "Unable to open constrain tab, either by finding element or running the JavaScript code associated.");
                }
            }


            Point constraintBoxDraggableStartingPoint = draggablePage.getConstrainBoxDraggablePosition(webDriver);

            constraintBoxDraggableTest.log(Status.INFO, "Attempting to move draggable element that is constrained by the box it is in" +
                    " on the page by 100px to the left and 100px up.");
            constraintBoxDraggableTest.log(Status.DEBUG, "Finds the box constrained draggable element on the second tab by invoking the FindElement(By.id()) method on the webDriver and assigns it to a WebElement. " +
                    "Then creates an Actions object to handle the mouse movements on this page. Calling the moveToElement() method on this page we move our mouse over the draggable object" +
                    "and then call the clickAndHold() method and moveByOffset() method again in order to drag the element before finally releasing with the release() method and the perform() method.");
            draggablePage.constrainBoxDrag(webDriver);

            Point expectedBoxDraggablePosition = constraintBoxDraggableStartingPoint.moveBy(-100,-100);
            Point actualBoxDraggablePosition = draggablePage.getConstrainBoxDraggablePosition(webDriver);

            try{
                Assert.assertNotEquals("Expected Point of " + expectedBoxDraggablePosition.getX() + "," + expectedBoxDraggablePosition.getY(), expectedBoxDraggablePosition, actualBoxDraggablePosition);
                constraintBoxDraggableTest.log(Status.PASS, "assertNotEquals PASS: No movement from draggable element.");
            } catch (AssertionError ae) {
                try {
                    String filePath = ScreenShot.take(webDriver, "navigationError");
                    constraintBoxDraggableTest.addScreenCaptureFromPath(filePath);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                constraintBoxDraggableTest.log(Status.FAIL, "assertEquals FAIL: Draggable element found at " + actualBoxDraggablePosition.toString() + ". Desired location is " + expectedBoxDraggablePosition.toString());
                throw ae;
            }
        }

        @Test
        public void demoQAConstraintParentDraggableTest(){

            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\AutomatedTesting\\chromedriver.exe");

            ExtentTest constraintParentDraggableTest = report.createTest("DemoQAConstraintParentDraggableTest");
            List<String> inputs;

            constraintParentDraggableTest.log(Status.INFO, "This is a test to move the draggable element that is constrained by its parent object. This should not be able to move outside of the bounds of its" +
                    " parent object. This test will attempt to move it to the right by 10px and down by another 10px. This should effectively take it out of the parents bounds, so should not be possible. To test this we are checking the " +
                    "draggable element's position after drag against the position before the drag offset by 10px in both directions. They should not be the same.");
            constraintParentDraggableTest.log(Status.INFO, "Test begin. Opening website.");
            constraintParentDraggableTest.log(Status.DEBUG, "webDriver opening Chrome with url http://demoqa.com/draggable/");
            inputs = spreadSheetReader.readRow(0, "demoqa");
            String url = inputs.get(1);
            webDriver.navigate().to(url);
            constraintParentDraggableTest.log(Status.INFO, "Open.");

            constraintParentDraggableTest.log(Status.INFO, "Navigating to the second tab.");
            constraintParentDraggableTest.log(Status.DEBUG, "Finding element of \"Constrain Movement\" tab on the page using a Find.By(id) annotation and clicking it to switch visible element on the page.");

            DraggablePage draggablePage = PageFactory.initElements(webDriver, DraggablePage.class);

            draggablePage.clickConstrain();
            if (draggablePage.constrainTab.isDisplayed()) {
                constraintParentDraggableTest.log(Status.INFO, "Tab changed");
            } else {
                constraintParentDraggableTest.log(Status.ERROR, "Tab not changed, attempting JavaScript script");
                if (webDriver instanceof JavascriptExecutor) {
                    js = (JavascriptExecutor) webDriver;
                }
                js.executeScript("$('#tabs a[href=\"#tabs-3\"]').click()");
                if (draggablePage.constrainTab.isDisplayed()) {
                    constraintParentDraggableTest.log(Status.INFO, "Tab changed");
                } else {
                    constraintParentDraggableTest.log(Status.FATAL, "Unable to open constrain tab, either by finding element or running the JavaScript code associated.");
                }
            }

            Point constraintParentDraggableStartingPoint = draggablePage.getConstrainParentDraggablePosition(webDriver);

            constraintParentDraggableTest.log(Status.INFO, "Attempting to move draggable element that is constrained by its parent object." +
                    " on the page by 10px to the right and 10px down.");
            constraintParentDraggableTest.log(Status.DEBUG, "Finds the draggable element constrained by its parent on the second tab by invoking the FindElement(By.id()) method on the webDriver and assigns it to a WebElement. " +
                    "Then creates an Actions object to handle the mouse movements on this page. Calling the moveToElement() method on this page we move our mouse over the draggable object" +
                    "and then call the clickAndHold() method and moveByOffset() method again in order to drag the element before finally releasing with the release() method and the perform() method.");
            draggablePage.constrainParentDrag(webDriver);

            Point expectedParentDraggableNewPosition = constraintParentDraggableStartingPoint.moveBy(10,10);
            Point actualParentDraggablePosition = draggablePage.getConstrainParentDraggablePosition(webDriver);

            try{
                Assert.assertNotEquals("Expected Point of " + expectedParentDraggableNewPosition.getX() + "," + expectedParentDraggableNewPosition.getY(), expectedParentDraggableNewPosition, actualParentDraggablePosition);
                constraintParentDraggableTest.log(Status.PASS, "assertNotEquals PASS: Draggable element cannot move by 10px to the right or downwards, as that would take it out of bounds of the parent object.");
            } catch (AssertionError ae) {
                try {
                    String filePath = ScreenShot.take(webDriver, "navigationError");
                    constraintParentDraggableTest.addScreenCaptureFromPath(filePath);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                constraintParentDraggableTest.log(Status.FAIL, "assertNotEquals FAIL: Draggable element found at desired location. This should not be possible, given the confines of the parent object");
                throw ae;
            }
        }

        @Test
        public void demoQACursorCentreDraggableTest(){
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\AutomatedTesting\\chromedriver.exe");

            ExtentTest cursorCentreDraggableTest = report.createTest("DemoQACursonCentreDraggableTest");
            List<String> inputs;

            cursorCentreDraggableTest.log(Status.INFO, "");
            cursorCentreDraggableTest.log(Status.INFO, "Test begin. Opening website.");
            cursorCentreDraggableTest.log(Status.DEBUG, "webDriver opening Chrome with url http://demoqa.com/draggable/");
            inputs = spreadSheetReader.readRow(0, "demoqa");
            String url = inputs.get(1);
            webDriver.navigate().to(url);
            cursorCentreDraggableTest.log(Status.INFO, "Open.");

            cursorCentreDraggableTest.log(Status.INFO, "Navigating to the third tab.");
            cursorCentreDraggableTest.log(Status.DEBUG, "Finding element of \"Cursor Style\" tab on the page using a Find.By(id) annotation and clicking it to switch visible element on the page.");

            DraggablePage draggablePage = PageFactory.initElements(webDriver, DraggablePage.class);

            draggablePage.clickCursor();
            if (draggablePage.cursorTab.isDisplayed()){
                cursorCentreDraggableTest.log(Status.INFO, "Tab changed");
            } else {
                cursorCentreDraggableTest.log(Status.ERROR, "Tab not changed, attempting JavaScript script");
                if (webDriver instanceof JavascriptExecutor) {
                    js = (JavascriptExecutor) webDriver;
                }
                js.executeScript("$('#tabs a[href=\"#tabs-4\"]').click()");
                if (draggablePage.cursorTab.isDisplayed()) {
                    cursorCentreDraggableTest.log(Status.INFO, "Tab changed");
                } else {
                    cursorCentreDraggableTest.log(Status.FATAL, "Unable to open constrain tab, either by finding element or running the JavaScript code associated.");
                }
            }

            Point centreCursorDraggableStartingPoint = draggablePage.getCentreCursorDraggablePosition(webDriver);
            System.out.println(centreCursorDraggableStartingPoint.toString());

            cursorCentreDraggableTest.log(Status.INFO, "Moving the draggable object that snaps to the centre (relative to the mouse). The test will check the intended position against its " +
                    "actual position after the move.");
            cursorCentreDraggableTest.log(Status.DEBUG, "Finds the draggable element constrained by its parent on the second tab by invoking the FindElement(By.id()) method on the webDriver and assigns it to a WebElement." +
                    " Then creates an Actions object to handle the mouse movements on this page. Calling the moveToElement() method on this page we move our mouse over the draggable object" +
                    " and then call the clickAndHold() method and moveByOffset() method again in order to drag the element before finally releasing with the release() method and the perform() method.");
            draggablePage.cursorCentreDrag(webDriver);


            Point expectedCentreCursorPosition = centreCursorDraggableStartingPoint.moveBy(120-6,120-6);
            cursorCentreDraggableTest.log(Status.DEBUG, expectedCentreCursorPosition.toString() + ": We have moved the object by 120 in both directions but due to the offset of the mouse, we have to take " +
                    "another 6 from our desired location to find out actual one");
            Point actualCentreCursorPosition = draggablePage.getCentreCursorDraggablePosition(webDriver);

            try{
                assertEquals("Expected Point of " + expectedCentreCursorPosition.getX() + "," + expectedCentreCursorPosition.getY(), expectedCentreCursorPosition, actualCentreCursorPosition);
                cursorCentreDraggableTest.log(Status.PASS, "assertEquals PASS: Object moved to desired position.");
            } catch (AssertionError ae) {
                try {
                    String filePath = ScreenShot.take(webDriver, "navigationError");
                    cursorCentreDraggableTest.addScreenCaptureFromPath(filePath);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                cursorCentreDraggableTest.log(Status.FAIL, "assertEquals FAIL: Draggable element found at " + actualCentreCursorPosition.toString() + ". Desired location is " + expectedCentreCursorPosition.toString());
                throw ae;
            }


        }




    }
