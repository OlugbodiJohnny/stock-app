package com.example.stockapp2.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerResponse {
    private String ticker;
    private String name;
    private String type;
    public String currency_name;
    private String market;
    private String locale;
    private String primary_exchange;
    private boolean active;
    private String cik;
    private String composite_figi;
    private String share_class_figi;
    private LocalDateTime last_updated_utc;

//    @Override
//    public String toString() {
//        return String.format(
//                "{\""ticker\":\"%s\", \"name\":\"%s\", \"market\":\"%s\", \"locale\":\"%s\", \"primary_exchange\":\"%s\", \"type\":\"%s\", \"active\":%s, \"currency_name\":\"%s\", \"cik\":\"%s\", \"composite_figi\":\"%s\", \"share_class_figi\":\"%s\", \"lastUpdatedUtc\":\"%s\"}",
//                ticker, name, market, locale, primary_exchange, type, active, currency_name, cik, composite_figi, share_class_figi, last_updated_utc);
//    }
}
