package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.models.CreatedBooking;
import core.models.NewBooking;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static core.utils.SetupNewBookingFields.setupNewBookingFields;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

public class BaseBookingTest extends BaseTest{
    protected int createdBookingId;
    protected ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void createNewBookingPrecondition() throws JsonProcessingException {
        NewBooking newBooking = setupNewBookingFields();
        String requestBody = objectMapper.writeValueAsString(newBooking);
        Response createdBookingResponse = apiClient.createBooking(requestBody);
       Assertions.assertThat(createdBookingResponse.getStatusCode()).isEqualTo(200);

        String responseBody = createdBookingResponse.asString(); //Приводим объект response к строке, для последующей десериализации
        CreatedBooking createdBooking = objectMapper.readValue(responseBody, CreatedBooking.class); //Десериализуем респонс
        createdBookingId = createdBooking.getBookingid();
        System.out.println("Предусловие выполнено успешно, бронирование: " + createdBookingId + " создано");

    }

    @AfterEach
    public void tearDown() {
        apiClient.createToken("admin", "password123");
        apiClient.deleteBookingById(createdBookingId); //Вызываем метод delete для созданного в рамках теста bookingId

        AssertionsForClassTypes.assertThat(apiClient.getBookingById(createdBookingId).getStatusCode()).isEqualTo(404);
        System.out.println("Постусловие выполнено успешно, бронирование: " + createdBookingId + " удалено");//Проверяем, что bookingId действительно удален
    }


}
