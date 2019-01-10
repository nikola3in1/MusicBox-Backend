package com.nikola2934.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nikola2934.model.Report;
import java.io.IOException;

public class ReportSerializer extends JsonSerializer<Report> {

    @Override
    public void serialize(Report t, JsonGenerator jg, SerializerProvider sp) throws IOException {
        jg.writeStartObject();
        jg.writeNumberField("id", t.getReport_id());
        jg.writeStringField("reason", t.getReason());
        jg.writeStringField("creator", t.getReportedSong().getUser().getUsername());
        jg.writeStringField("song", t.getReportedSong().getName());
        jg.writeStringField("reportedBy",t.getReporter().getUsername());
        jg.writeEndObject();
    }

}
