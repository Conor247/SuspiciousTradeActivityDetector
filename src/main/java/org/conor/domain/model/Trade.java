package org.conor.domain.model;

import java.time.LocalDateTime;

public class Trade extends MarketAction {
    public Trade(long id, double price, double volume, Side side, LocalDateTime timestamp) {
        super(id, price, volume, side, timestamp);
    }
}
