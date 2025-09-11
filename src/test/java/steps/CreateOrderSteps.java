package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.OrderModel;

import static data.TestData.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateOrderSteps {

    public static final String PATH_CREATE_ORDER = "/api/orders";

    @Step("Создать заказ с авторизацией")
    public static Response createOrderWithAuthorization(String accessToken) {
        OrderModel orderModel = new OrderModel()
                .addIngredients(hashBun)
                .addIngredients(hashFilling);

        return given()
                .log().all()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .body(orderModel)
                .when()
                .post(PATH_CREATE_ORDER)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Проверка, что заказ авторизированного пользователя прошел успешно")
    public static void checkCreateOrderWithAuthorization(Response response) {
        response.then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Step("Создать заказ без авторизации")
    public static Response createOrderWithoutAuthorization() {
        OrderModel orderModel = new OrderModel()
                .addIngredients(hashBun)
                .addIngredients(hashFilling);

        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(orderModel)
                .when()
                .post(PATH_CREATE_ORDER)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Проверка, что заказ неавторизированного пользователя прошел успешно")
    public static void checkCreateOrderWithoutAuthorization(Response response) {
        response.then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Step("Создать заказ с авторизацией, без добавления ингредиентов")
    public static Response createOrderWithAuthorizationWithoutAddIngredients(String accessToken) {

        return given()
                .log().all()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .when()
                .post(PATH_CREATE_ORDER)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Проверка ответа сервера если не передать ни один из ингредиентов")
    public static void checkCreateOrderWithAuthorizationWithoutAddIngredients(Response response) {
        response.then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Создание заказа с авторизацией и передачей несуществующего хеша ингредиента")
    public static Response createOrderWithAuthorizationAndFalseHash(String accessToken) {
        OrderModel orderModel = new OrderModel()
                .addIngredients(falseHashBun);

        return given()
                .log().all()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .body(orderModel)
                .when()
                .post(PATH_CREATE_ORDER)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Проверка ответа после создания заказа с указанием неверного хеша ингредиента")
    public static void checkCreateOrderWithAuthorizationAndFalseHash(Response response) {
        response.then()
                .statusCode(500);
    }
}
