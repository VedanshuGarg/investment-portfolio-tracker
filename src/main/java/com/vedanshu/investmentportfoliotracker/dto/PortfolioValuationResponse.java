package com.vedanshu.investmentportfoliotracker.dto;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Represents the complete real-time valuation of the user's portfolio.
 *
 * @author Vedanshu Garg
 */
public record PortfolioValuationResponse(
        Map<String, AssetValuation> assetBreakdown,
        BigDecimal totalPortfolioValue
) {}
