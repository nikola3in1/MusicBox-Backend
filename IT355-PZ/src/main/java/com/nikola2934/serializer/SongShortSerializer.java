package com.nikola2934.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nikola2934.model.Song;
import java.io.IOException;

public class SongShortSerializer extends JsonSerializer<Song>{

    @Override
    public void serialize(Song s, JsonGenerator jg, SerializerProvider sp) throws IOException {
        jg.writeStartObject();
//        jg.writeNumberField("id", s.getSong_id());
        jg.writeStringField("name", s.getName());
//        jg.writeNumberField("length", s.getLength());
//        jg.writeNumberField("price", s.getPrice());
//        jg.writeNumberField("purchase", s.getNr_purchases());
//        jg.writeStringField("about", s.getAbout());
        jg.writeStringField("path", s.getPath_w());
        jg.writeObjectField("upload-date", s.getDate());
//        jg.writeObjectField("genre", s.getGenre().getName());
        jg.writeObjectField("creator", s.getUser().getUsername());
        jg.writeBooleanField("liked", s.getLikedByMe());
        jg.writeNumberField("likes",s.getLikes().size());
        jg.writeEndObject();
    }
    
}
