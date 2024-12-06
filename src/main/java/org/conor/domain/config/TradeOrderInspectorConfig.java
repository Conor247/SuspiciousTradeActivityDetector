package org.conor.domain.config;

public class TradeOrderInspectorConfig {
    private final double priceThresholdPercentage;
    private final long timeCapInSeconds;

    public TradeOrderInspectorConfig(double priceThresholdPercentage, long timeCapInSeconds) {
        this.priceThresholdPercentage = priceThresholdPercentage;
        this.timeCapInSeconds = timeCapInSeconds;
    }

    public double getPriceThresholdPercentage() {
        return priceThresholdPercentage;
    }

    public long getTimeCapInSeconds() {
        return timeCapInSeconds;
    }
}
