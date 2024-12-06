package org.conor.domain.service.strategy;

import org.conor.domain.model.Order;
import org.conor.domain.model.Trade;

public interface PriceCheckStrategy {
    boolean isWithinPercentCapOfPrice(Trade trade, Order order, double percentThreshold);
}
