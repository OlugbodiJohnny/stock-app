package com.example.stockapp2.services;

import org.springframework.http.ResponseEntity;

public interface StockService {

    ResponseEntity<?> getAllTickers(int limit);

    ResponseEntity<?> getTickerBySymbol(String symbol);
}
