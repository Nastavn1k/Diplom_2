package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static data.TestData.BASE_URL;
import static steps.CreateUserSteps.*;
import static steps.DeleteUserSteps.*;

public class CreateUserTest {

    private Response response;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void createNewUserTest() {
        response =
                createNewUser();
        checkStatusNewUser(response);
    }

    @Test
    public void recreatingRegisteredUserTest() {
        response =
                createNewUser();
        checkStatusNewUser(response);
        Response responseAfterRecreating = createNewUser();
        checkStatusAfterRecreatingUser(responseAfterRecreating);
    }

    @Test
    public void createNewUserWithoutEmailTest() {
        response =
                createNewUserWithoutEmail();
        checkStatusAfterRegistrationWithoutRequiredField(response);
    }

    @Test
    public void createNewUserWithoutPasswordTest() {
        response =
                createNewUserWithoutPassword();
        checkStatusAfterRegistrationWithoutRequiredField(response);
    }

    @Test
    public void createNewUserWithoutNameTest() {
        response =
                createNewUserWithoutName();
        checkStatusAfterRegistrationWithoutRequiredField(response);
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
