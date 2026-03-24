package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import core.models.Booking;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBookingTest extends BaseBookingTest {

    @Test
    public void testGetAllBookingsResponseBody () throws JsonProcessingException {

        Response bookingListResponse = apiClient.getBooking();
        assertThat(bookingListResponse.getStatusCode()).isEqualTo(200);

        String responseBodyWithBookingList = bookingListResponse.getBody().asString();
        List<Booking> bookings = objectMapper.readValue(responseBodyWithBookingList, new TypeReference<>() {
        });

        assertThat(bookings).isNotEmpty();
        assertThat(bookings).extracting(Booking::getBookingId).contains(createdBookingId);
    }
}

