package com.example.stockapp2.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    private String symbol;
    private String name;
    private String type;
    private String currency_name;
    private String market;
    private String locale;
    private String primary_exchange;
    private boolean active;
    private String cik;
    private String composite_figi;
    private String share_class_figi;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime last_updated_utc;
    private int likes;

    // getters and setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Stock stock = (Stock) o;
        return symbol != null && Objects.equals(symbol, stock.symbol);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

