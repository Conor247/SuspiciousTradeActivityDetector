package org.conor.domain.service.strategy;

import org.conor.domain.model.Order;
import org.conor.domain.model.Trade;

import java.time.Duration;

public interface TimeCheckStrategy {

    boolean isWithinTimeCapBeforeTrade(Trade trade, Order order, long timeCapInSeconds);
}
