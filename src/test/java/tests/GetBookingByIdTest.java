package tests;

import core.models.CreatedBooking;
import core.models.NewBooking;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBookingByIdTest extends BaseBookingTest {

    @Test
    @DisplayName("Получение данных о бронировании по id")
    public void testGetBookingAfterCreatingById(){

        Response response = apiClient.getBookingById(createdBookingId);

        step("Ответ от метода GET /booking/{id} с кодом == " + response.getStatusCode() , () ->
        assertThat(response.getStatusCode()).isEqualTo(200));

        NewBooking bookingInfo = response.as(NewBooking.class);

        step("В ответе присутствует поле 'firstname' не пустое" , () ->
        assertThat(bookingInfo.getFirstname()).isNotNull());

        step("В ответе присутствует поле 'lastname' не пустое"  , () ->
        assertThat(bookingInfo.getLastname()).isNotNull());

        step("В ответе присутствует поле 'totalprice' не пустое"  , () ->
        assertThat(bookingInfo.getTotalprice()).isGreaterThan(0));

        step("В ответе присутствует поле 'isdepositpaid' не пустое"  , () ->
        assertThat(bookingInfo.isDepositpaid()).asString().isNotNull());

        step("В ответе присутствует поля 'checkin' и checkout, checkout > checkin: " + bookingInfo.bookingdates.getCheckout() + " > " + bookingInfo.bookingdates.getCheckin() , () ->
        assertThat(bookingInfo.bookingdates.getCheckout()).isGreaterThan(bookingInfo.bookingdates.getCheckin()));

    }
}