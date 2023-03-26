package com.example.stockapp2.repositories;

import com.example.stockapp2.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
    Stock findBySymbol(String symbol);
}
