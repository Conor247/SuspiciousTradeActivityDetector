package org.conor.domain.service.strategy;

import org.conor.domain.model.Order;
import org.conor.domain.model.Trade;

import java.time.Duration;

public class TimeCapCheckStrategy implements TimeCheckStrategy {

    @Override
    public boolean isWithinTimeCapBeforeTrade(Trade trade, Order order, long timeCapInSeconds) {
        Duration duration = Duration.between(order.getTimestamp(), trade.getTimestamp());
        return !duration.isNegative() && duration.toSeconds() <= timeCapInSeconds;
    }
}
