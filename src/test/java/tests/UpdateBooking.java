package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.models.CreatedBooking;
import core.models.NewBooking;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static core.utils.TestData.setupNewBookingFields;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateBooking extends BaseBookingTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    private NewBooking updatedBooking;

    @Test
    public void testUpdateBooking() throws JsonProcessingException {

        NewBooking updateBooking = setupNewBookingFields();
        String requestBody = objectMapper.writeValueAsString(updateBooking);

        Response response = apiClient.updateBooking(createdBookingId, requestBody);
        assertThat(response.getStatusCode()).isEqualTo(200);
        String responseBody = response.asString();
        updatedBooking = objectMapper.readValue(responseBody, NewBooking.class);

        assertThat(updatedBooking).isNotNull();
        assertEquals(updateBooking.getFirstname(), updatedBooking.getFirstname());
        assertEquals(updateBooking.getLastname(), updatedBooking.getLastname());
        assertEquals(updateBooking.getTotalprice(), updatedBooking.getTotalprice());
        assertEquals(updateBooking.isDepositpaid(), updatedBooking.isDepositpaid());
        assertEquals(updateBooking.getBookingdates().getCheckin(), updatedBooking.getBookingdates().getCheckin());
        assertEquals(updateBooking.getBookingdates().getCheckout(), updatedBooking.getBookingdates().getCheckout());
        assertEquals(updateBooking.getAdditionalneeds(), updatedBooking.getAdditionalneeds());

    }

}
