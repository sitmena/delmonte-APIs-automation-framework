package frontendsuite;

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

public class DatabricksSqlEndpoint {

    public void databricksSqlEndpoint()throws IOException, InterruptedException{

        CustomKeywords CustomKeywords = new CustomKeywords();

        baseURI = readConfig("baseUriDatabricksSqlEndpoint");

        String body;

        JSONArray array = new JSONArray();
        JSONObject item = new JSONObject();

//        String jsonString = new JSONObject()
//                .put("email", "mohammad.d@sitech.me")  //.put("email", "admin@admin.me")
//                .put("password", "********")  //.put("password", "*********")
//                .toString();

//        body = jsonString;

        body = "";

        GlobalVariables.REQUEST_BODY = body;

        Response response =  given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer dapi1c88ca826d52630b8b64d58472f28999")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                //.body(body)
                .when()
                .post("/start")
                .then()
                .statusCode(200)
                .log().all()  // This is to log all the response on the consol
                .extract().response();

        GlobalVariables.CONSOL = response.jsonPath().prettify();

        Assert.assertEquals(response.getStatusCode(), 200, "header status code is not 200");


    }



}
