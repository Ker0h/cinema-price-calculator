package domain;

import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;

public class Order
{
    private int orderNr;
    private boolean isStudentOrder;

    private ArrayList<MovieTicket> tickets;

    public Order(int orderNr, boolean isStudentOrder)
    {
        this.orderNr = orderNr;
        this.isStudentOrder = isStudentOrder;

        tickets = new ArrayList<MovieTicket>();
    }

    public int getOrderNr()
    {
        return orderNr;
    }

    public void addSeatReservation(MovieTicket ticket)
    {
        tickets.add(ticket);
    }

    public double calculatePrice() {
        double totalPrice = 0.00;
        int totalAmount = 0;
        int amountPremium = 0;

        //Go through every ticket
        for (MovieTicket ticket : tickets) {
            //Count amount of premium tickets
            if (ticket.isPremiumTicket()) {
                amountPremium++;
            }
            //Add price to total of order
            totalPrice += ticket.getPrice();
            //Count amount of tickets (for 2nd free discount)
            totalAmount++;
        }

        //Check if it is a student order
        if (isStudentOrder) {
            //Calculate every second ticket as free
            Pair newVals = secondTicketFree(totalAmount, amountPremium, totalPrice);
            //Set the new price
            totalPrice = (double) newVals.getKey();
            amountPremium = (int) newVals.getValue();
            //Add the fee for premium tickets
            totalPrice += (amountPremium * 2);
        }

        else {
            //Handle second ticket free for monday until thursday
            Pair newVals = secondMoTuWeThTicketFree(tickets);
            totalPrice = totalPrice - (double) newVals.getKey();
            amountPremium = amountPremium - (int) newVals.getValue();
            totalPrice += (amountPremium * 3);

            //Handle 10% discount if there are 6+ weekend tickets
            Pair wkndVals = weekendTicketsDiscount(tickets);
            if ((int) wkndVals.getKey() != 0) {
                totalPrice = totalPrice - (double) wkndVals.getKey();
                totalPrice = totalPrice - ((double) wkndVals.getValue() * 3);
            }
        }


        return 0;
    }

    private Pair<Double, Integer> secondTicketFree(int totalAmount, int amountPremium, Double totalPrice) {

        //If the total amount of tickets is uneven, remove one for the calculation (only 2nd is free)
        //The discount is calculated as a 50% discount on the part of the price with even tickets
        if ((totalAmount%2) != 0) {
            totalPrice = ((((totalPrice / totalAmount) * (totalAmount - 1)) / 2)
                            + ((totalPrice / totalAmount) * 1));
        } else {
            totalPrice = totalPrice / 2;
        }

        //If the amount of premium tickets is uneven, remove one for the calculation (only 2nd is free)
        if ((amountPremium%2) != 0) {
            amountPremium = ((amountPremium - 1) / 2) + 1;
        } else {
            amountPremium = amountPremium / 2;
        }

        return new Pair<Double, Integer>(totalPrice, amountPremium);
    }

    private Pair<Double, Integer> secondMoTuWeThTicketFree(ArrayList<MovieTicket> tickets) {
        int discountAmount = 0;
        Double discountPrice = 0.00;
        int amountPremium = 0;

        for (MovieTicket ticket : tickets) {
            //Get tickets with the correct day for the discount
            DayOfWeek dayOfWeek = ticket.getMovieScreening().getDateAndTime().getDayOfWeek();
            if (dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.TUESDAY || dayOfWeek == DayOfWeek.WEDNESDAY || dayOfWeek == DayOfWeek.THURSDAY) {
                discountAmount ++;
                discountPrice += ticket.getPrice();
            }
            if (ticket.isPremiumTicket()) {
                amountPremium ++;
            }
        }

        //If the total amount of tickets is uneven, remove one for the calculation (only 2nd is free)
        //The discount is calculated as a 50% discount on the part of the price with even tickets
        if ((discountAmount%2) != 0) {
            discountPrice = (((discountPrice / discountAmount) * (discountAmount - 1)) / 2);
        } else {
            discountPrice = discountPrice / 2;
        }

        //If the amount of premium tickets is uneven, remove one for the calculation (only 2nd is free)
        if ((amountPremium%2) != 0) {
            amountPremium = ((amountPremium - 1) / 2);
        } else {
            amountPremium = amountPremium / 2;
        }

        return new Pair<Double, Integer>(discountPrice, amountPremium);
    }

    private Pair<Double, Double> weekendTicketsDiscount(ArrayList<MovieTicket> tickets) {
        int discountAmount = 0;
        double discountPrice = 0;
        double amountPremium = 0;

        for (MovieTicket ticket : tickets) {
            //Get tickets with the correct day for the discount
            DayOfWeek dayOfWeek = ticket.getMovieScreening().getDateAndTime().getDayOfWeek();
            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                discountAmount ++;
                discountPrice += ticket.getPrice();
            }
            if (ticket.isPremiumTicket()) {
                amountPremium ++;
            }
        }

        if (discountAmount >= 6) {
            if ((discountAmount%2) != 0) {
                discountPrice = (((discountPrice / discountAmount) * (discountAmount - 1)) * 0.1);
            } else {
                discountPrice = discountPrice * 0.1;
            }


            //If the amount of premium tickets is uneven, remove one for the calculation (only 2nd is free)
            if ((amountPremium%2) != 0) {
                amountPremium = ((amountPremium - 1) * 0.1);
            } else {
                amountPremium = amountPremium * 0.1;
            }
        }

        return new Pair<Double, Double>(discountPrice, amountPremium);
    }

        // Bases on the string respresentations of the tickets (toString), write
        // the ticket to a file with naming convention Order_<orderNr>.txt of
        // Order_<orderNr>.json
    public void export(TicketExportFormat exportFormat) throws IOException {
        if(exportFormat.equals(TicketExportFormat.PLAINTEXT)) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Order_" + orderNr + ".txt", true));
            
            writer.write("Order " + orderNr + ": ");

            for (MovieTicket ticket: tickets) {
                writer.append(ticket.toString());
                writer.append("\n\n");
            }

            writer.close();
        }
    }
}
