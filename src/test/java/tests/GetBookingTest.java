package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.clients.APIClient;
import core.models.Booking;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBookingTest extends BaseBookingTest{

    @Test
    public void testGetAllBookings() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // Выполняем GET запрос на /ping через APIClient
        Response response = apiClient.getBooking();

        //Проверяем, что статус- код ответа равен 200
        assertThat(response.getStatusCode()).isEqualTo(200);

        //Десериализуем тело ответа в список объектов Booking
        String responseBody = response.getBody().asString();
        List<Booking> bookings = objectMapper.readValue(responseBody, new TypeReference<List<Booking>>() {
        });

        //Проверяем, что тело ответа содержит объекты Booking
        assertThat(bookings).isNotEmpty();

        //Проверяем, что каждый объект Booking содержит валидное значение bookingid
        for (Booking booking : bookings) {
            assertThat(booking.getBookingId()).isGreaterThan(0);// bookingid должен быть > 0
            System.out.println(booking.getBookingId());
        }
    }
}
