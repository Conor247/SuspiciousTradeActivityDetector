package org.conor.domain.service.strategy;

import org.conor.domain.model.MarketAction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeCapCheckStrategyTest {

    private static final TimeCheckStrategy strategy = new TimeCapCheckStrategy();
    private static final MarketAction.Builder builder = new MarketAction.Builder();

    @Test
    void isWithinTimeCapBeforeTradeTest() {

        assertTrue(strategy.isWithinTimeCapBeforeTrade(
                builder.setTimestamp(LocalDateTime.now()).buildTrade(),
                builder.setTimestamp(LocalDateTime.now().minusMinutes(2L)).buildOrder(),
                1800));
    }

    @Test
    void isNotWithinTimeCapBeforeTradeTest() {

        assertFalse(strategy.isWithinTimeCapBeforeTrade(
                builder.setTimestamp(LocalDateTime.now()).buildTrade(),
                builder.setTimestamp(LocalDateTime.now().minusMinutes(200L)).buildOrder(),
                1800));
    }

}