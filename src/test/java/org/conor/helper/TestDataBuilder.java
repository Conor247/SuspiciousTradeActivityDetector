package org.conor.helper;

import org.conor.domain.model.MarketAction;
import org.conor.domain.model.Order;
import org.conor.domain.model.Side;
import org.conor.domain.model.Trade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataBuilder {

    private static final MarketAction.Builder builder = new MarketAction.Builder();

    public static Trade buildTradeAction(Side side, double price, int minutesBeforeNow) {
        return builder
                .setId(Math.abs(ThreadLocalRandom.current().nextLong()))
                .setSide(side)
                .setPrice(price)
                .setVolume(1000.00)
                .setTimestamp(LocalDateTime.now().minusMinutes(minutesBeforeNow))
                .buildTrade();
    }

    public static Order buildOrderAction(Side side, double price, int minutesBeforeNow) {
        return builder
                .setId(Math.abs(ThreadLocalRandom.current().nextLong()))
                .setSide(side)
                .setPrice(price)
                .setVolume(1000.00)
                .setTimestamp(LocalDateTime.now().minusMinutes(minutesBeforeNow))
                .buildOrder();
    }

    //Attempt to create Upwards Pressure (Price Increase) after buying
    public static List<Order> buildSellOrderList() {
        List<Order> orders = new ArrayList<>();
        orders.add(buildOrderAction(Side.SELL, 10.51, 1));
        orders.add(buildOrderAction(Side.SELL, 10.52, 14));
        orders.add(buildOrderAction(Side.SELL, 10.53, 31));     //Outside Time
        orders.add(buildOrderAction(Side.SELL, 10.53, -1));     //After Trade Time
        orders.add(buildOrderAction(Side.SELL, 8.53, 3));       //Outside 10%
        orders.add(buildOrderAction(Side.SELL, 11.01, 3));      //Outside 10%
        orders.add(buildOrderAction(Side.SELL, 100.53, 31));    //Outside Both
        return orders;
    }

    //Attempt to create Downwards Pressure (Price Decrease) after selling
    public static List<Order> buildBuyOrderList() {
        List<Order> orders = new ArrayList<>();
        orders.add(buildOrderAction(Side.BUY, 9.51, 1));
        orders.add(buildOrderAction(Side.BUY, 9.52, 14));
        orders.add(buildOrderAction(Side.BUY, 9.53, 31));      //Outside Time
        orders.add(buildOrderAction(Side.BUY, 9.53, -1));      //After Trade Time
        orders.add(buildOrderAction(Side.BUY, 8.53, 3));       //Outside 10%
        orders.add(buildOrderAction(Side.BUY, 11.01, 3));      //Outside 10%
        orders.add(buildOrderAction(Side.BUY, 100.53, 31));    //Outside Both

        return orders;
    }

    //Attempt to create Upwards Pressure (Price Increase) (Less Negative) after buying
    public static List<Order> buildNegativePriceSellOrderList() {
        List<Order> orders = new ArrayList<>();
        orders.add(buildOrderAction(Side.SELL, -9.51, 1));
        orders.add(buildOrderAction(Side.SELL, -9.52, 14));
        orders.add(buildOrderAction(Side.SELL, -9.53, 31));      //Outside Time
        orders.add(buildOrderAction(Side.SELL, -9.53, -1));      //After Trade Time
        orders.add(buildOrderAction(Side.SELL, -8.53, 3));       //Outside 10%
        orders.add(buildOrderAction(Side.SELL, -11.01, 3));      //Outside 10%
        orders.add(buildOrderAction(Side.SELL, -100.53, 31));    //Outside Both
        return orders;
    }

    //Attempt to create Downwards Pressure (Price Decrease) (More Negative) after selling
    public static List<Order> buildNegativePriceBuyOrderList() {
        List<Order> orders = new ArrayList<>();
        orders.add(buildOrderAction(Side.BUY, -10.51, 1));
        orders.add(buildOrderAction(Side.BUY, -10.52, 14));
        orders.add(buildOrderAction(Side.BUY, -10.53, 31));     //Outside Time
        orders.add(buildOrderAction(Side.BUY, -10.53, -1));     //After Trade Time
        orders.add(buildOrderAction(Side.BUY, -8.53, 3));       //Outside 10%
        orders.add(buildOrderAction(Side.BUY, -11.01, 3));      //Outside 10%
        orders.add(buildOrderAction(Side.BUY, -100.53, 31));    //Outside Both
        return orders;
    }
}
