package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.UserModel;

import static data.TestData.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserSteps {

    public static final String PATH_CREATE_USER = "/api/auth/register";

    @Step("Создание нового пользователя")
    public static Response createNewUser() {
        UserModel userModel = new UserModel()
                .setEmail(RANDOM_EMAIL)
                .setPassword(RANDOM_PASSWORD)
                .setName(RANDOM_NAME);

        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(userModel)
                .when()
                .post(PATH_CREATE_USER)
                .then()
                .extract().response();
    }

    @Step("Проверка, что пользователь с валидными данными действительно создан")
    public static void checkStatusNewUser(Response response) {
        response.then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Step("Проверка, что пользователь  с уже существующими данными повторно в системе не зарегистрирован")
    public static void checkStatusAfterRecreatingUser(Response response) {
        response.then()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @Step("Создание нового пользователя без обязательного поля email")
    public static Response createNewUserWithoutEmail() {
        UserModel userModel = new UserModel()
                .setEmail(null)
                .setPassword(RANDOM_PASSWORD)
                .setName(RANDOM_NAME);

        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(userModel)
                .when()
                .post(PATH_CREATE_USER)
                .then()
                .extract().response();
    }

    @Step("Проверка, что пользователь  без указания email в системе не зарегистрирован")
    public static void checkStatusAfterRegistrationWithoutEmail(Response response) {
        response.then()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}