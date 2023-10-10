package steps;


import io.cucumber.java.BeforeAll;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class PetStepDefs {
    private Response response;
    private RequestSpecification request;

    @BeforeStep
    public static void init() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        System.out.println("Test test");
    }

    @Given("pet id {string} and name as {string}")
    public void pet_details(String petId, String name) {
        request = given();
        request.contentType("application/json");
        request = request.body("{\n" +
                "    \"id\": " + petId + ",\n" +
                "    \"category\": {\n" +
                "        \"id\": 0,\n" +
                "        \"name\": \"" + name + "\"\n" +
                "    },\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"photoUrls\": [\n" +
                "        \"string\"\n" +
                "    ],\n" +
                "    \"tags\": [\n" +
                "        {\n" +
                "            \"id\": 0,\n" +
                "            \"name\": \"string\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"status\": \"available\"\n" +
                "}");
    }

    @When("user invokes the API to create a pet")
    public void user_invokes_the_api_to_create_a_pet() {
        response = request.post("/pet");
    }

    @Then("Pet object is created")
    public void pet_object_is_created() {
        int i = response.statusCode();
        Assert.assertEquals(200, i);
        Headers headers = response.getHeaders();
        Header header = headers.get("Content-Type");
        Assert.assertEquals("application/json", header.getValue());

        String responseBoy = response.asString();
        JsonPath from = JsonPath.from(responseBoy);
        Object id = from.get("id");
        Assert.assertEquals(1, id);
        Object name = from.get("name");
        Assert.assertEquals("tommy", name);
    }

    @When("user invokes the API to update a pet")
    public void userInvokesTheAPIToUpdateAPet() {
        response = request.put("/pet");
    }

    @Then("Pet is updated with new name")
    public void petIsUpdatedWithNewName() {
        int i = response.statusCode();
        String s = response.asString();
        JsonPath from = JsonPath.from(s);
        String name = from.get("name");
        Assert.assertEquals(200, i);
        Assert.assertEquals("Johnny", name);
    }

    @Given("pet {string} status")
    public void petStatus(String status) {
        request.basePath("/pet/findByStatus").queryParam("status", status);
    }

    @When("user invokes the api to read pets")
    public void userInvokesTheApiToReadPets() {
        response = request.get();
    }

    @Then("all pets should be returned by status")
    public void allPetsShouldBeReturnedByStatus() {
        int i = response.statusCode();
        Assert.assertEquals(200, i);
        System.out.println(response.getBody());
    }
}
