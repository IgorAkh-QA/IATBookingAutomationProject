package tests;

import core.clients.APIClient;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public class BaseBookingTest {
    protected APIClient apiClient;

    @BeforeEach
    public void setup() {
        apiClient = new APIClient();
        apiClient.createToken("admin", "password123");
    }
}