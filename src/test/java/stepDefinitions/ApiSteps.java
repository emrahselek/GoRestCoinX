package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import junit.framework.AssertionFailedError;
import org.apache.http.util.Asserts;
import org.junit.Assert;
import pages.ApiValidation;

public class ApiSteps extends ApiValidation {
    @Given("user given api url {string}")
    public void userGivenApiUrl(String url) {
        RestAssured.baseURI = url;
        System.out.println("user given api url " + url);
    }

    @Given("set api endpoint {string}")
    public void setApiEndpoint(String endpoint) {
        RestAssured.basePath = endpoint;
        //RestAssured.port=8080;

        System.out.println("set api endpoint " + endpoint);
    }

    @And("User creates new user with request body {string},{string},{string},{string}")
    public void userCreatesNewUserWithRequestBody(String name, String gender, String email, String status) {
        response = postMethod(name, gender, email, status);
        response.prettyPrint();

    }

    @Then("validate the status code {int}")
    public void validateTheStatusCode(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        try {
            Assert.assertEquals(expectedStatusCode, actualStatusCode);
        } catch (AssertionFailedError ae) {
            ae.printStackTrace();
        }

        System.out.println("Assertion Successful");
    }

    @And("validate the userId is not null")
    public void validateTheUserIdIsNotNull() {
        int id = response.jsonPath().getInt("data.id");
        Assert.assertTrue("id is empty", id != 0);
    }

    @And("validate the user Name is {string}")
    public void validateTheUserNameIs(String expectedName) {
        String actualName = response.jsonPath().getString("data.name");
        Assert.assertEquals(expectedName, actualName);
        System.out.println("Assertion successful " + actualName);
    }

    @And("validate the user Gender is {string}")
    public void validateTheUserGenderIs(String gender) {
        String actualGender = response.jsonPath().getString("data.gender");
        Assert.assertEquals(gender, actualGender);
        System.out.println("Assertion successful " + actualGender);
    }

    @And("validate the user Email is {string}")
    public void validateTheUserEmailIs(String email) {
        String actualEmail = response.jsonPath().getString("data.email");
        Assert.assertEquals(email, actualEmail);
        System.out.println("Assertion successful " + actualEmail);
    }

    @And("validate the user Status is {string}")
    public void validateTheUserStatusIs(String status) {
        String actualStatus = response.jsonPath().getString("data.status");
        Assert.assertEquals(status, actualStatus);
        System.out.println("Assertion successful " + actualStatus);
    }

    @Then("validate the user details")
    public void validateTheUserDetails(DataTable dataTable) {
        System.out.println(dataTable.asList());
		/*int id = response.jsonPath().getInt("data.id");
		Assert.assertTrue("id is empty", id != 0);
		String actualName = response.jsonPath().getString("data.name");
		Assert.assertEquals(expectedName, actualName);
		System.out.println("Assertion successful " + actualName);
		String actualGender = response.jsonPath().getString("data.gender");
		Assert.assertEquals(gender, actualGender);
		System.out.println("Assertion successful " + actualGender);
		String actualEmail = response.jsonPath().getString("data.email");
		Assert.assertEquals(email, actualEmail);
		System.out.println("Assertion successful " + actualEmail);
		String actualStatus = response.jsonPath().getString("data.status");
		Assert.assertEquals(status, actualStatus);
		System.out.println("Assertion successful " + actualStatus);*/

    }

    @Given("set api endpoint {string}{string}")
    public void setApiEndpoint(String endpoint, String userId) {
        RestAssured.basePath = (endpoint + userId);
    }

    @And("Update the user with request body {string},{string},{string},{string}")
    public void updateTheUserWithRequestBody(String name, String gender, String email, String status) {
        response = putMethod(name, gender, email, status);
        response.prettyPrint();
    }


    /**
     * Post and Comment scenario start from here
     */


    @Given("user sets {string} post")
    public void userSetsPost(String endpointPost) {
        RestAssured.basePath = endpointPost;
    }
    int postId;
    @And("create a post with given userId and create one {string} and {string}")
    public void createAPostWithGivenUserIdAndCreateOneAnd(String body, String title) {
        response = postMethodCreate(body, title);
        response.prettyPrint();
        postId=response.jsonPath().get("data.id");
    }


    @When("user sets {string} post and create one {string} using {string}, {string}, {string}, {string}")
    public void userSetsPostAndCreateOneUsing(String endpointComment, String comment, String userId, String name, String email, String commentBody) {
        endpointComment=endpointComment.replaceAll(userId, String.valueOf(postId));
        RestAssured.basePath = endpointComment;
        System.out.println(endpointComment);
        response = postMethodComment(comment, name, email);
        response.prettyPrint();
    }


    @Then("verify that comment created {string} {string}")
    public void verifyThatCommentCreated(String name, String email) {
        String actualName = response.jsonPath().get("data.name");
        String actualEmail = response.jsonPath().get("data.email");
        Assert.assertEquals(name, actualName);
        Assert.assertEquals(email, actualEmail);
        System.out.println("Assertion successful");
    }


}