package org.conor.domain.service.strategy;

import org.conor.domain.model.Order;
import org.conor.domain.model.Side;
import org.conor.domain.model.Trade;

public class SideOppositeCheckStrategy implements SideCheckStrategy {

    @Override
    public boolean isOppositeSide(Trade trade, Order order) {
        return trade.getSide() == Side.BUY && order.getSide() == Side.SELL ||
                trade.getSide() == Side.SELL && order.getSide() == Side.BUY;
    }
}
