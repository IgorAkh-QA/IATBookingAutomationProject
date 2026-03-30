package core.models;

import com.fasterxml.jackson.annotation.JsonView;

public class NewBooking {

    public interface partialFields {}
    public interface allFields extends partialFields{}

    @JsonView(partialFields.class)
    public String firstname;

    @JsonView(partialFields.class)
    public String lastname;

    @JsonView(allFields.class)
    public int totalprice;

    @JsonView(allFields.class)
    public boolean depositpaid;

    @JsonView(allFields.class)
    public BookingDates bookingdates;

    @JsonView(allFields.class)
    public String additionalneeds;

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public boolean isDepositpaid() {
        return depositpaid;
    }

    public void setDepositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    public BookingDates getBookingdates() {
        return bookingdates;
    }

    public void setBookingdates(BookingDates bookingDates) {
        this.bookingdates = bookingDates;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public void setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }


}
