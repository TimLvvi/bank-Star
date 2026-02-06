package org.skypro.bank_Star.entity;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.List;

public class RuleArgumentsConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        return String.join(";", strings);
    }

    public List<String> convertToEntityAttribute(String s) {
        return Arrays.asList(s.split(";"));
    }
}