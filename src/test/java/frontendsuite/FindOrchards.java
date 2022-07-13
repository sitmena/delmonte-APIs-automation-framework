package frontendsuite;

import helpers.CustomKeywords;
import helpers.GlobalVariables;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.IOException;

import static helpers.ReadingConfigFileData.readConfig;
import static io.restassured.RestAssured.given;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class FindOrchards {

    public void findOrchards()throws IOException, InterruptedException{

        CustomKeywords CustomKeywords = new CustomKeywords();



        baseURI = readConfig("baseUriFrontEnd");

        int quantity_percentage_value = 10;
        int size_value = 48;
        int page_size_value = 10;
        int page_num_value= 1;
        int category_value = 1;

        String quantity_percentage = "quantity_percentage: "+quantity_percentage_value;
        String size = "size: "+ size_value;
        String page_size = "page_size: "+page_size_value;
        String page_num = "page_num: "+ page_num_value;

        // String category = "category: "+category_value;

        Response response = null;

        try {

            response =
                    given()
                            .header("Authorization", "Bearer " + GlobalVariables.ACCESS_TOKEN)
                            .param("quantity_percentage", quantity_percentage_value)
                            .param("size", size_value)
                            .param("page_size", page_size_value)
                            .param("page_num", page_num_value)
                            .param("category", category_value)
                            .when()
                            .get("/prediction/supply-model/find-orchards")
                            .then()
                            .assertThat()
                            //.statusCode(200)
                            .log().all()
                            .contentType(ContentType.JSON)
                            .extract().response();

        }catch(Throwable t){

            response =
                    given()
                            .header("Authorization", "Bearer " + GlobalVariables.ACCESS_TOKEN)
                            .param("quantity_percentage", quantity_percentage_value)
                            .param("size", size_value)
                            .param("page_size", page_size_value)
                            .param("page_num", page_num_value)
                            .param("category", category_value)
                            .when()
                            .get("/prediction/supply-model/find-orchards")
                            .then()
                            .assertThat()
                            //.statusCode(200)
                            .log().all()
                            .contentType(ContentType.HTML)
                            .extract().response();
        }


        try {
            GlobalVariables.CONSOL = response.jsonPath().prettify();
        }catch(Throwable t){
            GlobalVariables.CONSOL = response.htmlPath().prettify();
        }

        //APIGlobalVariable.requestBody = APIGlobalVariable.requestBody + "\n"+ quantity_percentage+"\n"+ size+"\n"+page_size+"\n"+page_num+"\n"+category ;

        //APIGlobalVariable.requestBody = APIGlobalVariable.requestBody + "\n"+ quantity_percentage+"\n"+ size+"\n"+page_size+"\n"+page_num ;
        Assert.assertEquals(response.getStatusCode(), 200, "header status code is not 200");


    }
}
