package org.conor;

import org.conor.domain.config.TradeOrderInspectorConfig;
import org.conor.domain.model.MarketAction;
import org.conor.domain.model.Order;
import org.conor.domain.model.Side;
import org.conor.domain.model.Trade;
import org.conor.domain.service.TradeOrderInspector;
import org.conor.domain.service.TradeOrderInspectorImpl;
import org.conor.domain.service.strategy.*;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        final MarketAction.Builder builder = new MarketAction.Builder();
        Trade myFirstTrade = builder
                .setId(1L).setVolume(100.00).setPrice(10.00).setSide(Side.BUY)
                .setTimestamp(LocalDateTime.now())
                .buildTrade();
        Trade mySecondTrade = builder
                .setId(2L).setVolume(100.00).setPrice(20.00).setSide(Side.SELL)
                .setTimestamp(LocalDateTime.now())
                .buildTrade();
        Order myFirstOrder = builder
                .setId(3L).setVolume(100.00).setPrice(10.51).setSide(Side.SELL)
                .setTimestamp(LocalDateTime.now().minusMinutes(10))
                .buildOrder();
        Order mySecondOrder = builder
                .setId(4L).setVolume(1000.00).setPrice(10.52).setSide(Side.SELL)
                .setTimestamp(LocalDateTime.now().minusMinutes(10))
                .buildOrder();

        List<Trade> trades = List.of(myFirstTrade, mySecondTrade);
        List<Order> orders = List.of(myFirstOrder, mySecondOrder);

        TradeOrderInspectorConfig config = new TradeOrderInspectorConfig(0.10, 1800);
        SideCheckStrategy sideCheck = new SideOppositeCheckStrategy();
        TimeCheckStrategy timeCheck = new TimeCapCheckStrategy();
        PriceCapCheckStrategy priceCheck = new PriceCapCheckStrategy();

        TradeOrderInspector inspector = new TradeOrderInspectorImpl(config, sideCheck, timeCheck, priceCheck);

        System.out.println("Suspicious Activity Report:");
        inspector.suspiciousActivityDetector(trades, orders).forEach((trade, ordersList) -> {
            System.out.println("Trade: " + trade);
            System.out.println("Associated Orders:");
            ordersList.forEach(order -> System.out.println(" - " + order));
        });
    }
}