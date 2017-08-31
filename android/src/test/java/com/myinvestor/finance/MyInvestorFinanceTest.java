package com.myinvestor.finance;

import org.junit.Test;

import java.math.BigDecimal;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Unit testing for the finance APIs.
 */

public class MyInvestorFinanceTest {

    @Test
    public void testGetStock(){
        try {
            Stock stock = YahooFinance.get("INTC");
            BigDecimal price = stock.getQuote().getPrice();
            System.out.println(price);
        } catch (Exception  e) {
            System.out.println(e.getMessage());
        }
    }
}
