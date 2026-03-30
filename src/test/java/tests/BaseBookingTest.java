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
import org.junit.jupiter.api.DisplayName;

import static io.qameta.allure.Allure.step;

import static core.utils.TestData.setupNewBookingFields;
import static org.assertj.core.api.Assertions.assertThat;

public class BaseBookingTest extends BaseTest{
    protected int createdBookingId;
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected NewBooking newBooking;

    @BeforeEach
    @DisplayName("Предусловие: создание бронирования")
    public void createNewBookingPrecondition() throws JsonProcessingException {
        newBooking = setupNewBookingFields();
        String requestBody = objectMapper.writeValueAsString(newBooking);
        Response createdBookingResponse = apiClient.createBooking(requestBody);

        step("Выполнено предусловие, создано бронирование для последующих проверок", () ->
        assertThat(createdBookingResponse.getStatusCode()).isEqualTo(200));

        String responseBody = createdBookingResponse.asString(); //Приводим объект response к строке, для последующей десериализации
        CreatedBooking createdBooking = objectMapper.readValue(responseBody, CreatedBooking.class); //Десериализуем респонс
        createdBookingId = createdBooking.getBookingid();

    }

    @AfterEach
    @DisplayName("Постусловие: удаление бронирования")
    public void tearDown() {
        apiClient.createToken("admin", "password123");
        apiClient.deleteBookingById(createdBookingId); //Вызываем метод delete для созданного в рамках теста bookingId
        step("Выполнено постусловие, бронирование удалено, после проведения проверки", () ->
        AssertionsForClassTypes.assertThat(apiClient.getBookingById(createdBookingId).getStatusCode()).isEqualTo(404));

    }


}
