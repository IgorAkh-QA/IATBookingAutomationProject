package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.models.NewBooking;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static core.utils.TestData.setupNewBookingFields;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateBookingTest extends BaseBookingTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    private NewBooking updatedBooking;

    @Test
    @DisplayName("Полное редактирование данных бронирования")
    public void testUpdateBooking() throws JsonProcessingException {

        NewBooking updateBooking = setupNewBookingFields();
        String requestBody = objectMapper.writeValueAsString(updateBooking);

        Response response = apiClient.updateBooking(createdBookingId, requestBody);

        step("Ответ от метода PUT /booking/{id} с кодом: " + response.getStatusCode(), () ->
        assertThat(response.getStatusCode()).isEqualTo(200));

        String responseBody = response.asString();
        updatedBooking = objectMapper.readValue(responseBody, NewBooking.class);

        step("Боди ответа от метода PUT /booking/{id} не пустое" + response.getStatusCode(), () ->
        assertThat(updatedBooking).isNotNull());

        step("Поле 'firstname' соответствует ожидаемому: " + updateBooking.getFirstname() + " == " + updatedBooking.getFirstname(), () ->
        assertEquals(updateBooking.getFirstname(), updatedBooking.getFirstname()));

        step("Поле 'lastname' соответствует ожидаемому: " + updateBooking.getLastname() + " == " + updatedBooking.getLastname(), () ->
                assertEquals(updateBooking.getLastname(), updatedBooking.getLastname()));

        step("Поле 'totalprice' соответствует ожидаемому: " + updateBooking.getTotalprice() + " == " + updatedBooking.getTotalprice(), () ->
        assertEquals(updateBooking.getTotalprice(), updatedBooking.getTotalprice()));

        step("Поле 'isedepositpaid' соответствует ожидаемому: " + updateBooking.isDepositpaid() + " == " + updatedBooking.isDepositpaid(), () ->
        assertEquals(updateBooking.isDepositpaid(), updatedBooking.isDepositpaid()));

        step("Поле 'checkin' соответствует ожидаемому: " + updateBooking.getBookingdates().getCheckin() + " == " + updatedBooking.getBookingdates().getCheckin(), () ->
        assertEquals(updateBooking.getBookingdates().getCheckin(), updatedBooking.getBookingdates().getCheckin()));

        step("Поле 'checkout' соответствует ожидаемому: " + updateBooking.getBookingdates().getCheckout() + " == " + updatedBooking.getBookingdates().getCheckout(), () ->
        assertEquals(updateBooking.getBookingdates().getCheckout(), updatedBooking.getBookingdates().getCheckout()));

        step("Поле 'additionalneeds' соответствует ожидаемому: " + updateBooking.getAdditionalneeds() + " == " + updatedBooking.getAdditionalneeds(), () ->
        assertEquals(updateBooking.getAdditionalneeds(), updatedBooking.getAdditionalneeds()));
    }

}
