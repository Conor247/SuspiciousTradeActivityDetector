package org.conor.domain.service.strategy;

import org.conor.domain.model.MarketAction;
import org.conor.domain.model.Side;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SideOppositeCheckStrategyTest {

    private static final SideCheckStrategy strategy = new SideOppositeCheckStrategy();
    private static final MarketAction.Builder builder = new MarketAction.Builder();

    @Test
    void isOppositeSideTest() {
        assertTrue(strategy.isOppositeSide(
                builder.setSide(Side.BUY).buildTrade(),
                builder.setSide(Side.SELL).buildOrder()
        ));
    }

    @Test
    void isNotOppositeSideTest() {
        assertFalse(strategy.isOppositeSide(
                builder.setSide(Side.BUY).buildTrade(),
                builder.setSide(Side.BUY).buildOrder()
        ));
    }
}