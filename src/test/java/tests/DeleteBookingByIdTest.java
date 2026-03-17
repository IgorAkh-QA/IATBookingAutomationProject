package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.shouldHaveThrown;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteBookingByIdTest extends BaseBookingTest {

    @Test
    public void testBookingIsNotFoundAfterDeletion() {
        List<Integer> bookingIdList = apiClient.bookingIdList();
        Integer bookingId = bookingIdList.get(0);

        Response deleteBookingById = apiClient.deleteBookingById(bookingId);
        assertEquals(201, deleteBookingById.getStatusCode(), "Удаление не было успешным, код ответа: " + deleteBookingById.getStatusCode());
        System.out.println("Успешно удалено бронирование с id==" + bookingId);

        Response getRequestWithDeletedBookingId = apiClient.getBookingById(bookingId);
        assertEquals(404, getRequestWithDeletedBookingId.getStatusCode(),"Запрос метода GET /booking/" + bookingId + " вернул некорректный статус код: " + getRequestWithDeletedBookingId.getStatusCode());
        System.out.println("Удаленное бронирование с id==" + bookingId + " не было найдено в списке бронирований");
        }
    }



