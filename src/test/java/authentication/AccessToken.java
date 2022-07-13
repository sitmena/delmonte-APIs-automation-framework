package authentication;

import helpers.CustomKeywords;
import helpers.GlobalVariables;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.IOException;

import static helpers.ReadingConfigFileData.readConfig;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class AccessToken {

    public void accessToken()throws IOException, InterruptedException{

        CustomKeywords CustomKeywords = new CustomKeywords();

        baseURI = readConfig("baseUriFrontEnd");


        String body;

        JSONArray array = new JSONArray();
        JSONObject item = new JSONObject();

        String jsonString = new JSONObject()
                .put("email", "mohammad.d@sitech.me")  //.put("email", "admin@admin.me")
                .put("password", "********")  //.put("password", "*********")
                .toString();

        body = jsonString;

        GlobalVariables.REQUEST_BODY = body;

        Response response =  given()
                .header("Content-Type", "application/json")
                //.param("lastName", "Dabbagh")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(body)
                .when()
                .post("/auth/token/")
                .then()
                .statusCode(200)
                .log().all()  // This is to log all the response on the consol
                .extract().response();

        GlobalVariables.CONSOL = response.jsonPath().prettify();

        Assert.assertEquals(response.getStatusCode(), 200, "header status code is not 200");

        GlobalVariables.ACCESS_TOKEN =   response.jsonPath().get("access");

        System.out.println("ACCESS_TOKEN = "+GlobalVariables.ACCESS_TOKEN);
    }


}
