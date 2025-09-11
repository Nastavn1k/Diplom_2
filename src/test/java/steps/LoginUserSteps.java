package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.AuthorizationModel;

import static data.TestData.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LoginUserSteps {

    public static final String PATH_LOGIN_USER = "/api/auth/login";

    @Step("Авторизация с применением существующих данных пользователя")
    public static Response authorizationUser() {
        AuthorizationModel authorizationModel = new AuthorizationModel()
                .setEmail(RANDOM_EMAIL)
                .setPassword(RANDOM_PASSWORD);

        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(authorizationModel)
                .when()
                .post(PATH_LOGIN_USER)
                .then()
                .extract().response();
    }

    @Step("Проверка, что пользователь с валидными данными действительно авторизирован")
    public static void checkAuthorizationUser(Response response) {
        response.then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Step("Авторизация с применением неверных данных пользователя")
    public static Response authorizationWithIncorrectDataUser() {
        AuthorizationModel authorizationModel = new AuthorizationModel()
                .setEmail(getRandomEmail())
                .setPassword(getRandomPassword());

        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(authorizationModel)
                .when()
                .post(PATH_LOGIN_USER)
                .then()
                .extract().response();
    }

    @Step("Проверка, что пользователь с неверными данными не авторизирован")
    public static void checkAuthorizationWithIncorrectDataUser(Response response) {
        response.then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
