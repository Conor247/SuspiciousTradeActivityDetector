package org.conor.domain.service;

import org.conor.domain.config.TradeOrderInspectorConfig;
import org.conor.domain.model.Order;
import org.conor.domain.model.Side;
import org.conor.domain.model.Trade;
import org.conor.domain.service.strategy.*;
import org.conor.helper.TestDataBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TradeOrderInspectorTest {

    TradeOrderInspectorConfig config = new TradeOrderInspectorConfig(0.10, 1800);
    SideCheckStrategy sideCheck = new SideOppositeCheckStrategy();
    TimeCheckStrategy timeCheck = new TimeCapCheckStrategy();
    PriceCapCheckStrategy priceCheck = new PriceCapCheckStrategy();

    @Test //All Data in one test to show no mix-ups
    void suspiciousTest() {
        List<Trade> trades = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        //build data
        trades.add(TestDataBuilder.buildTradeAction(Side.BUY, 10.00, 0));
        orders.addAll(TestDataBuilder.buildSellOrderList());
        trades.add(TestDataBuilder.buildTradeAction(Side.SELL, 10.00, 0));
        orders.addAll(TestDataBuilder.buildBuyOrderList());
        trades.add(TestDataBuilder.buildTradeAction(Side.BUY, -10.00, 0));
        orders.addAll(TestDataBuilder.buildNegativePriceSellOrderList());
        trades.add(TestDataBuilder.buildTradeAction(Side.SELL, -10.00, 0));
        orders.addAll(TestDataBuilder.buildNegativePriceBuyOrderList());
        trades.add(TestDataBuilder.buildTradeAction(Side.SELL, 40.00, 0));
        orders.add(TestDataBuilder.buildOrderAction(Side.BUY, 20.00, 1));

        //service under test
        TradeOrderInspector sut = new TradeOrderInspectorImpl(config, sideCheck, timeCheck, priceCheck);
        Map<Trade, List<Order>> results = sut.suspiciousActivityDetector(trades, orders);

        //assertions
        assertEquals(4, results.size());

        for (Map.Entry<Trade, List<Order>> mappedEntry : results.entrySet()) {
            assertEquals(2, mappedEntry.getValue().size());
        }
    }

    @Test //BUY Trade SELL Order - Attempt Upward Pressure Detected
    void suspiciousActivityDetectorBuyTradeSellOrderTest() {
        List<Trade> trades = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        trades.add(TestDataBuilder.buildTradeAction(Side.BUY, 10.00, 0));
        orders.addAll(TestDataBuilder.buildSellOrderList());

        TradeOrderInspector sut = new TradeOrderInspectorImpl(config, sideCheck, timeCheck, priceCheck);
        Map<Trade, List<Order>> results = sut.suspiciousActivityDetector(trades, orders);

        assertEquals(trades.size(), results.size());
        assertEquals(2, results.entrySet().iterator().next().getValue().size());
    }

    @Test //SELL Trade BUY Order - Attempt Downward Pressure Detected
    void suspiciousActivityDetectorSellTradeBuyOrderTest() {
        List<Trade> trades = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        trades.add(TestDataBuilder.buildTradeAction(Side.SELL, 10.00, 0));
        orders.addAll(TestDataBuilder.buildBuyOrderList());

        TradeOrderInspector sut = new TradeOrderInspectorImpl(config, sideCheck, timeCheck, priceCheck);
        Map<Trade, List<Order>> results = sut.suspiciousActivityDetector(trades, orders);

        assertEquals(trades.size(), results.size());
        assertEquals(2, results.entrySet().iterator().next().getValue().size());    }

    @Test //BUY Trade SELL Order - Negative Price - Attempt Upward Pressure Detected
    void suspiciousActivityDetectorBuyTradeNegativePriceTest() {
        List<Trade> trades = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        trades.add(TestDataBuilder.buildTradeAction(Side.BUY, -10.00, 0));
        orders.addAll(TestDataBuilder.buildNegativePriceSellOrderList());

        TradeOrderInspector sut = new TradeOrderInspectorImpl(config, sideCheck, timeCheck, priceCheck);
        Map<Trade, List<Order>> results = sut.suspiciousActivityDetector(trades, orders);

        assertEquals(trades.size(), results.size());
        assertEquals(2, results.entrySet().iterator().next().getValue().size());    }

    @Test //SELL Trade BUY Order - Negative Price - Attempt Downward Pressure Detected
    void suspiciousActivityDetectorSellTradeNegativePriceTest() {
        List<Trade> trades = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        trades.add(TestDataBuilder.buildTradeAction(Side.SELL, -10.00, 0));
        orders.addAll(TestDataBuilder.buildNegativePriceBuyOrderList());

        TradeOrderInspector sut = new TradeOrderInspectorImpl(config, sideCheck, timeCheck, priceCheck);
        Map<Trade, List<Order>> results = sut.suspiciousActivityDetector(trades, orders);

        assertEquals(trades.size(), results.size());
        assertEquals(2, results.entrySet().iterator().next().getValue().size());    }

    @Test //No Suspicious Activity
    void suspiciousActivityDetectorNoSuspiciousActivityTest() {
        List<Trade> trades = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        trades.add(TestDataBuilder.buildTradeAction(Side.SELL, 40.00, 20)); //Trade 20minutes ago
        orders.add(TestDataBuilder.buildOrderAction(Side.BUY, 30.00, 25));  //Outside 10%
        orders.add(TestDataBuilder.buildOrderAction(Side.BUY, 39.00, 0));   //After Trade Time
        orders.add(TestDataBuilder.buildOrderAction(Side.SELL, 39.00, 30)); //Wrong Side
        orders.add(TestDataBuilder.buildOrderAction(Side.SELL, 41.00, 30)); //Wrong Side

        TradeOrderInspector sut = new TradeOrderInspectorImpl(config, sideCheck, timeCheck, priceCheck);
        Map<Trade, List<Order>> results = sut.suspiciousActivityDetector(trades, orders);

        assertEquals(0, results.size());
    }

}