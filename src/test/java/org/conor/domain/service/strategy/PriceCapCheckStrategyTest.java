package org.conor.domain.service.strategy;

import org.conor.domain.model.MarketAction;
import org.conor.domain.model.Order;
import org.conor.domain.model.Side;
import org.conor.domain.model.Trade;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceCapCheckStrategyTest {

    private static final MarketAction.Builder builder = new MarketAction.Builder();
    PriceCheckStrategy strategy = new PriceCapCheckStrategy();

    Trade buyTrade = builder
            .setPrice(10.00).setSide(Side.BUY)
            .setTimestamp(LocalDateTime.now())
            .buildTrade();

    @Test
    void isWithinPercentCapOfPriceTest() {
        Order order = builder
                .setPrice(10.51).setSide(Side.SELL)
                .setTimestamp(LocalDateTime.now().minusMinutes(10))
                .buildOrder();
        assertTrue(strategy.isWithinPercentCapOfPrice(buyTrade, order, 0.10));
    }

    @Test
    void isOutsidePercentCapOfPriceTest() {
        Order order = builder
                .setPrice(9.51).setSide(Side.SELL)
                .setTimestamp(LocalDateTime.now().minusMinutes(10))
                .buildOrder();
        assertFalse(strategy.isWithinPercentCapOfPrice(buyTrade, order, 0.10));
    }
}