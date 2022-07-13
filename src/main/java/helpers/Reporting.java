package helpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.xml.sax.SAXException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Properties;

import static helpers.OSNames.*;
import static helpers.ReadingConfigFileData.readConfig;

public class Reporting {

    CustomKeywords customKeywords = new CustomKeywords();


    //String browserName;

    //@Parameters({ "OS", "browser" })
    @BeforeTest
    public void startReport() throws InterruptedException {

        String osName = readConfig("os");

        switch (osName) {
            case "macOS":
                GlobalVariables.OS_NAME = MACOS;
                break;
            case "Windows":
                GlobalVariables.OS_NAME = WINDOWS;
                break;
            case "Linux":
                GlobalVariables.OS_NAME = LINUX;
                break;
        }

        System.out.println("GlobalVariables.OS_NAME is: " + GlobalVariables.OS_NAME);

        GlobalVariables.BROWSER_NAME = null;

        //driver = driver;

    }

    @AfterSuite
    public void aftersuite() throws ParserConfigurationException, SAXException, IOException, InterruptedException {

        Properties props = new Properties();

        // this will set host of server- you can change based on your requirement
        props.put("mail.smtp.host", "smtp.gmail.com");

        // set the port of socket factory
        props.put("mail.smtp.socketFactory.port", "587");  // 587 for TLS  OR  465 for SSL

        // set socket factory
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

        // set the authentication to true
        props.put("mail.smtp.auth", "true");

        // set the port of SMTP server
        props.put("mail.smtp.port", "587"); //465

        props.put("mail.smtp.starttls.enable", true);

        // This will handle the complete authentication
        Session session = Session.getDefaultInstance(props,

                new javax.mail.Authenticator() {

                    protected PasswordAuthentication getPasswordAuthentication() {

                        return new PasswordAuthentication("Sender Email", "Password");

                    }

                });

        try {

            // Create object of MimeMessage class
            Message message = new MimeMessage(session);

            // Set the from address
            message.setFrom(new InternetAddress("mohammad.d@sitech.me"));

            // Set the recipient address
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("Recipient Email/Emals"));

            ////////////////// Gmail properties end here ////////////////////


            // Setting GlobalVariables of Reports' Paths
            GlobalVariables.FRONTEND_SUITE_REPORT_PATH = customKeywords.settingGBsSuitesPaths("jsonServer");
            GlobalVariables.AUTHENTICATION_SUITE_REPORT_PATH = customKeywords.settingGBsSuitesPaths("travelInsurance");


            // Mention the file which you want to send
            String userDirectory = System.getProperty("user.dir");
            String JSS = userDirectory + GlobalVariables.FRONTEND_SUITE_REPORT_PATH;
            String TIS = userDirectory + GlobalVariables.AUTHENTICATION_SUITE_REPORT_PATH;


            String JSSJR =  userDirectory + GlobalVariables.FRONTEND_SUITE_JUNITREPORT_PATH;
            if(GlobalVariables.OS_NAME.equals(MACOS)) {
                JSSJR = JSSJR.replace("\\","//");
            }

            String TISJR =  userDirectory + GlobalVariables.AUTHENTICATION_SUITE_JUNITREPORT_PATH;
            if(GlobalVariables.OS_NAME.equals(MACOS)) {
                TISJR = TISJR.replace("\\","//");
            }

            String reportURL_JSS = null;
            String reportURL_TIS = null;


            // Add the subject link
            message.setSubject("API Test Automation Report");

            Multipart multipart = new MimeMultipart();

            BodyPart messageBodyPart2 = new MimeBodyPart();
            BodyPart messageBodyPart3 = new MimeBodyPart();

            if(!GlobalVariables.FRONTEND_SUITE_REPORT_PATH.equals("null")) {

                // The code of uploading the html report on drive goes here with the use of reportURL_SS
                // reportURL_SS will hold the report url from drive

                // Mention the file which you want to send

                //Create another object to add another content

                // Create data source and pass the filename
                DataSource source = new FileDataSource(JSS);
                // set the handler
                messageBodyPart2.setDataHandler(new DataHandler(source));

                // set the file
                messageBodyPart2.setFileName(JSS);

                multipart.addBodyPart(messageBodyPart2);


            }

            if(!GlobalVariables.AUTHENTICATION_SUITE_REPORT_PATH.equals("null")) {

                // The code of uploading the html report on drive goes here with the use of reportURL_SS
                // reportURL_SS will hold the report url from drive

                // Mention the file which you want to send

                //Create another object to add another content

                // Create data source and pass the filename
                DataSource source = new FileDataSource(TIS);
                // set the handler
                messageBodyPart3.setDataHandler(new DataHandler(source));

                // set the file
                messageBodyPart3.setFileName(TIS);

                multipart.addBodyPart(messageBodyPart3);


            }



            // Create object to add multimedia type content
            BodyPart messageBodyPart = new MimeBodyPart();

            // add body part 1
            //multipart.addBodyPart(messageBodyPart2);
            //multipart.addBodyPart(messageBodyPart3);


            // Collecting suites' results go here

            // #122440 Border
            // #6CA0B8 Suite name, Scenarios, and total
            // #77F49F Pass
            // #990000 Fail
            // #BBC656 Skip
            // #143FAA Link

            String html_fixed_values = "<style>\r\n"
                    + "				table, th, td {\r\n"
                    + "				  border:1px solid #122440\r\n"
                    + "				}\r\n"
                    + "				</style>\r\n"
                    + "				<body>\r\n"
                    + "\r\n"
                    + "				<table style='width:50%; border:3px; border-collapse: collapse; border-color:#122440; color:122440' >\r\n"
                    + "				  <tr style= 'border:3px; border:solid; border-color:#122440;'>\r\n"
                    + "				    <th style= 'border:3px; border:solid; border-color:#122440; color: #6CA0B8;'>Suite Name</th>\r\n"
                    + "				    <th style= 'border:3px; border:solid; border-color:#122440; color: #6CA0B8; text-align:center; padding-right: 2px; padding-left: 2px;'>Scenarios</th>\r\n"
                    + "				    <th style= 'border:3px; border:solid; border-color:#122440; color: #6CA0B8; text-align:center; padding-right: 2px; padding-left: 2px;'>Total</th>\r\n"
                    + "				    <th style= 'border:3px; border:solid; border-color:#122440; color: #64CEC0; text-align:center; padding-right: 2px; padding-left: 2px;'>Passed</th>\r\n"
                    + "				    <th style= 'border:3px; border:solid; border-color:#122440; color: #990000; text-align:center; padding-right: 2px; padding-left: 2px;'>Failed</th>\r\n"
                    + "				    <th style= 'border:3px; border:solid; border-color:#122440; color: #BBC656; text-align:center; padding-right: 2px; padding-left: 2px;'>Skipped</th>    \r\n"
                    + "				    <th style= 'border:3px; border:solid; border-color:#122440; color: #143FAA; text-align:center; padding-right: 2px; padding-left: 2px;'>Report Link</th>    \r\n"
                    + "				  </tr>\r\n";




            String html_dynamic_values = " ";

            if(!GlobalVariables.FRONTEND_SUITE_REPORT_PATH.equals("null")) {
                reading_junitReports(JSSJR);
                reading_HTMLReport(JSS);

                html_dynamic_values = html_dynamic_values
                        + "				  <tr style= 'border:3px; border:solid; border-color:#122440;'>\r\n"
                        + "				    <td style= 'border:3px; border:solid; border-color:#122440; color: #6CA0B8' >Json Server Suite</td>\r\n"
                        + "				    <td style= 'border:3px; border:solid; border-color:#122440; color: #6CA0B8 '>1</td>\r\n"
                        + "				    <td style= 'border:3px; border:solid; border-color:#122440; color: #6CA0B8 '>"+totalTests+"</td>\r\n"
                        + "				    <td style= 'border:3px; border:solid; border-color:#122440; color: #64CEC0 '>"+passedTests+"</td>\r\n"
                        + "				    <td style= 'border:3px; border:solid; border-color:#122440; color: #990000 '>"+failedTests+"</td>\r\n"
                        + "				    <td style= 'border:3px; border:solid; border-color:#122440; color: #BBC656 '>"+skippedTests+"</td>    \r\n"
                        + "				    <td style= 'border:3px; border:solid; border-color:#122440; color: #143FAA '> <a href="+reportURL_JSS+">Click here</a></td>    \r\n"
                        + "				  </tr>\r\n";
            }

            if(!GlobalVariables.AUTHENTICATION_SUITE_REPORT_PATH.equals("null")) {
                reading_junitReports(TISJR);
                reading_HTMLReport(TIS);

                html_dynamic_values = html_dynamic_values
                        + "				  <tr style= 'border:3px; border:solid; border-color:#122440;'>\r\n"
                        + "				    <td style= 'border:3px; border:solid; border-color:#122440; color: #6CA0B8' >Travel Insurance Suite</td>\r\n"
                        + "				    <td style= 'border:3px; border:solid; border-color:#122440; color: #6CA0B8 '>1</td>\r\n"
                        + "				    <td style= 'border:3px; border:solid; border-color:#122440; color: #6CA0B8 '>"+totalTests+"</td>\r\n"
                        + "				    <td style= 'border:3px; border:solid; border-color:#122440; color: #64CEC0 '>"+passedTests+"</td>\r\n"
                        + "				    <td style= 'border:3px; border:solid; border-color:#122440; color: #990000 '>"+failedTests+"</td>\r\n"
                        + "				    <td style= 'border:3px; border:solid; border-color:#122440; color: #BBC656 '>"+skippedTests+"</td>    \r\n"
                        + "				    <td style= 'border:3px; border:solid; border-color:#122440; color: #143FAA '> <a href="+reportURL_TIS+">Click here</a></td>    \r\n"
                        + "				  </tr>\r\n";
            }





            String html_end_of_table  =
                    "				</table>\r\n"
                            + "				</body>";

            messageBodyPart.setContent("<p>Dears,</p>\r\n"+"<p>Here're API Automation Test results of capital bank for your kind perusal.</p>\r\n"+html_fixed_values+html_dynamic_values+html_end_of_table+ "<p>Best regards,</p>"+"<p>SiTech Test Automation Team</p>", "text/html");
            multipart.addBodyPart(messageBodyPart);


            // set the content
            message.setContent(multipart);


            // finally send the email

            Transport.send(message);

            System.out.println("\n\n"+" ========== EMAIL HAS BEEN GENERATED AND SENT ========== "+"\n\n");


        } catch (MessagingException e) {

            throw new RuntimeException(e);

        }

    }

    static String totalTests;
    static String passedTests;
    static String failedTests;
    static String skippedTests;
    static JTable container;

    public static String reading_HTMLReport(String report_Path){

        // Reading HTML file
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(report_Path));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
        }
        String content = contentBuilder.toString();



        org.jsoup.nodes.Document doc = Jsoup.parse(content);
        JsonObject jsonObject = new JsonObject();
        JsonArray list = new JsonArray();

        org.jsoup.nodes.Element Script = doc.select("script").get(0);



        String valueOfPass = Script.toString();



        if (valueOfPass.contains("passParent:")){


            valueOfPass= valueOfPass.split("passParent:")[1];


            valueOfPass = valueOfPass.replaceFirst(",", "@");

            valueOfPass = valueOfPass.split("@")[0];

            System.out.println("valueOfPass is =="+valueOfPass);

        }


        String valueOfFail = Script.toString();
        if (valueOfFail.contains("failParent:")){

            valueOfFail= valueOfFail.split("failParent:")[1];

            valueOfFail = valueOfFail.replaceFirst(",", "@");

            valueOfFail = valueOfFail.split("@")[0];

            System.out.println("valueOfFail is =="+valueOfFail);

        }


			 /*String valueOfSkip = Script.toString();
			 if (valueOfSkip.contains("skipParent:")){
				//skipParent//exceptionsParent
				 valueOfSkip= valueOfSkip.split("skipParent:")[1];
				 valueOfSkip = valueOfSkip.replaceFirst(",", "@");
				 valueOfSkip = valueOfSkip.split("@")[0];
				  System.out.println("valueOfSkip is =="+valueOfSkip);
			 }*/

        passedTests = valueOfPass; failedTests = valueOfFail;

        return "Passed Test Cases: "+valueOfPass + "   / "+"Failed Test Cases: "+valueOfFail;

    }




    ////////////////////////////////////////////////////////
    //File file = new File(user_Directory+"\\target\\surefire-reports\\junitreports\\TEST-RESTAssured.Strength_Not_Divisible.Scenario_Strength_Not_Divisible.xml");
    //NodeList nodeList = (NodeList) ((org.w3c.dom.Document) doc).getElementsByTagName("testsuite");

    public static String reading_junitReports(String junitreport_Path)  throws ParserConfigurationException, SAXException, IOException{

        String user_Directory = System.getProperty("user.dir");

        File xmlFile = new File(junitreport_Path);
        // Let's get XML file as String using BufferedReader
        // FileReader uses platform's default character encoding
        // if you need to specify a different encoding,
        // use InputStreamReader
        Reader fileReader = new FileReader(xmlFile);
        BufferedReader bufReader = new BufferedReader(fileReader);
        StringBuilder sb = new StringBuilder();
        String line = bufReader.readLine();
        while (line != null) {
            sb.append(line).append("\n");
            line = bufReader.readLine();
        }

        String xml2String = sb.toString();
        //System.out.println("XML to String using BufferedReader : ");
        //System.out.println(xml2String); bufReader.close();

        String valueOfTests = xml2String;
        if (valueOfTests.contains("tests=")) {

            valueOfTests = valueOfTests.split("tests=")[1];

            //System.out.println(valueOfTests);

            valueOfTests = valueOfTests.replaceFirst("\"", "@");

            // System.out.println(valueOfTests);

            valueOfTests = valueOfTests.replaceFirst("@", "").trim();

            //System.out.println(valueOfTests);

            valueOfTests = valueOfTests.replaceFirst("\"", "@");

            // System.out.println(valueOfTests);

            valueOfTests = valueOfTests.split("@")[0];

            System.out.println("valueOfTests is ==" + valueOfTests);
        }

        String valueOfFail = xml2String;
        if (valueOfFail.contains("failures=")) {

            valueOfFail = valueOfFail.split("failures=")[1];

            //System.out.println(valueOfFail);

            valueOfFail = valueOfFail.replaceFirst("\"", "@");

            //System.out.println(valueOfFail);

            valueOfFail = valueOfFail.replaceFirst("@", "").trim();

            //System.out.println(valueOfFail);

            valueOfFail = valueOfFail.replaceFirst("\"", "@");

            //System.out.println(valueOfFail);

            valueOfFail = valueOfFail.split("@")[0];

            //System.out.println(valueOfFail);
        }

        String valueOfSkip = xml2String;
        if (valueOfSkip.contains("skipped=")) {

            valueOfSkip = valueOfSkip.split("skipped=")[1];

            //System.out.println(valueOfSkip);

            valueOfSkip = valueOfSkip.replaceFirst("\"", "@");

            //System.out.println(valueOfSkip);

            valueOfSkip = valueOfSkip.replaceFirst("@", "").trim();

            //System.out.println(valueOfSkip);

            valueOfSkip = valueOfSkip.replaceFirst("\"", "@");

            //System.out.println(valueOfSkip);

            valueOfSkip = valueOfSkip.split("@")[0];

            System.out.println("valueOfSkip is ==" + valueOfSkip);
        }

        totalTests = valueOfTests;
        skippedTests = valueOfSkip;
        return "Total Test Cases: " + valueOfTests + "   / " + "Skipped Test Cases: " + valueOfSkip + "   / ";

    }


}
