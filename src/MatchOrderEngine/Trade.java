package MatchOrderEngine;

import Utils.Utils;

import java.time.LocalDateTime;


public class Trade {
    final private Integer txId;
    private LocalDateTime timestamp;
    private String instrument;
    private Double price;
    private Double quantity;
    private Integer buyOrderId;
    private Integer sellOrderId;
    static private Integer idCounter = 0;

    public Trade(Order buyOrder, Order sellOrder, Double quantity) {
        this.txId = idCounter++;
        this.timestamp = LocalDateTime.now();
        this.instrument = buyOrder.getInstrument();
        this.price = buyOrder.getPrice();
        this.quantity = quantity;
        this.buyOrderId = buyOrder.getId();
        this.sellOrderId = sellOrder.getId();
    }

    public Integer getTxId() {
        return txId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
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

    public Integer getBuyOrderId() {
        return buyOrderId;
    }

    public void setBuyOrderId(Integer buyOrderId) {
        this.buyOrderId = buyOrderId;
    }

    public Integer getSellOrderId() {
        return sellOrderId;
    }

    public void setSellOrderId(Integer sellOrderId) {
        this.sellOrderId = sellOrderId;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "txId=" + txId +
                ", timestamp=" + timestamp +
                ", instrument='" + instrument + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", buyOrderId=" + buyOrderId +
                ", sellOrderId=" + sellOrderId +
                '}';
    }

    public String toLog() {
        return timestamp.format(Utils.formatter)
                + " BUYORDER(" + buyOrderId
                + ") & SELLORDER(" + sellOrderId
                + ") MATCHED WITH " + quantity + " " + instrument
                + " @ " + price
                + " (tx" + txId + ")";
    }
}
