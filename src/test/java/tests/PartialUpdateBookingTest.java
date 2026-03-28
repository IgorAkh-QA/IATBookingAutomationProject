package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.models.NewBooking;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static core.utils.TestData.setupNewBookingFields;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.qameta.allure.Allure.step;

public class PartialUpdateBookingTest extends BaseBookingTest{

    private ObjectMapper objectMapper = new ObjectMapper();
    private NewBooking partialUpdatedBooking;

    @Test
    @DisplayName("Частичное редактирование данных бронирования")
    public void testPartialUpdateBooking() throws JsonProcessingException {

        NewBooking partialUpdateBooking = setupNewBookingFields();
        String requestBody = objectMapper
                .writerWithView(NewBooking.partialFields.class)
                .writeValueAsString(partialUpdateBooking);

        Response response = apiClient.partialUpdateBooking(createdBookingId, requestBody);

        step("Ответ от метода PATCH /booking/{id} с кодом: " + response.getStatusCode(), () ->
        assertThat(response.getStatusCode()).isEqualTo(200));

        String responseBody = response.asString();
        partialUpdatedBooking = objectMapper.readValue(responseBody, NewBooking.class);

        step("Боди ответа от метода PATCH /booking/{id} не пустое", () ->
        assertThat(partialUpdatedBooking).isNotNull());

        step("Поле 'firstname' соответствует ожидаемому: " + partialUpdateBooking.getFirstname() + " == " + partialUpdatedBooking.getFirstname(), () ->
        assertEquals(partialUpdateBooking.getFirstname(), partialUpdatedBooking.getFirstname()));

        step("Поле 'lastname' соответствует ожидаемому: " + partialUpdateBooking.getLastname() + " == " + partialUpdatedBooking.getLastname(), () ->
        assertEquals(partialUpdateBooking.getLastname(), partialUpdatedBooking.getLastname()));

        step("Поле 'totalprice' соответствует ожидаемому: " + newBooking.getTotalprice() + " == " + partialUpdatedBooking.getTotalprice(), () ->
        assertEquals(newBooking.getTotalprice(), partialUpdatedBooking.getTotalprice()));

        step("Поле 'isdepositpaid' соответствует ожидаемому: " + newBooking.isDepositpaid() + " == " + partialUpdatedBooking.isDepositpaid(), () ->
        assertEquals(newBooking.isDepositpaid(), partialUpdatedBooking.isDepositpaid()));

        step("Поле 'checkin' соответствует ожидаемому: " + newBooking.getBookingdates().getCheckin() + " == " + partialUpdatedBooking.getBookingdates().getCheckin(), () ->
        assertEquals(newBooking.getBookingdates().getCheckin(), partialUpdatedBooking.getBookingdates().getCheckin()));

        step("Поле 'checkout' соответствует ожидаемому: " + newBooking.getBookingdates().getCheckout() + " == " + partialUpdatedBooking.getBookingdates().getCheckout(), () ->
        assertEquals(newBooking.getBookingdates().getCheckout(), partialUpdatedBooking.getBookingdates().getCheckout()));

        step("Поле 'additionalneeds' соответствует ожидаемому: " + newBooking.getAdditionalneeds() + " == " + partialUpdatedBooking.getAdditionalneeds(), () ->
        assertEquals(newBooking.getAdditionalneeds(), partialUpdatedBooking.getAdditionalneeds()));

    }

}
