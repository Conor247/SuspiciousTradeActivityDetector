package org.conor.domain.service.strategy;

import org.conor.domain.model.Order;
import org.conor.domain.model.Side;
import org.conor.domain.model.Trade;

public class PriceCapCheckStrategy implements PriceCheckStrategy {

    @Override
    public boolean isWithinPercentCapOfPrice(Trade trade, Order order, double percentThreshold) {
        double lowerBound = calculateLowerBound(trade, percentThreshold);
        double upperBound = calculateUpperBound(trade, percentThreshold);
        return order.getPrice() >= lowerBound && order.getPrice() <= upperBound;
    }

    private double calculateLowerBound(Trade trade, double percentThreshold) {
        if(trade.getSide() == Side.BUY) {
            return trade.getPrice();
        } else if(trade.getSide() == Side.SELL && trade.getPrice() > 0) {
            return trade.getPrice() * (1 - percentThreshold);
        } else {
            //because of negative number line inversion the lower bound needs to be flipped
            return trade.getPrice() * (1 + percentThreshold);
        }
    }

    private double calculateUpperBound(Trade trade, double percentThreshold) {
        if(trade.getSide() == Side.SELL) {
            return trade.getPrice();
        } else if(trade.getSide() == Side.BUY && trade.getPrice() > 0) {
            return trade.getPrice() * (1 + percentThreshold);
        } else {
            //because of negative number line inversion the upper bound needs to be flipped
            return trade.getPrice() * (1 - percentThreshold);
        }
    }
}
