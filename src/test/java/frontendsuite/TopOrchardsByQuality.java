package frontendsuite;

import helpers.CustomKeywords;
import helpers.GlobalVariables;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.IOException;

import static helpers.ReadingConfigFileData.readConfig;
import static io.restassured.RestAssured.given;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class TopOrchardsByQuality {

    public void topOrchardsByQuality()throws IOException, InterruptedException {

        CustomKeywords CustomKeywords = new CustomKeywords();
        baseURI = readConfig("baseUriFrontEnd");

        int min_quality_value = 1;
        String min_quality = "min_quality = "+ min_quality_value;

        String  quality_period_value = "overall";
        String quality_period = "quality_period = "+quality_period_value;

        int page_size_value = 10;  //9246
        String page_size = "page_size = "+page_size_value;

        int page_num_value = 1;
        String page_num = "page_num = "+page_num_value;

        String orchard_name_value = "JOYA";
        String orchard_name = "orchard_name = "+orchard_name_value;


        Response response =
                given()
                        .header("Authorization", "Bearer "+GlobalVariables.ACCESS_TOKEN)
                        .param("min_quality", min_quality_value)
                        .param("quality_period", quality_period_value)
                        .param("page_size", page_size_value)
                        .param("page_num", page_num_value)
                        //.param("orchard_name", orchard_name_value)
                        .when()
                        .get("/prediction/supply-model/top-orchards-by-quality")
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .log().all()  // This is to log all the response on the consol
                        //.body("jicoId", equalToPath("jicoId"))
                        //.body("lastName[1:]", equalTo("Dabbagh"))
                        .contentType(ContentType.JSON)
                        .extract().response();

        GlobalVariables.CONSOL = response.jsonPath().prettify();

//        GlobalVariables.REQUEST_BODY= GlobalVariables.REQUEST_BODY + "\n"+ min_quality+"\n"+ quality_period+"\n"+page_size+"\n"+page_num+"\n"+orchard_name;

        GlobalVariables.REQUEST_BODY = "\n"+ min_quality+"\n"+ quality_period+"\n"+page_size+"\n"+page_num;


    }


}
