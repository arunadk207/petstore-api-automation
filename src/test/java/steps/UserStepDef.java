package steps;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonArray;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class UserStepDef {

    private Response response;
    private RequestSpecification request;

    @BeforeStep
    public static void init() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }


    @Given("user details {int} {string} {string} {string} {string} {string} {string} {int}")
    public void userDetailsWithArray(int id, String userName, String firstName, String lastName, String email, String password, String phone, int userStatus) {
        request = given();
        request.contentType("application/json");
        JsonObject user = new JsonObject();
        user.add("id", id);
        user.add("username", userName);
        user.add("firstName", firstName);
        user.add("lastName", lastName);
        user.add("email", email);
        user.add("password", password);
        user.add("phone", phone);
        user.add("userStatus", userStatus);

        JsonArray users = new JsonArray();
        users.add(user);
        request.body(users.toString());

    }

    @When("user invokes the post API to create users")
    public void userInvokesThePostAPIToCreateUsers() {
        response = request.post("/user/createWithArray");
    }

    @Then("All the users are created.")
    public void allTheUsersAreCreated() {
        Assert.assertEquals(200, response.statusCode());
    }

    @When("user invokes the post API to create users with list")
    public void userInvokesThePostAPIToCreateUsersWithList() {
        response = request.post("/user/createWithList");
    }

    @Given("user id {string}")
    public void userIdId(String username) {
        request = given();
        request.contentType("application/json");
        request.basePath("/user/" + username);

    }

    @When("user invokes the get api to fetch user details")
    public void userInvokesTheGetApiToFetchUserDetails() {
        response = request.get();
    }

    @Then("user {string} specific details are retrieved")
    public void userIdSpecificDetailsAreRetrieved(String username) {
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);
    }

    @When("user invokes the PUT API to create users with list")
    public void userInvokesThePUTAPIToCreateUsersWithList() {
        response = request.put("/user/user100");
    }

    @Then("user is updated with latest data.")
    public void userIsUpdatedWithLatestData() {
        Assert.assertEquals(500, response.statusCode());
    }

    @When("user invokes the delete api to delete user")
    public void userInvokesTheDeleteApiToDeleteUser() {
        response = request.delete("/user/user100");
    }

    @Then("user {string} is deleted")
    public void userUsernameIsDeleted(String username) {
        Assert.assertEquals(404, response.statusCode());
    }
}
