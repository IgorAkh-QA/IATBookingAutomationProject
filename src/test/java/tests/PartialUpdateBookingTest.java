package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.models.NewBooking;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static core.utils.TestData.setupNewBookingFields;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartialUpdateBookingTest extends BaseBookingTest{

    private ObjectMapper objectMapper = new ObjectMapper();
    private NewBooking partialUpdatedBooking;

    @Test
    public void testPartialUpdateBooking() throws JsonProcessingException {

        NewBooking partialUpdateBooking = setupNewBookingFields();
        String requestBody = objectMapper
                .writerWithView(NewBooking.partialFields.class)
                .writeValueAsString(partialUpdateBooking);
        System.out.println(requestBody);
        Response response = apiClient.partialUpdateBooking(createdBookingId, requestBody);
        assertThat(response.getStatusCode()).isEqualTo(200);
        String responseBody = response.asString();
        partialUpdatedBooking = objectMapper.readValue(responseBody, NewBooking.class);

        assertThat(partialUpdatedBooking).isNotNull();
        assertEquals(partialUpdateBooking.getFirstname(), partialUpdatedBooking.getFirstname());
        assertEquals(partialUpdateBooking.getLastname(), partialUpdatedBooking.getLastname());

        assertEquals(newBooking.getTotalprice(), partialUpdatedBooking.getTotalprice());
        assertEquals(newBooking.isDepositpaid(), partialUpdatedBooking.isDepositpaid());
        assertEquals(newBooking.getBookingdates().getCheckin(), partialUpdatedBooking.getBookingdates().getCheckin());
        assertEquals(newBooking.getBookingdates().getCheckout(), partialUpdatedBooking.getBookingdates().getCheckout());
        assertEquals(newBooking.getAdditionalneeds(), partialUpdatedBooking.getAdditionalneeds());

    }

}

