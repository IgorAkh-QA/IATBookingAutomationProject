package core.utils;
import com.github.javafaker.DateAndTime;
import com.github.javafaker.Faker;
import core.models.BookingDates;
import core.models.NewBooking;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import static core.utils.RandomUtils.*;

public class TestData {

    private static final Faker faker = new Faker();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static NewBooking setupNewBookingFields() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        int totalPrice = randomInt(1, 9999999);
        boolean isDepositPaid = randomBoolean();

        Date dateForCheckIn = faker.date().past(365, TimeUnit.DAYS);
        Date dateForCheckOut = faker.date().future(14, TimeUnit.DAYS, dateForCheckIn);
        String checkInDate = sdf.format(dateForCheckIn);
        String checkOutDate = sdf.format(dateForCheckOut);

        NewBooking newBooking = new NewBooking();
        newBooking.setFirstname(firstName);
        newBooking.setLastname(lastName);
        newBooking.setTotalprice(totalPrice);
        newBooking.setDepositpaid(isDepositPaid);
        newBooking.setBookingdates(new BookingDates(checkInDate, checkOutDate));
        newBooking.setAdditionalneeds(randomString(randomInt(3, 99)));
        return newBooking;
    }
}
