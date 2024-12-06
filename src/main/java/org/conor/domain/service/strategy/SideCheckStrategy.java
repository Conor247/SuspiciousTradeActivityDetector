package org.conor.domain.service.strategy;

import org.conor.domain.model.Order;
import org.conor.domain.model.Trade;

public interface SideCheckStrategy {
    boolean isOppositeSide(Trade trade, Order order);
}
