package com.vedanshu.investmentportfoliotracker.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a single stock market transaction (Buy or Sell).
 */
@Entity
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String tickerSymbol; // e.g., "RELIANCE.NS" or "AAPL"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradeType tradeType;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(nullable = false, precision = 15, scale = 4)
    private BigDecimal pricePerShare;

    @Column(nullable = false)
    private LocalDateTime tradeDate;

    public Trade() {}

    public Trade(String tickerSymbol, TradeType tradeType, BigDecimal quantity, BigDecimal pricePerShare, LocalDateTime tradeDate) {
        this.tickerSymbol = tickerSymbol;
        this.tradeType = tradeType;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
        this.tradeDate = tradeDate;
    }
}
