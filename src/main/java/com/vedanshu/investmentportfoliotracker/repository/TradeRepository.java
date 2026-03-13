package com.vedanshu.investmentportfoliotracker.repository;

import com.vedanshu.investmentportfoliotracker.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Trade entity database operations.
 * Spring Data JPA automatically provides the implementation at runtime.
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    /**
     * Custom finder method to retrieve all trades for a specific stock ticker.
     *
     * @param tickerSymbol the stock symbol (e.g., "RELIANCE.NS")
     * @return a list of matching trades
     */
    List<Trade> findByTickerSymbol(String tickerSymbol);
}
