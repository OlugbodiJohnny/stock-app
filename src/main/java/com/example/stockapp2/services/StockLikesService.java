package com.example.stockapp2.services;

import org.springframework.http.ResponseEntity;

public interface StockLikesService {
    ResponseEntity<?> likeStock(String symbol);

    ResponseEntity<?> getLikesForStock(String symbol);
}
