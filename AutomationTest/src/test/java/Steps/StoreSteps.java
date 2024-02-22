package Steps;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StoreSteps {
    private static RequestSpecification request;
    private static Response response;

    String URI = "http://localhost:8080/api/v3";

    int quantityInitial;

    @Given("PetStore manager wants to organize the inventory")
    public void getStorePage(){
        request = given().baseUri(URI).contentType(ContentType.JSON);
    }

    @When("the manager creates a new order with valid data")
    public void createANewOrder() {
        quantityInitial = request.get("/store/inventory").jsonPath().getInt("placed");
        File requestBody = new File("src/test/java/Data/Store/CreateOrder.json");
        response = request.when().body(requestBody).post("/store/order").prettyPeek();
        assertEquals(200, response.getStatusCode());
    }
    @Then("the manager validates that order was created in the inventory")
    public void validateOrderWasCreated() {
        int orderID = response.jsonPath().getInt("id");
        response = request.when().get("/store/order/" + orderID);
        response.then().assertThat().statusCode(200);
    }

    @And("the manager validates quantities in the inventory")
    public void validateQuantitiesInTheInventory() {
        response = request.when().get("/store/inventory");
        int quantityUpdated = response.jsonPath().getInt("placed");
        assertNotEquals(quantityInitial,quantityUpdated, "The inventory was not updated");
    }

    @When("the manager deletes an order with id {int}")
    public void deleteAnOrder(int orderID) {
        response = request.when().delete("/store/order/" + orderID).prettyPeek();
        assertEquals(200, response.getStatusCode());
    }

    @Then("the manager validates the order {int} was eliminated in the inventory")
    public void validateOrderWasDeleted(int orderID) {
        response = request.when().get("/store/order/" + orderID);
        response.then().assertThat().statusCode(404);
    }

}
