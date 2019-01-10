package com.nikola2934.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nikola2934.model.Song;
import java.io.IOException;

public class SongSerializer extends JsonSerializer<Song> {

    @Override
    public void serialize(Song s, JsonGenerator jg, SerializerProvider sp) throws IOException {
        jg.writeStartObject();
//        jg.writeNumberField("id", s.getSong_id());
        jg.writeObjectField("creator", s.getUser().getUsername());
        jg.writeStringField("name", s.getName());
        jg.writeStringField("path", s.getPath_w());
        jg.writeNumberField("likes", s.getLikes().size());
        jg.writeObjectField("upload-date", s.getDate());
        jg.writeBooleanField("liked", s.getLikedByMe());
        jg.writeStringField("about", s.getAbout());
        jg.writeNumberField("price", s.getPrice());
        jg.writeObjectField("genre", s.getGenre().getName());
        jg.writeNumberField("sales", s.getNr_purchases());
        jg.writeNumberField("duration", s.getLength());
        jg.writeEndObject();
    }

}
