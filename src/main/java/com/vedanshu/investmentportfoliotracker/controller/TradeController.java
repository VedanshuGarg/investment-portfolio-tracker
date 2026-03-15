package com.vedanshu.investmentportfoliotracker.controller;

import com.vedanshu.investmentportfoliotracker.dto.PortfolioValuationResponse;
import com.vedanshu.investmentportfoliotracker.entity.Trade;
import com.vedanshu.investmentportfoliotracker.service.TradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * REST Controller exposing endpoints for managing trades and retrieving portfolio metrics.
 *
 * @author Vedanshu Garg
 */
@RestController
@RequestMapping("/api/v1")
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * Endpoint to record a new stock trade (Buy or Sell).
     *
     * @param trade the trade details from the incoming JSON request body
     * @return a ResponseEntity containing the saved trade and a 201 Created status
     */
    @PostMapping("/trades")
    public ResponseEntity<Trade> recordTrade(@RequestBody Trade trade) {
        Trade savedTrade = tradeService.recordTrade(trade);
        return new ResponseEntity<>(savedTrade, HttpStatus.CREATED);
    }

    /**
     * Endpoint to fetch the complete ledger of all recorded trades.
     *
     * @return a ResponseEntity containing a list of all trades and a 200 OK status
     */
    @GetMapping("/trades")
    public ResponseEntity<List<Trade>> getTradeHistory() {
        return ResponseEntity.ok(tradeService.getTradeHistory());
    }

    /**
     * Endpoint to calculate and retrieve the current aggregated holdings for all stocks.
     *
     * @return a ResponseEntity containing a map of ticker symbols to total net shares owned
     */
    @GetMapping("/portfolio/holdings")
    public ResponseEntity<Map<String, BigDecimal>> getPortfolioHoldings() {
        return ResponseEntity.ok(tradeService.getPortfolioHoldings());
    }

    /**
     * Endpoint to calculate the real-time monetary value of the portfolio.
     * Integrates with external market data providers for live pricing.
     *
     * @return a ResponseEntity containing the detailed portfolio valuation
     */
    @GetMapping("/portfolio/valuation")
    public ResponseEntity<PortfolioValuationResponse> getPortfolioValuation() {
        return ResponseEntity.ok(tradeService.getLivePortfolioValuation());
    }
}
