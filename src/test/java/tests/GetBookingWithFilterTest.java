package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.models.NewBooking;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.filter;

public class GetBookingWithFilterTest extends BaseBookingTest {

    static Stream<Arguments> filterParam(){
        return Stream.of(
                Arguments.of("firstname", (Function<NewBooking, String>) NewBooking::getFirstname),
                Arguments.of("lastname", (Function<NewBooking, String>) NewBooking::getLastname),
                Arguments.of("checkout", (Function<NewBooking, String>) b -> b.getBookingdates().getCheckout())
        );
    }
    
    @ParameterizedTest(name= "Поиск бронирования с фильтром: {0}")
    @MethodSource("filterParam")
    public void testGetBookingWithFilter(String paramName, Function <NewBooking, String> valueExtractor) throws IOException {
        
        
        String filterValue = valueExtractor.apply(newBooking);


        Response response;
        if (paramName.equals("firstname")){
            response = apiClient.getBookingWithFilters(filterValue, null, null);
        }
        else if (paramName.equals("lastname")) {
            response = apiClient.getBookingWithFilters(null, filterValue, null);
        }
        else if (paramName.equals("checkout")) {
            response = apiClient.getBookingWithFilters(null, null, filterValue);
        }
        else {
            throw new IllegalArgumentException();
        }

        assertThat(response.getStatusCode()).isEqualTo(200);

        List<Integer> filteredBookingIds = response.jsonPath().getList("bookingid", Integer.class);
        filteredBookingIds = (filteredBookingIds != null) ? filteredBookingIds : Collections.emptyList();
        System.out.println(filteredBookingIds + "  " + createdBookingId);
        assertThat(filteredBookingIds).contains(createdBookingId);


        System.out.println(filteredBookingIds);

    }
}