package com.vedanshu.investmentportfoliotracker.service;

import com.vedanshu.investmentportfoliotracker.entity.Trade;
import com.vedanshu.investmentportfoliotracker.entity.TradeType;
import com.vedanshu.investmentportfoliotracker.repository.TradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handles the core business logic for processing trades and calculating portfolio metrics.
 *
 * @author Vedanshu Garg
 */
@Service
public class TradeService {

    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    /**
     * Records a new trade in the database.
     *
     * @param trade the trade details to save
     * @return the saved trade with its generated ID
     */
    @Transactional
    public Trade recordTrade(Trade trade) {
        // @todo Add validation here later (e.g., checking if they have enough shares to sell)
        return tradeRepository.save(trade);
    }

    /**
     * Retrieves the entire ledger of all trades.
     *
     * @return a list of all historical trades
     */
    public List<Trade> getTradeHistory() {
        return tradeRepository.findAll();
    }

    /**
     * Calculates the current quantity owned for each stock ticker.
     * It adds BUY quantities and subtracts SELL quantities.
     *
     * @return a map where the key is the ticker symbol and the value is the total shares owned
     */
    public Map<String, BigDecimal> getPortfolioHoldings() {
        List<Trade> allTrades = tradeRepository.findAll();

        return allTrades.stream()
                .collect(Collectors.groupingBy(
                        Trade::getTickerSymbol,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                trade -> trade.getTradeType() == TradeType.BUY
                                        ? trade.getQuantity()
                                        : trade.getQuantity().negate(),
                                BigDecimal::add
                        )
                ));
    }
}
