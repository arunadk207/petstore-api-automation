package steps;

import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

public class OrderStepDef {

    private Response response;
    private RequestSpecification request;

    @BeforeStep
    public static void init() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Given("Pet order {int} {int} {int} details")
    public void petOrderIdPetIdQuantityDetails(int id, int petId, int quantity) {
        request = given();
        request.contentType("application/json");
        request.body("{\n" +
                "  \"id\": " + id + ",\n" +
                "  \"petId\": " + petId + ",\n" +
                "  \"quantity\": " + quantity + ",\n" +
                "  \"shipDate\": \"" + LocalDateTime.now() + "\",\n" +
                "  \"status\": \"placed\",\n" +
                "  \"complete\": true\n" +
                "}");
    }

    @When("user invokes the order api to place an order")
    public void userInvokesTheOrderApiToPlaceAnOrder() {
        response = request.post("/store/order");
    }

    @Then("order id {int} is placed")
    public void orderIdIdIsPlaced(int id) {
        int statusCode = response.statusCode();
        Assert.assertEquals(200, statusCode);
        JsonPath jsonPath = response.jsonPath();
        int orderId = jsonPath.get("id");
        Assert.assertEquals(id, orderId);
    }

    @Given("order id {int}")
    public void orderIdId(int orderId) {
        request = given();
        request.contentType("application/json");
        request.basePath("/store/order/" + orderId);
    }

    @When("user invokes the order api to get orders")
    public void userInvokesTheOrderApiToGetOrders() {
        response = request.get();
    }

    @Then("order id {int} specific details are retrieved")
    public void orderIdIdSpecificDetailsAreRetrieved(int orderId) {
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);
    }

    @When("user invokes the order api to delete order")
    public void userInvokesTheOrderApiToDeleteOrder() {
        response = request.delete();
    }

    @Then("given order id {int} been deleted")
    public void givenOrderIdIdBeenDeleted(int orderId) {
        Assert.assertEquals(200, response.getStatusCode());
    }

    @When("user invokes the inventory api to get inventory details")
    public void userInvokesTheInventoryApiToGetInventoryDetails() {
        request = given();
        request.contentType("application/json");
        request.basePath("/store/inventory");
        response = request.get();
    }

    @Then("inventory details been retrieved")
    public void inventoryDetailsBeenRetrieved() {
        Assert.assertEquals(200, response.getStatusCode());
    }
}
