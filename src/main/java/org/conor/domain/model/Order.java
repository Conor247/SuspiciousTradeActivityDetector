package org.conor.domain.model;

import java.time.LocalDateTime;

public class Order extends MarketAction {
    public Order(long id, double price, double volume, Side side, LocalDateTime timestamp) {
        super(id, price, volume, side, timestamp);
    }
}
