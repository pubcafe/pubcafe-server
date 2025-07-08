package com.pubcafe.core.converter

import com.pubcafe.core.domain.common.LanguageCode
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class LinkListConverter : AttributeConverter<List<LanguageCode>, String> {

    override fun convertToDatabaseColumn(attribute: List<LanguageCode>?): String? {
        return attribute?.joinToString(",") { it.name }
    }

    override fun convertToEntityAttribute(dbData: String?): List<LanguageCode> {
        return dbData?.split(",")?.mapNotNull {
            runCatching { LanguageCode.valueOf(it.trim()) }.getOrNull()
        } ?: emptyList()
    }
}