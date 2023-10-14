package MatchOrderEngine;

import Utils.Utils;

import java.time.LocalDateTime;

public class Order {
    final private Integer id;
    private LocalDateTime timestamp;
    private Action action;
    private Double price;
    private Double quantity;
    private String instrument;
    private Integer idOrderToBeCancelled;
    private Action actionOrderToBeCancelled;
    private Double priceOrderToBeCancelled;
    private Order orderToBeCancelled;
    static private Integer idCounter = 0;

    public Order(Action action, Double price, Double quantity, String instrument) {
        this.id = idCounter++;
        this.timestamp = LocalDateTime.now();
        this.action = action;
        this.price = price;
        this.quantity = quantity;
        this.instrument = instrument;
    }

    // For cancel orders
    public Order(Action action, Integer idOrderToBeCancelled) {
        this.id = idCounter++;
        this.timestamp = LocalDateTime.now();
        this.action = action;
        this.idOrderToBeCancelled = idOrderToBeCancelled;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public Integer getIdOrderToBeCancelled() {
        return idOrderToBeCancelled;
    }

    public void setIdOrderToBeCancelled(Integer idOrderToBeCancelled) {
        this.idOrderToBeCancelled = idOrderToBeCancelled;
    }

    public Order getOrderToBeCancelled() {
        return orderToBeCancelled;
    }

    public void setOrderToBeCancelled(Order orderToBeCancelled) {
        this.orderToBeCancelled = orderToBeCancelled;
    }

    public boolean linkOrderToBeCancelled() {
        if (new MatchOrderEngine().getStandingOrders().get(idOrderToBeCancelled) == null) {
            return false;
        } else {
            this.orderToBeCancelled = new MatchOrderEngine().getStandingOrders().get(idOrderToBeCancelled);
            return true;
        }
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", timestamp=" + timestamp + ", action=" + action + ", price=" + price + ", quantity=" + quantity + ", instrument='" + instrument + '\'' + '}';
    }

    public String toLog() {
        if (action.equals(Action.CANCEL)) {
            return timestamp.format(Utils.formatter) + " " + action.toString() + " " + orderToBeCancelled.action + "ORDER(" + orderToBeCancelled.getId() + ") " + orderToBeCancelled.quantity + " " + orderToBeCancelled.instrument + " @ " + orderToBeCancelled.price + " (" + id + ")";
        }
        return timestamp.format(Utils.formatter) + " " + action.toString() + " " + quantity + " " + instrument + " @ " + price + " (" + id + ")";
    }
}
