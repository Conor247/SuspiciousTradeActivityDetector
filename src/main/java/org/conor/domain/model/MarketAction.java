package org.conor.domain.model;

import java.time.LocalDateTime;

public abstract class MarketAction {

    private final long id;
    private final double price;
    private final double volume;
    private final Side side;
    private final LocalDateTime timestamp;

    protected MarketAction(long id, double price, double volume, Side side, LocalDateTime timestamp) {
        this.id = id;
        this.price = price;
        this.volume = volume;
        this.side = side;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public double getVolume() {
        return volume;
    }

    public Side getSide() {
        return side;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public static class Builder {
        private long id;
        private double price;
        private double volume;
        private Side side;
        private LocalDateTime timestamp;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setVolume(double volume) {
            this.volume = volume;
            return this;
        }

        public Builder setSide(Side side) {
            this.side = side;
            return this;
        }

        public Builder setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        //build method for trades so that type safe Trade objects can be created
        public Trade buildTrade() {
            return new Trade(id, price, volume, side, timestamp);
        }

        //build method for orders so that type safe Order objects can be created
        public Order buildOrder() {
            return new Order(id, price, volume, side, timestamp);
        }
    }

    @Override
    public String toString() {
        return "[" + "id=" + id + ", price=" + price + ", volume=" + volume + ", side=" + side + ", timestamp=" + timestamp + ']';
    }
}
