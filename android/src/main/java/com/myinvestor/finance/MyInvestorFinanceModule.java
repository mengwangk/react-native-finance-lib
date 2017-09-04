package com.myinvestor.finance;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

import static com.myinvestor.finance.LibConfig.ERROR_PRICE_INFO;
import static com.myinvestor.finance.LibConfig.FIELD_CHANGE_IN_PERCENT;
import static com.myinvestor.finance.LibConfig.FIELD_NAME;
import static com.myinvestor.finance.LibConfig.FIELD_PRICE;
import static com.myinvestor.finance.LibConfig.FIELD_SYMBOL;

public class MyInvestorFinanceModule extends ReactContextBaseJavaModule {

  private static final String MODULE_NAME = "MyInvestorFinance";

  MyInvestorFinanceModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return MODULE_NAME;
  }


  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put(ERROR_PRICE_INFO, ERROR_PRICE_INFO);
    constants.put(FIELD_NAME, FIELD_NAME);
    constants.put(FIELD_SYMBOL, FIELD_SYMBOL);
    constants.put(FIELD_PRICE, FIELD_PRICE);
    constants.put(FIELD_CHANGE_IN_PERCENT, FIELD_CHANGE_IN_PERCENT);
    return constants;
  }

  @ReactMethod
  public void getStockPrice(String symbol, Promise promise) {
    try {
      final Stock stock = YahooFinance.get(symbol);
      if (stock != null) {
        StockQuote quote = stock.getQuote();
        WritableMap priceInfo = Arguments.createMap();
        priceInfo.putString(FIELD_NAME, stock.getName());
        priceInfo.putString(FIELD_SYMBOL, symbol);
        priceInfo.putDouble(FIELD_PRICE, quote.getPrice().doubleValue());
        priceInfo.putDouble(FIELD_CHANGE_IN_PERCENT, quote.getChangeInPercent().doubleValue());
        promise.resolve(priceInfo);
      } else {
        promise.reject(ERROR_PRICE_INFO, "Stock not found");
      }
    } catch (IOException ioEx) {
      promise.reject(ERROR_PRICE_INFO, ioEx);
    }
  }

  @ReactMethod
  public void getStockPrice(ReadableArray symbols, Promise promise) {
    try {
      final Map<String, Stock> stocks = YahooFinance.get(symbols.toArrayList().toArray(new String[0]));
      if (!stocks.isEmpty()) {
        WritableMap results  = Arguments.createMap();
        for (String key : stocks.keySet()) {
          Stock stock = stocks.get(key);
          StockQuote quote = stock.getQuote();
          WritableMap priceInfo = Arguments.createMap();
          priceInfo.putString(FIELD_NAME, stock.getName());
          priceInfo.putString(FIELD_SYMBOL, stock.getSymbol());
          priceInfo.putDouble(FIELD_PRICE, quote.getPrice().doubleValue());
          priceInfo.putDouble(FIELD_CHANGE_IN_PERCENT, quote.getChangeInPercent().doubleValue());
          results.putMap(stock.getSymbol(), priceInfo);
        }
        promise.resolve(results);
      } else {
        promise.reject(ERROR_PRICE_INFO, "Stock not found");
      }
    } catch (IOException ioEx) {
      promise.reject(ERROR_PRICE_INFO, ioEx);
    }
  }

}
