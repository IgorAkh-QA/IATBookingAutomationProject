package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.models.Booking;
import core.models.CreatedBooking;
import core.models.NewBooking;
import io.restassured.response.Response;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static core.utils.SetupNewBookingFields.setupNewBookingFields;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBookingTest extends BaseBookingTest{

    private ObjectMapper objectMapper;
    private NewBooking newBooking;
    private int createdBookingId;

    @Test
    public void testGetAllBookingsResponseBody () throws JsonProcessingException {
        //Предусловия
        objectMapper = new ObjectMapper();
        newBooking = setupNewBookingFields(); // Создал экземпляр модели NewBooking и присвоил его поля значения с помощью метода setup

        String requestBody = objectMapper.writeValueAsString(newBooking); // Создал переменную строку requestBody, в которую присвается сериализованный объект newBooking с присвоенными значениями методом setup

        Response createdBookingResponse = apiClient.createBooking(requestBody); // Создаю экземпляр класса Response, который присваивает результат вызова метода createBooking с параметрами на вход строки requestBody. Результат вызыва метода POST booking
        String responseBody = createdBookingResponse.asString(); //Приводим объект response к строке, для последующей десериализации
        CreatedBooking createdBooking = objectMapper.readValue(responseBody, CreatedBooking.class); //Десериализуем респонс
        createdBookingId = createdBooking.getBookingid(); //Создаем переменную, для хранения созданного bookingId для последующего удаления
        System.out.println(createdBookingId); //Убедился, что получилось сохранить переменную

        //Тестовый сценарий
        Response bookingListResponse = apiClient.getBooking(); // Вызов метода GET /booking
        assertThat(bookingListResponse.getStatusCode()).isEqualTo(200); //Проверка, что статус код 200

        String responseBodyWithBookingList = bookingListResponse.getBody().asString(); //Упаковка респонса в стрингу
        List<Booking> bookings = objectMapper.readValue(responseBodyWithBookingList, new TypeReference<>() {
        }); //Преобразовываем стрингу в список айдишников

        assertThat(bookings).isNotEmpty(); //Проверка, что список айдишников не пуст
        //Проверка, что созданное нами бронирование - возвращается в списке бронирований
        assertThat(bookings).extracting(Booking::getBookingId).contains(createdBookingId); // extracting(Booking::getBookingId)- Для каждого объекта Booking в коллекции bookings, вызывает метод getBookingId(), геттер, который возвращает int bookingid. Напрямую проверить, входит ли createdBookingId в коллекцию bookings assertThat(bookings).contains(createdBookingId); не получилось из-за не соответствия типов.
        //Чтобы проверить, что этот ассерт работает как надо, для начала я проверил, что ассерт assertThat(bookings).extracting(Booking::getBookingId).doesNotContain(createdBookingId) - упадет. Упал с ошибкой:
        /* not to contain
        [4346]
        but found
        [4346] */
    }
//Постусловие
@AfterEach
public void tearDown() {
    apiClient.createToken("admin", "password123");
    apiClient.deleteBookingById(createdBookingId); //Вызываем метод delete для созданного в рамках теста bookingId

    AssertionsForClassTypes.assertThat(apiClient.getBookingById(createdBookingId).getStatusCode()).isEqualTo(404); //Проверяем, что bookingId действительно удален
}
}

