package tests;

import core.clients.APIClient;
import org.junit.jupiter.api.BeforeEach;

public class BaseBookingTest {
    protected APIClient apiClient;

    @BeforeEach
    public void setup() {
        apiClient = new APIClient();
        apiClient.createToken("admin", "password123");
    }
}
