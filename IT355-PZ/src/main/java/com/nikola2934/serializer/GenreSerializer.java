package com.nikola2934.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nikola2934.model.Genre;
import java.io.IOException;

public class GenreSerializer extends JsonSerializer<Genre>{

    @Override
    public void serialize(Genre t, JsonGenerator jg, SerializerProvider sp) throws IOException {
//        jg.writeStartOb();
        jg.writeString(t.getName());
//        jg.writeStringField("about", t.getAbout());
//        jg.writeEndArray();
    }

}
