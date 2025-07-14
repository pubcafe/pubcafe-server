package com.pubcafe.core.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class LinkListConverter : AttributeConverter<List<String>, String> {

    private val separator: String = ","

    override fun convertToDatabaseColumn(attribute: List<String>?): String? {
        return attribute?.joinToString(separator)
    }

    override fun convertToEntityAttribute(dbData: String?): List<String> {
        return dbData
            ?.split(separator)
            ?.filter { it.isNotBlank() }
            ?: emptyList()
    }
}