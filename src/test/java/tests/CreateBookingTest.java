package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.models.CreatedBooking;
import core.models.NewBooking;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static core.utils.TestData.setupNewBookingFields;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateBookingTest extends BaseTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    private CreatedBooking createdBooking;

    @Test
    public void createBooking() throws JsonProcessingException{

        NewBooking newBooking = setupNewBookingFields();
        String requestBody = objectMapper.writeValueAsString(newBooking);

        Response response = apiClient.createBooking(requestBody);

        step("Создать новое бронирование", () ->
        assertThat(response.getStatusCode()).isEqualTo(200));
        String responseBody = response.asString();
        createdBooking = objectMapper.readValue(responseBody, CreatedBooking.class);

        step("Ответ не пустой", () ->
        assertThat(createdBooking).isNotNull());
        step("В ответе присутствует поле 'firstname' значение поля совпадает с переданным == " + createdBooking.getBooking().getFirstname() , () ->
        assertEquals(newBooking.getFirstname(), createdBooking.getBooking().getFirstname()));

        step("В ответе присутствует поле 'lastname' значение поля совпадает с переданным == " + createdBooking.getBooking().getLastname() , () ->
        assertEquals(newBooking.getLastname(), createdBooking.getBooking().getLastname()));

        step("В ответе присутствует поле 'totalprice' значение поля совпадает с переданным == " + createdBooking.getBooking().getTotalprice() , () ->
        assertEquals(newBooking.getTotalprice(), createdBooking.getBooking().getTotalprice()));

        step("В ответе присутствует поле 'isdepositpaid' значение поля совпадает с переданным == " + createdBooking.getBooking().isDepositpaid() , () ->
        assertEquals(newBooking.isDepositpaid(), createdBooking.getBooking().isDepositpaid()));

        step("В ответе присутствует поле 'checkin' значение поля совпадает с переданным == " + createdBooking.getBooking().getBookingdates().getCheckin() , () ->
        assertEquals(newBooking.getBookingdates().getCheckin(), createdBooking.getBooking().getBookingdates().getCheckin()));

        step("В ответе присутствует поле 'checkout' значение поля совпадает с переданным == " + createdBooking.getBooking().getBookingdates().getCheckout() , () ->
        assertEquals(newBooking.getBookingdates().getCheckout(), createdBooking.getBooking().getBookingdates().getCheckout()));

        step("В ответе присутствует поле 'additionalneeds' значение поля совпадает с переданным == " + createdBooking.getBooking().getAdditionalneeds() , () ->
        assertEquals(newBooking.getAdditionalneeds(), createdBooking.getBooking().getAdditionalneeds()));
    }



    @AfterEach
    public void tearDown(){
        apiClient.createToken("admin", "password123");
        apiClient.deleteBookingById(createdBooking.getBookingid());

        assertThat(apiClient.getBookingById(createdBooking.getBookingid()).getStatusCode()).isEqualTo(404);
    }




}
