package com.example.stockapp2.util;

import com.example.stockapp2.dto.response.TickerResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class TickerResponseSerializer extends JsonSerializer<TickerResponse> {


    @Override
    public void serialize(TickerResponse value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("ticker'", value.getTicker());
        gen.writeStringField("'name'", value.getName());
        gen.writeStringField("'market'", value.getMarket());
        gen.writeStringField("'locale'", value.getLocale());
        gen.writeStringField("primary_exchange", value.getPrimary_exchange());
        gen.writeStringField("type", value.getType());
        gen.writeBooleanField("active", value.isActive());
        gen.writeStringField("currency_name", value.getCurrency_name());
        gen.writeStringField("cik", value.getCik());
        gen.writeStringField("composite_figi", value.getComposite_figi());
        gen.writeStringField("share_class_figi", value.getShare_class_figi());
        gen.writeStringField("last_updated_utc", value.getLast_updated_utc().toString());
        gen.writeEndObject();
    }
}
