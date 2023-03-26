package com.example.stockapp2.services.impl;

import com.example.stockapp2.dto.response.HttpResponseDto;
import com.example.stockapp2.dto.response.PolygonResponse;
import com.example.stockapp2.dto.response.TickerResponse;
import com.example.stockapp2.exceptions.ApiBadRequestException;
import com.example.stockapp2.exceptions.ApiResourceNotFoundException;
import com.example.stockapp2.services.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockServiceImpl implements StockService {

    private static final String ENTITY = "STOCK";
    private final RestTemplate restTemplate;

    @Value("${polygon.apiKey}")
    private String apiKey;

    @Value("${polygon.baseUrl}")
    private String baseUrl;

    @Override
    public ResponseEntity<?> getAllTickers(int limit) {
        String url = baseUrl + "?market=stocks&active=true&sort=ticker&order=asc&limit=" + limit + "&apiKey=" + apiKey;
        try {
            PolygonResponse<TickerResponse> response = restTemplate.getForObject(url, PolygonResponse.class);
            return new ResponseEntity<>(new HttpResponseDto(HttpStatus.OK,ENTITY,"Stocks fetched",response.getResults()),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiBadRequestException("Error retrieving stocks");
        }
    }

    @Override
    public ResponseEntity<?> getTickerBySymbol(String symbol) {
        String url = baseUrl + "?market=stocks&active=true&sort=ticker&order=asc&ticker=" + symbol + "&apiKey=" + apiKey;
        try {
            PolygonResponse<TickerResponse> response = restTemplate.getForObject(url, PolygonResponse.class);
            return new ResponseEntity<>(new HttpResponseDto(HttpStatus.OK,ENTITY,"Stocks fetched",response.getResults().get(0)),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiResourceNotFoundException("Stock not found");
        }
    }
}
