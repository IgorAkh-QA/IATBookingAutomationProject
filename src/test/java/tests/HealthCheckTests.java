package tests;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HealthCheckTests extends BaseTest {

    //Тест на метод ping()
    @Test
    public void testPing() {
        // Выполняем GET запрос на /ping через APIClient
        Response response = apiClient.ping();
        assertThat(response.getStatusCode()).isEqualTo(201);
    }
}
