package com.pubcafe.core.converter

import com.pubcafe.core.domain.common.LanguageCode
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class LanguageCodeListConverter : AttributeConverter<List<LanguageCode>, String> {

    private val separator: String = ","

    override fun convertToDatabaseColumn(attribute: List<LanguageCode>?): String? {
        return attribute?.joinToString(separator) { it.name }
    }

    override fun convertToEntityAttribute(dbData: String?): List<LanguageCode> {
        return dbData
            ?.split(separator)
            ?.map { LanguageCode.valueOf(it) }
            ?: emptyList()
    }
}