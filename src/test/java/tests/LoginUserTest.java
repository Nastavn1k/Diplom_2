package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static data.TestData.BASE_URL;
import static steps.CreateUserSteps.checkStatusNewUser;
import static steps.CreateUserSteps.createNewUser;
import static steps.DeleteUserSteps.*;
import static steps.LoginUserSteps.*;

public class LoginUserTest {

    Response response;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        response =
                createNewUser();
        checkStatusNewUser(response);
    }

    @Test
    public void authorizationUserTest() {
        response =
                authorizationUser();
        checkAuthorizationUser(response);
    }

    @Test
    public void authorizationWithIncorrectEmailTest() {
        Response responseAfterAuthorizationWithIncorrectEmail =
                authorizationWithIncorrectEmail();
        checkAuthorizationWithIncorrectDataUser(responseAfterAuthorizationWithIncorrectEmail);
    }

    @Test
    public void authorizationWithIncorrectPasswordTest() {
        Response responseAfterAuthorizationWithIncorrectPassword =
                authorizationWithIncorrectPassword();
        checkAuthorizationWithIncorrectDataUser(responseAfterAuthorizationWithIncorrectPassword);
    }

    @After
    public void cleanUp() {
        String accessToken = findAccessToken(response);
        if (accessToken != null) {
            response =
                    deleteUser(accessToken);
            checkDeleteUser(response);
        }
    }
}
