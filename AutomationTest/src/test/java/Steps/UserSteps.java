package Steps;

import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import javax.validation.constraints.AssertTrue;
import java.io.File;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserSteps {
    private static RequestSpecification request;
    private static Response response;

    String URI = "http://localhost:8080/api/v3";
    @Given("PetStore manager wants to organize the users")
    public void getLoginPage(){
        request = given().baseUri(URI).contentType(ContentType.JSON);
    }

    @When("the manager creates a new user with valid data")
    public void createNewUser() {
        File requestBody = new File("src/test/java/Data/Users/CreateUser.json");
        response = request.when().body(requestBody).post("/user").prettyPeek();
        assertEquals(200, response.getStatusCode());
    }

    @Then("the manager validates the new user was created")
    public void validateNewUser() {
        String username = response.jsonPath().getString("username");
        response = request.get("/user/" + username);
        response.then().statusCode(200);
    }

    @When("the manager logs in with user {string} and password {string}")
    public void logInUser(String user, String password) {
        response = request.queryParam("username", user).queryParam("password", password).get("/user/login").prettyPeek();
        assertEquals(200, response.getStatusCode());
    }
    @Then("the manager logs the user out")
    public void logOutUser() {
        response = request.get("/user/logout").prettyPeek();
        assertEquals(200, response.getStatusCode());

    }

    @When("the manager updates the user {string} with valid data")
    public void updateUser(String user) {
        File requestBody = new File("src/test/java/Data/Users/UpdateUser.json");
        response = request.when().body(requestBody).put("/user/" + user).prettyPeek();
        assertEquals(200, response.getStatusCode());
    }

    @Then("the manager validates user was updated")
    public void validateUserUpdated() {
        String username = response.jsonPath().getString("username");
        String firstName = response.jsonPath().getString("firstName");
        response = request.get("/user/" + username);
        assertEquals(firstName, response.jsonPath().getString("firstName"));
    }
    @When("the manager deletes the user {string}")
    public void deleteUser(String user) {
        response = request.when().delete("/user/" + user).prettyPeek();
        assertEquals(200, response.getStatusCode());
    }

    @Then("the manager validates {string} was deleted")
    public void validateUserDeleted(String user) {
        response = request.get("/user/" + user);
        assertEquals(404, response.getStatusCode());


    }
}
