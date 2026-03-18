package core.utils;

import core.models.BookingDates;
import core.models.NewBooking;

public class SetupNewBookingFields {
    public static NewBooking setupNewBookingFields() {
        NewBooking newBooking = new NewBooking();
        newBooking.setFirstname("Igor");
        newBooking.setLastname("Qakhmet");
        newBooking.setTotalprice(222);
        newBooking.setDepositpaid(true);
        newBooking.setBookingdates(new BookingDates("2025-01-22", "2026-01-02"));
        newBooking.setAdditionalneeds("Breakfast");
        return newBooking;
    }
}
