package helpers;

public class GlobalVariables {

    private GlobalVariables(){

    }

    public static OSNames OS_NAME;

    public static String BROWSER_NAME = null;

    public static String ACCESS_TOKEN = "";

    public static String REQUEST_BODY = null;

    public static String CONSOL = null;

    public static String FRONTEND_SUITE_REPORT_PATH = null;

    public static String FRONTEND_SUITE_JUNITREPORT_PATH = "\\target\\surefire-reports\\junitreports\\TEST-jsonserversuite.JsonServerSuite.xml";

    public static String AUTHENTICATION_SUITE_REPORT_PATH = null;

    public static String AUTHENTICATION_SUITE_JUNITREPORT_PATH = "\\target\\surefire-reports\\junitreports\\TEST-travelinsurancesuite.TravelInsuranceSuite.xml";


}
