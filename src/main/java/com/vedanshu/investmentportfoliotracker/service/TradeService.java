package com.vedanshu.investmentportfoliotracker.service;

import com.vedanshu.investmentportfoliotracker.dto.AssetValuation;
import com.vedanshu.investmentportfoliotracker.dto.PortfolioValuationResponse;
import com.vedanshu.investmentportfoliotracker.entity.Trade;
import com.vedanshu.investmentportfoliotracker.entity.TradeType;
import com.vedanshu.investmentportfoliotracker.repository.TradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
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
    private final MarketDataService marketDataService;

    public TradeService(TradeRepository tradeRepository, MarketDataService marketDataService) {
        this.tradeRepository = tradeRepository;
        this.marketDataService = marketDataService;
    }

    @Transactional
    public Trade recordTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    public List<Trade> getTradeHistory() {
        return tradeRepository.findAll();
    }

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

    /**
     * Calculates the real-time valuation of the portfolio by fetching live market prices.
     *
     * @return a structured response containing individual asset values and the grand total
     */
    public PortfolioValuationResponse getLivePortfolioValuation() {
        Map<String, BigDecimal> holdings = getPortfolioHoldings();
        Map<String, AssetValuation> breakdown = new HashMap<>();
        BigDecimal grandTotal = BigDecimal.ZERO;

        for (Map.Entry<String, BigDecimal> entry : holdings.entrySet()) {
            String ticker = entry.getKey();
            BigDecimal quantity = entry.getValue();

            // Only evaluating assets we currently own (quantity > 0)
            if (quantity.compareTo(BigDecimal.ZERO) > 0) {
                try {
                    BigDecimal livePrice = marketDataService.getLivePrice(ticker);
                    BigDecimal assetTotal = livePrice.multiply(quantity);

                    breakdown.put(ticker, new AssetValuation(quantity, livePrice, assetTotal));
                    grandTotal = grandTotal.add(assetTotal);
                } catch (Exception e) {
                    // In a production app, we would log this and perhaps use a fallback/cached price
                    // For now, we skip the asset if the API fails or rate limits us
                    System.err.println("Could not fetch price for " + ticker + ": " + e.getMessage());
                }
            }
        }

        return new PortfolioValuationResponse(breakdown, grandTotal);
    }
}
