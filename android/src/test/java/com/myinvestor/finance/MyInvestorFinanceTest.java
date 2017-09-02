package com.myinvestor.finance;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Unit testing for the finance APIs.
 */

public class MyInvestorFinanceTest {

    private static final String TEST_SYMBOL = "6742.KL";

    @Test
    public void testGetStock() throws IOException {
        Stock stock = YahooFinance.get(TEST_SYMBOL);
        BigDecimal price = stock.getQuote().getPrice();
        assertTrue(price.doubleValue() > 0);
        stock.print();
    }
}
