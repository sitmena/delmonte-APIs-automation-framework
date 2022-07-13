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

public class PasswordReset {

    public void passwordReset()throws IOException, InterruptedException {

        CustomKeywords CustomKeywords = new CustomKeywords();

        baseURI = readConfig("baseUriFrontEnd");

        String body;

        JSONArray array = new JSONArray();
        JSONObject item = new JSONObject();

        String jsonString = new JSONObject()
                .put("email", "mohammad.d@sitech.me")
                .toString();

        body = jsonString;

        GlobalVariables.REQUEST_BODY = body;

        Response response =  given()
                .header("Authorization", "Bearer " + GlobalVariables.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                //.param("lastName", "Dabbagh")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(body)
                .when()
                .post("/auth/password_reset")
                .then()
                .statusCode(200)
                .log().all()  // This is to log all the response on the consol
                .extract().response();

        GlobalVariables.CONSOL = response.jsonPath().prettify();

        Assert.assertEquals(response.getStatusCode(), 200, "header status code is not 200");

    }


}
