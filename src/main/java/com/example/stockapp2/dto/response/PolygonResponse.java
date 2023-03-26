package com.example.stockapp2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolygonResponse<T> {
    private String status;
    private String request_id;
    private Integer count;
    private List<T> results;
}

