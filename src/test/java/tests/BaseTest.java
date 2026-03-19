package tests;

import core.clients.APIClient;
import core.models.CreatedBooking;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BaseTest {
    protected APIClient apiClient;
    CreatedBooking createdBooking;

    @BeforeEach
    public void setup() {
        apiClient = new APIClient();
        apiClient.createToken("admin", "password123");
    }


    /*
    @AfterEach
    public void tearDown(){
        apiClient.createToken("admin", "password123");

        apiClient.deleteBookingById(createdBooking.getBookingid());

        assertThat(apiClient.getBookingById(createdBooking.getBookingid()).getStatusCode()).isEqualTo(404);
    }*/
}