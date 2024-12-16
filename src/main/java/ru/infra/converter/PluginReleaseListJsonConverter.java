package ru.infra.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.infra.api.monitor.model.dto.PluginRelease;

import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class PluginReleaseListJsonConverter implements AttributeConverter<List<PluginRelease>, String>{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<PluginRelease> attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting List<PluginRelease> to JSON", e);
        }
    }

    @Override
    public List<PluginRelease> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        try {
            return objectMapper.readValue(dbData, objectMapper.getTypeFactory().constructCollectionType(List.class, PluginRelease.class));
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON to List<PluginRelease>", e);
        }
    }
}
