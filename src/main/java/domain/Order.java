package domain;

import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
        int totalPrice = 0;
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
            totalPrice = (int) newVals.getKey();
            amountPremium = (int) newVals.getValue();
            //Add the fee for premium tickets
            totalPrice += (amountPremium * 2);
        }


        return 0;
    }

    private Pair<Integer, Integer> secondTicketFree(int totalAmount, int amountPremium, int totalPrice){

        //If the total amount of tickets is uneven, remove one for the calculation (only 2nd is free)
        //The discount is calculated as a 50% discount on the part of the price with even tickets
        if ((totalAmount%2) != 0) {
            totalPrice = ((((totalPrice / totalAmount) * (totalAmount - 1)) / 2)
                            + ((totalPrice / totalAmount) * 1));
        }

        //If the amount of premium tickets is uneven, remove one for the calculation (only 2nd is free)
        if ((amountPremium%2) != 0) {
            amountPremium = ((amountPremium - 1) / 2) + 1;
        }

        return new Pair<Integer, Integer>(totalPrice, amountPremium);
    }

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
