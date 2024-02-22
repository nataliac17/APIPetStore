package Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PetSteps {

    private static RequestSpecification request;
    private static Response response;

    private ValidatableResponse json;
    String URI = "http://localhost:8080/api/v3";

    @Given("PetStore manager wants to organize the pets in the inventory")
    public void getStatusPetPage(){
        request = given().baseUri(URI).contentType(ContentType.JSON);
    }

    @When("the manager creates a new pet with valid data")
    public void createNewPet() {
        File requestBody = new File("src/test/java/Data/Pet/CreatePet.json");
        response = request.when().body(requestBody).post("/pet").prettyPeek();
        assertEquals(200, response.getStatusCode());
    }
    @Then("the manager validates that {string} was created by Tags")
    public void managerValidatesTheNewPet(String expectedValue) {
        String petTags = response.jsonPath().getString("tags.name");
        String tag = petTags.substring(1, petTags.length()-1);
        response = request.queryParam("tags", tag).get("/pet/findByTags");
        String jsonResponse = response.jsonPath().getString("name");
        assertTrue(jsonResponse.contains(expectedValue), "The Pet " +expectedValue+ " was not created");
    }
    @When("the manager updates a pet with valid data")
    public void updateAPetWithValidData() {
        File requestBody = new File("src/test/java/Data/Pet/UpdatePet.json");
        response = request.when().body(requestBody).put("/pet").prettyPeek();
        assertEquals(200, response.getStatusCode());
    }

    @Then("the manager validates that {string} was updated by status")
    public void managerValidatesWasUpdatedByStatus(String petName) {
        String petStatus= response.jsonPath().getString("status");
        response = request.queryParam("status",petStatus).get("/pet/findByStatus");
        List<String> jsonResponse = response.jsonPath().getList("name");
        assertTrue(jsonResponse.contains(petName), "The Pet " +petName+ " was not updated");
    }
    @When("the manager deletes a pet {int}")
    public void deleteAPet(int petID) {
        response = request.delete("/pet/" + petID);
        assertEquals(200, response.getStatusCode());
    }
    @Then("the manager validates that pet {int} was deleted by ID")
    public void managerValidatesThatWasDeletedByTags(int petID) {
        response = request.get("/pet/" + petID);
        assertEquals(404, response.getStatusCode());
    }
}
