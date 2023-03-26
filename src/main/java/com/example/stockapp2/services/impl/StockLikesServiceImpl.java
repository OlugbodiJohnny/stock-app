package com.example.stockapp2.services.impl;

import com.example.stockapp2.dto.response.HttpResponseDto;
import com.example.stockapp2.exceptions.ApiResourceNotFoundException;
import com.example.stockapp2.models.Stock;
import com.example.stockapp2.repositories.StockRepository;
import com.example.stockapp2.services.StockLikesService;
import com.example.stockapp2.services.StockService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockLikesServiceImpl implements StockLikesService {

    private static final String ENTITY = "LIKE";
    private final StockRepository stockRepository;
    private final StockService stockService;

    @Override
    @SneakyThrows
    public ResponseEntity<?> likeStock(String symbol) {
        Stock stock = stockRepository.findBySymbol(symbol);
        if (stock == null) {
            HttpResponseDto responseDto = (HttpResponseDto) stockService.getTickerBySymbol(symbol).getBody();
            log.info("responseDto :\n {}",responseDto);
//            ObjectMapper mapper = new ObjectMapper();
//            SimpleModule module = new SimpleModule();
//            module.addSerializer(TickerResponse.class, new TickerResponseSerializer());
//            mapper.registerModule(module);
//
//            String json = mapper.writeValueAsString(responseDto.getData().toString()); // serialize to JSON string
//            log.info("json : {}\n",json);
            LinkedHashMap<String,Object> tickerResponse = (LinkedHashMap<String, Object>) responseDto.getData();
//            TickerResponse tickerResponse = mapper.readValue(json,TickerResponse.class);
            if (tickerResponse != null) {
                stock = new Stock();
                stock.setSymbol(symbol);
                stock.setName(tickerResponse.get("name").toString());
                stock.setType(tickerResponse.get("type").toString());
                stock.setCurrency_name(tickerResponse.get("currency_name").toString());
                stock.setMarket(tickerResponse.get("market").toString());
                stock.setLocale(tickerResponse.get("locale").toString());
                stock.setPrimary_exchange(tickerResponse.get("primary_exchange").toString());
                Boolean active = (Boolean) tickerResponse.get("active");
                stock.setActive(active);
                stock.setCik(tickerResponse.get("cik").toString());
                stock.setComposite_figi(tickerResponse.get("share_class_figi").toString());
                String dateString = tickerResponse.get("last_updated_utc").toString();
                Instant instant = Instant.parse(dateString);
                LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Africa/Lagos").getRules().getOffset(instant));
                stock.setLast_updated_utc(dateTime);
                stock.setLikes(1);
            }else {
                throw new ApiResourceNotFoundException("Stock with given symbol/ticker was not found");
            }
        } else {
            stock.setLikes(stock.getLikes() + 1);
        }
        stock = stockRepository.save(stock);
        return new ResponseEntity<>(new HttpResponseDto(HttpStatus.OK,ENTITY,"Stock liked",stock),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getLikesForStock(String symbol) {
        Stock stock = stockRepository.findBySymbol(symbol);
        if (stock == null) {
            return new ResponseEntity<>(new HttpResponseDto(HttpStatus.OK,ENTITY,"Likes fetched",0),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpResponseDto(HttpStatus.OK,ENTITY,"Likes fetched",stock.getLikes()),HttpStatus.OK);
        }
    }
}
