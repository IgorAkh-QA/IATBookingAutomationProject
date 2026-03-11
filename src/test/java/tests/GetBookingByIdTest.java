package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.clients.APIClient;
import core.models.BookingById;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBookingByIdTest {
    private APIClient apiClient;

    @BeforeEach
    public void setup() {
        apiClient = new APIClient();
    }

    @Test
    public void testGetBookingById() throws Exception {

        Response response = apiClient.getBookingById();

        //Проверяем, что статус- код ответа равен 200
        assertThat(response.getStatusCode()).isEqualTo(200);

        //Десериализуем тело ответа в список объектов Booking
        String getBookingByIdResponseBody = response.getBody().asString();
        BookingById bookingInfo = response.as(BookingById.class);
        assertThat(bookingInfo.getFirstname()).isNotNull();
        assertThat(bookingInfo.getLastname()).isNotNull();
        assertThat(bookingInfo.getTotalprice()).isGreaterThan(0);
        assertThat(bookingInfo.isDepositpaid()).asString().isNotNull();
        assertThat(bookingInfo.bookingdates.getCheckin()).isBetween("2013-02-23", "2023-02-23");
        assertThat(bookingInfo.bookingdates.getCheckout()).isGreaterThan(bookingInfo.bookingdates.getCheckin());
        assertThat(bookingInfo.getAdditionalneeds()).isNotNull();






        }
    }

