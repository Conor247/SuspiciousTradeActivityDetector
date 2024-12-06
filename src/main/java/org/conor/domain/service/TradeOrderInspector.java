package org.conor.domain.service;

import org.conor.domain.model.Order;
import org.conor.domain.model.Trade;

import java.util.List;
import java.util.Map;

public interface TradeOrderInspector {

    Map<Trade, List<Order>> suspiciousActivityDetector(List<Trade> trades, List<Order> orders);
}
