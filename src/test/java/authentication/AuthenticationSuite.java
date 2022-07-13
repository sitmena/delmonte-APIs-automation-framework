package authentication;

import java.io.IOException;

import helpers.CustomKeywords;
import helpers.GlobalVariables;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ResourceCDN;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import authentication.AccessToken;

import static helpers.OSNames.*;
import static helpers.ReadingConfigFileData.readConfig;

public class AuthenticationSuite {

    CustomKeywords customKeywords = new CustomKeywords();

    String ReportName_with_path;

    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;

    //helps to generate the logs in test report.
    ExtentTest test;

    // Report code //

    @BeforeTest
    public void startReport() throws IOException, InterruptedException {

        String osName = readConfig("os");

        switch (osName) {
            case"macOS":
                GlobalVariables.OS_NAME = MACOS;
                break;
            case"Windows":
                GlobalVariables.OS_NAME = WINDOWS;
                break;
            case"Linux":
                GlobalVariables.OS_NAME = LINUX;
                break;
        }

        System.out.println("GlobalVariables.OS_NAME is: "+GlobalVariables.OS_NAME);

        String reportRename = customKeywords.getRandomString(16);
        ReportName_with_path = "/target/authentication"+reportRename+".html";

        // initialize the HtmlReporter

        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +ReportName_with_path);

        //initialize ExtentReports and attach the HtmlReporter
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);


        //configuration items to change the look and feel
        //add content, manage tests etc
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("REST Assured API Test");
        htmlReporter.config().setReportName("Authentication APIs Suite");  // To be changed to the suite name
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        htmlReporter.config().getTestViewChartLocation();
        htmlReporter.config().setCSS("css-string");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setJS("js-string");
        htmlReporter.config().setProtocol(Protocol.HTTPS);

        htmlReporter.config().setCSS(".node.level-1  ul{ display:none;} .node.level-1.active ul{display:block;}  .card-panel.environment  th:first-child{ width:30%;}");
        htmlReporter.config().setJS("$(window).off(\"keydown\");");
        htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);

    }

    //////////////////////Creating objects for the test cases./////////////////////////
    AccessToken accessToken = new AccessToken();
    PasswordReset passwordReset = new PasswordReset();

    /////////////////// Test cases list starts here///////////////////////////////////

    @Test(priority= 0)
    public void access_token_tc() throws InterruptedException, IOException {
        test = extent.createTest("Access Token");
        accessToken.accessToken();
    }

    @Test(priority= 1)
    public void password_reset_tc() throws InterruptedException, IOException {
        test = extent.createTest("Password Reset");
        passwordReset.passwordReset();
    }

    // After test cases running is done
    @AfterMethod
    public void getResult(ITestResult result) throws Throwable {

        String RequestBody  = "Request Body\n"+GlobalVariables.REQUEST_BODY;
        String responseLabel = "Response Body\n"+GlobalVariables.CONSOL;

        if(result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED ", ExtentColor.RED));
            test.fail(result.getThrowable());
            test.fail(MarkupHelper.createCodeBlock(RequestBody));
            test.fail(MarkupHelper.createCodeBlock(responseLabel));
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" PASSED ", ExtentColor.GREEN));
            test.pass(MarkupHelper.createCodeBlock(RequestBody));
            test.pass(MarkupHelper.createCodeBlock(responseLabel));
        }
        else {
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" SKIPPED ", ExtentColor.ORANGE));
            test.skip(result.getThrowable());
        }

        // Resetting Request body and response after each test case
        GlobalVariables.REQUEST_BODY = null;
        GlobalVariables.CONSOL = null;

    }

    @AfterTest
    public void tearDown() {
        //to write or update test information to reporter
        extent.flush();
    }

    @AfterSuite
    public void aftersuite() throws IOException {

        GlobalVariables.AUTHENTICATION_SUITE_REPORT_PATH = ReportName_with_path;

        if(GlobalVariables.OS_NAME.equals(MACOS)){
            GlobalVariables.AUTHENTICATION_SUITE_JUNITREPORT_PATH = "\\target\\surefire-reports\\junitreports\\TEST-frontendsuite.FrontEndSuite.xml";
        }else{
            GlobalVariables.AUTHENTICATION_SUITE_JUNITREPORT_PATH = "//target//surefire-reports//junitreports//TEST-frontendsuite.FrontEndSuite.xml";
        }

        customKeywords.replacingAllSuitesNamesValue("authentication", ReportName_with_path);

    }


}
