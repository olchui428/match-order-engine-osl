package MatchOrderEngine;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import Utils.Utils;

public class LimitOrderBook {
    private String instrument;
    private HashMap<Double, Vector<Order>> LOBBid = new HashMap<>(); // Bid=Buy. Price->Order
    private HashMap<Double, Vector<Order>> LOBAsk = new HashMap<>(); // Ask=Sell. Price->Order
    private ArrayList<Trade> trades = new ArrayList<>();

    public LimitOrderBook(String instrument) {
        this.instrument = instrument;
    }

    public Vector<String> process(Order order) {
        Vector<String> logs = new Vector<>();

        HashMap<Integer, Order> standingOrders = MatchOrderEngine.getStandingOrders();
        Action action = order.getAction();
        if (action.equals(Action.CANCEL)) {
            Order orderToBeCancelled = order.getOrderToBeCancelled();
            HashMap<Double, Vector<Order>> nearSide = switch (orderToBeCancelled.getAction()) {
                case BUY -> LOBBid;
                case SELL -> LOBAsk;
                case CANCEL -> null;
            };
            nearSide.get(orderToBeCancelled.getPrice()).remove(orderToBeCancelled);
            standingOrders.remove(order.getIdOrderToBeCancelled());
            logs.add("ORDER>>> " + order.toLog());
            return logs;
        }

        Double quantity = order.getQuantity();
        Double price = order.getPrice();

        HashMap<Double, Vector<Order>> nearSide;
        HashMap<Double, Vector<Order>> farSide;
        if (action.equals(Action.BUY)) {
            nearSide = LOBBid;
            farSide = LOBAsk;
        } else {
            nearSide = LOBAsk;
            farSide = LOBBid;
        }

        logs.add("ORDER>>> " + order.toLog());
        if (farSide.get(price) == null) {
            addOrderByPrice(price, order);
            return logs;
        }
        while (quantity > 0 && !farSide.get(price).isEmpty()) {
            Order matchedOrder = farSide.get(price).get(0);
            Double matchedQuantity = matchedOrder.getQuantity();
            if (quantity.equals(matchedQuantity)) {
                System.out.println("matched");
                Trade trade = new Trade(order, matchedOrder, quantity);
                trades.add(trade);
                logs.add("TRADE>>> " + trade.toLog());
//                nearSide.get(price).remove(order);
                farSide.get(price).remove(matchedOrder);
                standingOrders.remove(matchedOrder.getId());
                standingOrders.remove(order.getId());
                quantity = 0.0;
            } else if (quantity > matchedQuantity) {
                quantity -= matchedQuantity;
                Trade trade = new Trade(order, matchedOrder, matchedQuantity);
                trades.add(trade);
                logs.add("TRADE>>> " + trade.toLog());
                order.setQuantity(quantity);
                addOrderByPrice(price, order);
                logs.add("RESIDUAL ORDER>>> " + order.toLog());
                farSide.get(price).remove(matchedOrder);
                standingOrders.remove(matchedOrder.getId());
            } else if (quantity < matchedQuantity) {
                matchedQuantity -= quantity;
                Trade trade = new Trade(order, matchedOrder, quantity);
                trades.add(trade);
                logs.add("TRADE>>> " + trade.toLog());
                matchedOrder.setQuantity(matchedQuantity);
                logs.add("RESIDUAL ORDER>>> " + matchedOrder.toLog());
                quantity = 0.0;
                standingOrders.remove(order.getId());
            }
        }
        return logs;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public HashMap<Double, Vector<Order>> getLOBBid() {
        return LOBBid;
    }

    public void setLOBBid(HashMap<Double, Vector<Order>> LOBBid) {
        this.LOBBid = LOBBid;
    }

    public HashMap<Double, Vector<Order>> getLOBAsk() {
        return LOBAsk;
    }

    public void setLOBAsk(HashMap<Double, Vector<Order>> LOBAsk) {
        this.LOBAsk = LOBAsk;
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public void setTrades(ArrayList<Trade> trades) {
        this.trades = trades;
    }

    private void addOrderByPrice(Double price, Order order) {
        if (order.getAction().equals(Action.BUY)) {
            if (LOBBid.get(price) == null) {
                LOBBid.put(price, new Vector<>(Arrays.asList(order)));
            } else if (!LOBBid.get(price).contains(order)) {
                LOBBid.get(price).add(order);
            }
        } else if (order.getAction().equals(Action.SELL)) {
            if (LOBAsk.get(price) == null) {
                LOBAsk.put(price, new Vector<>(Arrays.asList(order)));
            } else if (!LOBAsk.get(price).contains(order)) {
                LOBAsk.get(price).add(order);
            }
        }
    }

    @Override
    public String toString() {
        return "LimitOrderBook{" + "instrument='" + instrument + '\'' + ", LOBBid=" + LOBBid + ", LOBAsk=" + LOBAsk + ", trades=" + trades + '}';
    }
}
