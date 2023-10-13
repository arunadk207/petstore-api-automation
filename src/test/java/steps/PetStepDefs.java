package steps;


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
        request = given();
        request.contentType("application/json");
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
    }

    @Given("pet pet {int}  number")
    public void pet_pet_number(int id) {
        request = given();
        request.contentType("application/json");
        request.basePath("/pet/" + id);
    }

    @When("user invokes the api to read pet")
    public void userInvokesTheApiToReadPet() {
        response = request.get();
    }

    @Then("pet id {int} details need to be retrieved")
    public void pet_id_details_need_to_be_retrieved(int id) {
        if (id == 1) {
            Assert.assertEquals(200, response.statusCode());
            JsonPath body = response.jsonPath();
            Object responseId = body.get("id");
            Assert.assertEquals(id, responseId);
        } else {
            Assert.assertEquals(404, response.statusCode());
            JsonPath body = response.jsonPath();
            Object message = body.get("message");
            Assert.assertEquals("Pet not found", message);
        }
    }

    @Given("pet {int} number")
    public void petIdNumber(int id) {
        request = given();
        request.contentType("application/json");
        request.basePath("/pet/" + id);
    }

    @When("user invokes the api to delete pet")
    public void userInvokesTheApiToDeletePet() {
        response = request.delete();
    }

    @Then("pet with {int} deleted")
    public void petWithIdDeleted(int id) {
        int statusCode = response.statusCode();
        Assert.assertEquals(200, statusCode);
    }

    @Given("pet {int} {string} and {string}")
    public void petIdNameNameAndStatusStatus(int id, String name, String status) {
        request = given();
        request.contentType("application/json");
        request.basePath("/pet/" + id);
        request.body("name=" + name + "&status=" + status);
    }

    @When("uses invokes the api to update pet")
    public void usesInvokesTheApiToUpdatePet() {

        response = request.post();
    }

    @Then("pet with {int} updated")
    public void petWithIdUpdated(int id) {
        int statusCode = response.statusCode();
        Assert.assertEquals(415, statusCode);
    }
}
