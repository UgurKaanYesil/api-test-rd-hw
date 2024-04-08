import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("DemoQA Tests")
@Feature("Account Management")
public class UserPost {
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test to generate token")
    @Story("Generate Token")
    @Issue("DEMOQA-123")
    @Link(name = "DemoQA Website", url = "https://demoqa.com")
    public void postUser() {

        String url = "https://demoqa.com/Account/v1/User";

        String requestBody = "{\n" +
                "\"userName\": \"Özge Güney\",\n" +
                "\"password\": \"dsfdsfsd\"\n" +
                "}";
        String contentType = ContentType.JSON.toString();

        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", ContentType.JSON.toString());

        Response response = given()
                .contentType(contentType)
                .headers(header)
                .body(requestBody)
                .when().log().all()
                .post(url);

        System.out.println(response.getBody().asString());

        assertThat(response.contentType()).isEqualTo("application/json; charset=utf-8");
        assertThat(response.jsonPath().getString("username")).isEqualTo("Uğur Kaan Yeşil");
        String userID = response.jsonPath().getString("userID");
        assertThat(userID).isNotNull();

        System.out.println("Created user ID: " + userID);
        assertThat(response.statusCode()).isEqualTo(201);

    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test to create user")
    @Story("Create User")
    @Issue("DEMOQA-124")
    @Link(name = "DemoQA Website", url = "https://demoqa.com")
    public void generateToken() {

        RestAssured.baseURI = "https://demoqa.com";


        String requestBody = "{\n" +
                "  \"userName\": \"Uğur Kaan Yeşil\",\n" +
                "  \"password\": \"Adminpassw0rd!\"\n" +
                "}";


        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Content-Type", "application/json");


        Response response = given()
                .headers(headers)
                .body(requestBody)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .log().all()
                .extract().response();


        assertThat(response.contentType()).isEqualTo("application/json; charset=utf-8");
        String token = response.jsonPath().getString("token");
        assertThat(token).isNotNull();

        System.out.println("Generated Token: " + token);
        assertThat(response.statusCode()).isEqualTo(200);
    }

}
