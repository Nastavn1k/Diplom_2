package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static data.TestData.BASE_URL;
import static steps.CreateOrderSteps.*;
import static steps.CreateUserSteps.checkStatusNewUser;
import static steps.CreateUserSteps.createNewUser;
import static steps.DeleteUserSteps.*;

public class CreateOrderTest {

    Response response;
    String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        response =
                createNewUser();
        checkStatusNewUser(response);
        accessToken =
                findAccessToken(response);
    }

    @Test
    public void createOrderWithAuthorizationTest() {
        response =
                createOrderWithAuthorization(accessToken);
        checkCreateOrderWithAuthorization(response);
    }

    @Test
    public void createOrderWithoutAuthorizationTest() {
        response =
                createOrderWithoutAuthorization();
        checkCreateOrderWithoutAuthorization(response);
    }

    @Test
    public void createOrderWithAuthorizationAndIngredientsTest() {
        response =
                createOrderWithAuthorization(accessToken);
        checkCreateOrderWithAuthorization(response);
    }

    @Test
    public void checkCreateOrderWithAuthorizationWithoutAddIngredientsTest() {
        response =
                createOrderWithAuthorizationWithoutAddIngredients(accessToken);
        checkCreateOrderWithAuthorizationWithoutAddIngredients(response);
    }

    @Test
    public void createOrderWithAuthorizationAndFalseHashTest() {
        response =
                createOrderWithAuthorizationAndFalseHash(accessToken);
        checkCreateOrderWithAuthorizationAndFalseHash(response);
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            response =
                    deleteUser(accessToken);
            checkDeleteUser(response);
        }
    }
}
