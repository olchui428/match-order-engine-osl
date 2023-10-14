package MatchOrderEngine;

import java.util.HashMap;
import java.util.Vector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatchOrderEngine {
    static private HashMap<Integer, Order> standingOrders = new HashMap<>();

    static public HashMap<Integer, Order> getStandingOrders() {
        return standingOrders;
    }

    public void setStandingOrders(HashMap<Integer, Order> orders) {
        this.standingOrders = orders;
    }

    public void addStandingOrder(Order order) {
        standingOrders.put(order.getId(), order);
    }

    @Test
    public void main() throws Exception {
        Vector<Order> inputQueue = new Vector<>();
        inputQueue.add(new Order(Action.SELL, 10.5, 100.0, "BTC"));
        inputQueue.add(new Order(Action.SELL, 10.5, 100.0, "BTC"));
        inputQueue.add(new Order(Action.SELL, 10.5, 100.0, "BTC"));
        inputQueue.add(new Order(Action.SELL, 10.5, 100.0, "BTC"));
        inputQueue.add(new Order(Action.BUY, 10.5, 350.0, "BTC"));
//        inputQueue.add(new Order(Action.CANCEL, 3));

        HashMap<String, LimitOrderBook> limitOrderBooks = new HashMap<>();

        for (Order order : inputQueue) {
            standingOrders.put(order.getId(), order);
            String instrument;
            if (!order.getAction().equals(Action.CANCEL)) {
                instrument = order.getInstrument();
                if (limitOrderBooks.get(instrument) == null) {
                    limitOrderBooks.put(instrument, new LimitOrderBook(instrument));
                }
            } else {
                if (order.linkOrderToBeCancelled()) {
                    order.linkOrderToBeCancelled();
                    instrument = order.getOrderToBeCancelled().getInstrument();
                } else {
                    throw new Exception("<Error: Order to be cancelled does not exist or has already been executed.>");
                }
            }
            Vector<String> logs = limitOrderBooks.get(instrument).process(order);
            for (String log : logs) {
                System.out.println(log);
            }
            System.out.println("limitOrderBooks = " + limitOrderBooks.get(instrument));
            System.out.println("================================");
        }
        Assertions.assertEquals(4, limitOrderBooks.get("BTC").getTrades().size());
        Assertions.assertEquals(1, limitOrderBooks.get("BTC").getLOBAsk().keySet().size());
        Assertions.assertEquals(1, limitOrderBooks.get("BTC").getLOBBid().keySet().size());
    }


}
