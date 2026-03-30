package core.clients;

import core.settings.ApiEndpoints;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class APIClient {

    private final String baseUrl;
    private String token;

    public APIClient() {
        this.baseUrl = determineBaseUrl();
    }

    private String determineBaseUrl() {
        String environment = System.getProperty("env", "test");
        String configFileName = "application-" + environment + ".properties";

        Properties properties = new Properties();
        try (InputStream input =
                     getClass().getClassLoader().getResourceAsStream(configFileName)) {
            if (input == null) {
                throw new IllegalStateException("Configuration file not found: "
                        + configFileName);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load configuration file: " + configFileName, e);
        }

        return properties.getProperty("baseUrl");
    }

    //Настройка базовых параметров HTTP-запросов
    private RequestSpecification getRequestSpec(){
        return given()
                .baseUri(baseUrl)
                .header("Content-Type","application/json")
                .header("Accept", "application/json")
                .filter(addAuthTokenFilter());
    }

    //Метод получения токена

    public void createToken (String username, String password){
        // Тело запроса для получения токена
        String requestBody = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

        Response response = getRequestSpec()
                .body(requestBody)
                .when()
                .post(ApiEndpoints.AUTH.getPath())
                .then()
                .statusCode(200)
                .extract().response();

        // Извлекаем токен из ответа и кладем в переменную token
        token = response.jsonPath().getString("token");
    }

    // Фильтр для добавления токена в заголовок Authorization
    private Filter addAuthTokenFilter(){
        return (FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) -> {
            if (token != null) {
                requestSpec.header("Cookie", "token=" + token);
            }
            return ctx.next(requestSpec, responseSpec);
        };
    }

    // GET запрос на ендпоинт /ping
    public Response ping(){
        return getRequestSpec()
                .when()
                .get(ApiEndpoints.PING.getPath())
                .then()
                .statusCode(201)
                .extract().response();
    }

    // GET запрос на ендпоинт /booking
    public Response getBooking(){
        return getRequestSpec()
                .when()
                .get(ApiEndpoints.BOOKING.getPath()) // Enum для едпоинта /ping
                .then()
                .log().all()
                .statusCode(200) // Ожидаемый статус-код 200
                .extract().response();
    }

    //Обработка ответа от GET /booking, создание списка bookingid

    public List<Integer> bookingIdList(){
        List bookingIds = getBooking().jsonPath().getList("bookingid", Integer.class);
        return bookingIds != null ? bookingIds : Collections.emptyList();
    }

    // GET запрос на ендпоинт /booking/{id}
    public Response getBookingById(int bookingId){

        return getRequestSpec()
                .given()
                .when()
                .get(ApiEndpoints.BOOKING.getPath() + "/" + bookingId)
                .then()
                .extract().response();
    }

    public Response getBookingWithFilters(String firstName, String lastName, String checkOutDate) {
        RequestSpecification spec = getRequestSpec();
        if (firstName != null) spec.queryParam("firstname", firstName);
        if (lastName != null) spec.queryParam("lastname", lastName);
        if (checkOutDate != null) spec.queryParam("checkout", checkOutDate);
        return spec.log().params()
                .when()
                .get(ApiEndpoints.BOOKING.getPath())
                .then()
                .statusCode(200)
                .extract().response();
    }

    // DELETE запрос на эндпоинт /booking
    public Response deleteBookingById(int bookingId) {
        return getRequestSpec()
                .pathParam("id", bookingId)
                .when()
                .delete(ApiEndpoints.BOOKING.getPath()+ "/{id}")
                .then()
                .statusCode(201)
                .extract().response();
    }

    public Response createBooking (String newBooking){
        return getRequestSpec()
                .body(newBooking)
                .log().all()
                .when()
                .post(ApiEndpoints.BOOKING.getPath())
                .then()
                .log().all()
                .extract().response();
    }

    public Response updateBooking(int bookingId, String newBooking) {
        return getRequestSpec()
                .pathParam("id", bookingId)
                .body(newBooking)
                .when()
                .put(ApiEndpoints.BOOKING.getPath()+ "/{id}")
                .then()
                .log().all()
                .extract().response();

    }

    public Response partialUpdateBooking(int bookingId, String partialUpdateBooking){
        return getRequestSpec()
                .pathParam("id", bookingId)
                .body(partialUpdateBooking)
                .when()
                .patch(ApiEndpoints.BOOKING.getPath()+ "/{id}")
                .then()
                .log().all()
                .extract().response();
    }

}
