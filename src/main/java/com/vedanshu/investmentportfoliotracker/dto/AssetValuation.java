package com.vedanshu.investmentportfoliotracker.dto;

import java.math.BigDecimal;

/**
 * Represents the current market valuation of a single asset in the portfolio.
 *
 * @author Vedanshu Garg
 */
public record AssetValuation(
        BigDecimal quantityOwned,
        BigDecimal currentPricePerShare,
        BigDecimal totalAssetValue
) {}
