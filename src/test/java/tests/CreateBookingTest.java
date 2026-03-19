package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.models.CreatedBooking;
import core.models.NewBooking;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static core.utils.SetupNewBookingFields.setupNewBookingFields;
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
        assertThat(response.getStatusCode()).isEqualTo(200);
        String responseBody = response.asString();
        createdBooking = objectMapper.readValue(responseBody, CreatedBooking.class);

        assertThat(createdBooking).isNotNull();
        assertEquals(setupNewBookingFields().getFirstname(), createdBooking.getBooking().getFirstname());
        assertEquals(setupNewBookingFields().getLastname(), createdBooking.getBooking().getLastname());
        assertEquals(setupNewBookingFields().getTotalprice(), createdBooking.getBooking().getTotalprice());
        assertEquals(setupNewBookingFields().isDepositpaid(), createdBooking.getBooking().isDepositpaid());
        assertEquals(setupNewBookingFields().getBookingdates().getCheckin(), createdBooking.getBooking().getBookingdates().getCheckin());
        assertEquals(setupNewBookingFields().getBookingdates().getCheckout(), createdBooking.getBooking().getBookingdates().getCheckout());
        assertEquals(setupNewBookingFields().getAdditionalneeds(), createdBooking.getBooking().getAdditionalneeds());
    }

    @AfterEach
    public void tearDown(){
        apiClient.createToken("admin", "password123");
        apiClient.deleteBookingById(createdBooking.getBookingid());

        assertThat(apiClient.getBookingById(createdBooking.getBookingid()).getStatusCode()).isEqualTo(404);
    }




}
