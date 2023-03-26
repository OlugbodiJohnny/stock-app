package com.example.stockapp2.controllers;

import com.example.stockapp2.services.StockService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
@PreAuthorize("hasAnyRole('ROLE_USER')")
@Slf4j
public class StockController {
    private final StockService stockService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/all")
    public ResponseEntity<?> getAllTickers (@RequestParam(defaultValue = "10")int limit) {
        return stockService.getAllTickers(limit);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/get-by-symbol")
    public ResponseEntity<?> getTickerBySymbol (@RequestParam() String symbol) {
        return stockService.getTickerBySymbol(symbol);
    }
}
