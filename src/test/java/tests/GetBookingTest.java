package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import core.models.Booking;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBookingTest extends BaseBookingTest {

    @Test
    @DisplayName("Поиск созданного бронирования в списке всех бронирований")
    public void testGetAllBookingsResponseBody () throws JsonProcessingException {

        Response bookingListResponse = apiClient.getBooking();

        step("Ответ от метода GET /booking с кодом: " + bookingListResponse.getStatusCode(), () ->
        assertThat(bookingListResponse.getStatusCode()).isEqualTo(200));

        String responseBodyWithBookingList = bookingListResponse.getBody().asString();
        List<Booking> bookings = objectMapper.readValue(responseBodyWithBookingList, new TypeReference<>() {
        });

        step("Список бронирований в ответе не пустой" , () ->
        assertThat(bookings).isNotEmpty());

        step("В списке бронирований присутствует созданное бронирование с booking == " + createdBookingId , () ->
        assertThat(bookings).extracting(Booking::getBookingId).contains(createdBookingId));
    }
}

