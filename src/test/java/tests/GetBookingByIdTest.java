package tests;

import core.models.CreatedBooking;
import core.models.NewBooking;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBookingByIdTest extends BaseBookingTest {

    /* Этот тест больше не нужен, он был сделан с целью проверять конкретный bookingId без его предварительного создания. Но я его тут прикопаю закомментированным- пригодится.
    @Test
    public void testGetBookingFromBookingListById() throws Exception {
        boolean successfulResponseFound = false;
        List<Integer> bookingIdList = apiClient.bookingIdList();

        for (Integer bookingId : bookingIdList) {
            Response response = apiClient.getBookingById(bookingId);

            if (response.statusCode() == 200) {
                successfulResponseFound = true;

                NewBooking bookingInfo = response.as(NewBooking.class);
                assertThat(bookingInfo.getFirstname()).isNotNull();
                assertThat(bookingInfo.getLastname()).isNotNull();
                assertThat(bookingInfo.getTotalprice()).isGreaterThan(0);
                assertThat(bookingInfo.isDepositpaid()).asString().isNotNull();
                assertThat(bookingInfo.bookingdates.getCheckin()).isBetween("2013-02-23", "2023-02-23");
                assertThat(bookingInfo.bookingdates.getCheckout()).isGreaterThan(bookingInfo.bookingdates.getCheckin());
                assertThat(bookingInfo.getAdditionalneeds()).isNotNull();
                System.out.println("Успешный сценарий, статус-код ответа == " + response.statusCode());
                break;
            }
        }
    } */

    @Test
    public void testGetBookingAfterCreatingById(){

        Response response = apiClient.getBookingById(createdBookingId);
        assertThat(response.getStatusCode()).isEqualTo(200);

        NewBooking bookingInfo = response.as(NewBooking.class);
        assertThat(bookingInfo.getFirstname()).isNotNull();
        assertThat(bookingInfo.getLastname()).isNotNull();
        assertThat(bookingInfo.getTotalprice()).isGreaterThan(0);
        assertThat(bookingInfo.isDepositpaid()).asString().isNotNull();
        assertThat(bookingInfo.bookingdates.getCheckout()).isGreaterThan(bookingInfo.bookingdates.getCheckin());

    }
}