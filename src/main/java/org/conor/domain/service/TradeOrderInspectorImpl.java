package org.conor.domain.service;

import org.conor.domain.config.TradeOrderInspectorConfig;
import org.conor.domain.model.Order;
import org.conor.domain.model.Trade;
import org.conor.domain.service.strategy.PriceCheckStrategy;
import org.conor.domain.service.strategy.SideCheckStrategy;
import org.conor.domain.service.strategy.TimeCheckStrategy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TradeOrderInspectorImpl implements TradeOrderInspector {

    private final TradeOrderInspectorConfig config;
    private final SideCheckStrategy sideCheckStrategy;
    private final TimeCheckStrategy timeCheckStrategy;
    private final PriceCheckStrategy priceCheckStrategy;

    public TradeOrderInspectorImpl(TradeOrderInspectorConfig config,
                                   SideCheckStrategy sideOppositeCheckStrategy,
                                   TimeCheckStrategy timeCapCheckStrategy,
                                   PriceCheckStrategy priceCheckStrategy) {
        this.config = config;
        this.sideCheckStrategy = sideOppositeCheckStrategy;
        this.timeCheckStrategy = timeCapCheckStrategy;
        this.priceCheckStrategy = priceCheckStrategy;
    }

    @Override
    public Map<Trade, List<Order>> suspiciousActivityDetector(List<Trade> trades, List<Order> orders) {
        Map<Trade, List<Order>> results = trades.stream().collect(
                Collectors.toMap(
                        trade -> trade,
                        trade -> filterSuspiciousOrdersForTrade(trade, orders)
                ));

        //remove any key value pairs that have no suspicious orders
        results.entrySet().removeIf(entry -> entry.getValue().isEmpty());

        return results;
    }

    private List<Order> filterSuspiciousOrdersForTrade(Trade trade, List<Order> orders) {
        //filters the orders which match the criteria of being suspicious
        return orders.stream()
                .filter(order -> isSuspiciousOrder(trade, order))
                .toList();
    }

    private boolean isSuspiciousOrder(Trade trade, Order order) {
        //avoids doing multiple filter runs in the order stream
        return sideCheckStrategy.isOppositeSide(trade, order)
                && timeCheckStrategy.isWithinTimeCapBeforeTrade(trade, order, config.getTimeCapInSeconds())
                && priceCheckStrategy.isWithinPercentCapOfPrice(trade, order, config.getPriceThresholdPercentage());
    }
}
