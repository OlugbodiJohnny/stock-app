package com.example.stockapp2.controllers;

import com.example.stockapp2.services.StockLikesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks-likes")
@PreAuthorize("hasAnyRole('ROLE_USER')")
@Slf4j
public class StockLikesController {
    private final StockLikesService stockLikesService;

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/like-stock")
    public ResponseEntity<?> likeStock (@RequestParam() String symbol) {
        return stockLikesService.likeStock(symbol);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/get-likes-for-stock")
    public ResponseEntity<?> getLikesForStock (@RequestParam() String symbol) {
        return stockLikesService.getLikesForStock(symbol);
    }
}
