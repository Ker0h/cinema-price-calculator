package domain;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

public class OrderTest {

    @BeforeAll
    initAll() {
        //Create a usable LocalDateTime object
        LocalDateTime wednesday = LocalDateTime.of(2020, 1, 29, 17, 0);
        LocalDateTime friday = LocalDateTime.of(2020, 1, 31, 17, 0);
        LocalDateTime saturday = LocalDateTime.of(2020, 2, 1, 17, 0);

        //Test Movies
        Movie circle = new Movie("The Circle");
        Movie square = new Movie("The Square");
        Movie triangle = new Movie("The Triangle");

        //Test Screenings
        MovieScreening circleScreening1 = new MovieScreening(circle, wednesday, 10.0);
        MovieScreening circleScreening2 = new MovieScreening(circle, wednesday.plusHours(2), 10.0);

        MovieScreening squareScreening1 = new MovieScreening(square, friday, 10.0);
        MovieScreening squareScreening2 = new MovieScreening(square, friday.plusHours(2), 10.0);

        MovieScreening triangleScreening1 = new MovieScreening(triangle, saturday, 10.0);
        MovieScreening triangleScreening2 = new MovieScreening(triangle, saturday.plusHours(2), 10.0);
        
        circle.addScreening(circleScreening1);
        circle.addScreening(circleScreening2);

        square.addScreening(squareScreening1);
        square.addScreening(squareScreening2);

        triangle.addScreening(triangleScreening1);
        triangle.addScreening(triangleScreening2);

        //Test Tickets
        MovieTicket circleTicket = new MovieTicket(circleScreening1, false, 1, 1);
        MovieTicket circleTicketPremium = new MovieTicket(circleScreening1, true, 1, 2);

        MovieTicket squareTicket = new MovieTicket(squareScreening1, false, 1, 1);
        MovieTicket squareTicketPremium = new MovieTicket(squareScreening1, true, 1, 2);

        MovieTicket triangleTicket = new MovieTicket(triangleScreening1, false, 1, 1);
        MovieTicket triangleTicketPremium = new MovieTicket(triangleScreening1, true, 1, 2);

        //Test Orders
        Order circleOrder = new Order(1, false);
        Order circleOrderStudent = new Order(2, true);

        circleOrder.addSeatReservation(circleTicket);
        circleOrder.addSeatReservation(circleTicketPremium);

        circleOrderStudent .addSeatReservation(circleTicket);
        circleOrderStudent.addSeatReservation(circleTicketPremium);

        Order squareOrder = new Order(3, false);
        Order squareOrderStudent = new Order(4, true);

        squareOrder.addSeatReservation(squareTicket);
        squareOrder.addSeatReservation(squareTicketPremium);

        squareOrderStudent .addSeatReservation(squareTicket);
        squareOrderStudent.addSeatReservation(squareTicketPremium);

        Order triangleOrder = new Order(5, false);
        Order triangleOrderStudent = new Order(6, true);

        triangleOrder.addSeatReservation(triangleTicket);
        triangleOrder.addSeatReservation(triangleTicketPremium);

        triangleOrderStudent .addSeatReservation(triangleTicket);
        triangleOrderStudent.addSeatReservation(triangleTicketPremium);
    }

    @Test
    OrderContainsOnlyNonPremiumNonStudentWeekendTickets() {

    }
}
