package domain;

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

    public double calculatePrice()
    {
        int totalPrice = 0;

        for (MovieTicket ticket: tickets) {
            totalPrice += ticket.getPrice();
        }

        return totalPrice;
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
