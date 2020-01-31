package domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;

public class OrderTest {

    private LocalDateTime wednesday;
    private LocalDateTime friday;
    private LocalDateTime saturday;

    private Movie circle;
    private Movie square;
    private Movie triangle;

    private MovieScreening circleScreening1;
    private MovieScreening circleScreening2;
    private MovieScreening squareScreening1;
    private MovieScreening squareScreening2;
    private MovieScreening triangleScreening1;
    private MovieScreening triangleScreening2;

    private MovieTicket circleTicket;
    private MovieTicket circleTicketPremium;
    private MovieTicket squareTicket;
    private MovieTicket squareTicketPremium;
    private MovieTicket triangleTicket;
    private MovieTicket triangleTicketPremium;

    private Order circleOrder;
    private Order circleOrderStudent;
    private Order squareOrder;
    private Order squareOrderStudent;
    private Order triangleOrder;
    private Order triangleOrderStudent;


    @BeforeAll
    void initAll() {

        // Create a usable LocalDateTime object
        wednesday = LocalDateTime.of(2020, 1, 29, 17, 0);
        friday = LocalDateTime.of(2020, 1, 31, 17, 0);
        saturday = LocalDateTime.of(2020, 2, 1, 17, 0);

        // Test Movies
        circle = new Movie("The Circle");
        square = new Movie("The Square");
        triangle = new Movie("The Triangle");

        // Test Screenings
        circleScreening1 = new MovieScreening(circle, wednesday, 20.0);
        circleScreening2 = new MovieScreening(circle, wednesday.plusHours(2), 20.0);

         squareScreening1 = new MovieScreening(square, friday, 20.0);
         squareScreening2 = new MovieScreening(square, friday.plusHours(2), 20.0);

        triangleScreening1 = new MovieScreening(triangle, saturday, 20.0);
         triangleScreening2 = new MovieScreening(triangle, saturday.plusHours(2), 20.0);

        circle.addScreening(circleScreening1);
        circle.addScreening(circleScreening2);

        square.addScreening(squareScreening1);
        square.addScreening(squareScreening2);

        triangle.addScreening(triangleScreening1);
        triangle.addScreening(triangleScreening2);

        //Test Tickets
        circleTicket = new MovieTicket(circleScreening1, false, 1, 1);
        circleTicketPremium = new MovieTicket(circleScreening1, true, 1, 2);

        squareTicket = new MovieTicket(squareScreening1, false, 1, 1);
        squareTicketPremium = new MovieTicket(squareScreening1, true, 1, 2);

        triangleTicket = new MovieTicket(triangleScreening1, false, 1, 1);
        triangleTicketPremium = new MovieTicket(triangleScreening1, true, 1, 2);

        //Test Orders
        circleOrder = new Order(1, false);
        circleOrderStudent = new Order(2, true);

        squareOrder = new Order(3, false);
        squareOrderStudent = new Order(4, true);

        triangleOrder = new Order(5, false);
        triangleOrderStudent = new Order(6, true);
    }

    // TODO: Change movie prices

    @Test
    void NonPremiumNonStudentWeekendTickets() {
        triangleOrder.addSeatReservation(triangleTicket);

        Assertions.assertEquals(20.0, triangleOrder.calculatePrice());
    }

    @Test
    void NonPremiumNonStudentWeekdayTicketsWithTwoTickets() {
        circleOrder.addSeatReservation(circleTicket);
        circleOrder.addSeatReservation(circleTicket);

        Assertions.assertEquals(10.0, circleOrder.calculatePrice());
    }

    @Test
    void NonPremiumNonStudentWeekdayTicketsWithThreeTickets() {
        circleOrder.addSeatReservation(circleTicket);
        circleOrder.addSeatReservation(circleTicket);
        circleOrder.addSeatReservation(circleTicket);

        Assertions.assertEquals(20.0, circleOrder.calculatePrice());
    }

    @Test
    void NonPremiumStudentWeekendTickets() {
        Assertions.assertEquals(true, true);
    }

    @Test
    void NonPremiumStudentWeekday() {

    }

    @Test
    void PremiumNonStudentWeekend() {

    }

    @Test
    void PremiumNonStudentWeekday() {

    }

    @Test
    void PremiumStudentWeekend() {

    }

    @Test
    void PremiumStudentWeekday() {

    }
}
