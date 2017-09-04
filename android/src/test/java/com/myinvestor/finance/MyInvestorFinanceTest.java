package com.myinvestor.finance;

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

import static org.junit.Assert.assertTrue;

/**
 * Unit testing for the finance APIs.
 */

public class MyInvestorFinanceTest {

  private static final String TEST_SYMBOL = "6742.KL";

  public void testGetStock() throws IOException {
    Stock stock = YahooFinance.get(TEST_SYMBOL);
    StockQuote quote = stock.getQuote(true);
    BigDecimal price = stock.getQuote().getPrice();
    BigDecimal change = stock.getQuote().getChangeInPercent();
    BigDecimal peg = stock.getStats().getPeg();
    assertTrue(price.doubleValue() > 0);
    stock.print();
  }

  @Test
  public void testGetStocks() throws IOException {
    Map<String, Stock> stocks = YahooFinance.get(new String[]{"AAPL", "6742.KL"});
    for (String key : stocks.keySet()) {
      Stock stock = stocks.get(key);
      stock.print();
    }
  }
}
