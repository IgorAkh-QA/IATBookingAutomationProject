package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteBookingByIdTest extends BaseTest {

    @Test
    @DisplayName("Удаленное бронирование не получается найти по его id")
    public void testBookingIsNotFoundAfterDeletion() {
        List<Integer> bookingIdList = apiClient.bookingIdList();
        Integer bookingId = bookingIdList.get(0);

        Response deleteBookingById = apiClient.deleteBookingById(bookingId);

        step("Бронирование удалено, ответ от метода с кодом " + deleteBookingById.getStatusCode() , () ->
        assertEquals(201, deleteBookingById.getStatusCode(), "Удаление не было успешным, код ответа: " + deleteBookingById.getStatusCode()));

        Response getRequestWithDeletedBookingId = apiClient.getBookingById(bookingId);

        step("При поиске удаленного бронирование, бронирование не найдено, ответ от метода GET /booking/{id} с кодом " + deleteBookingById.getStatusCode() , () ->
        assertEquals(404, getRequestWithDeletedBookingId.getStatusCode(),"Запрос метода GET /booking/" + bookingId + " вернул некорректный статус код: " + getRequestWithDeletedBookingId.getStatusCode()));

        }
    }



