package com.esfimus.model.dto

import com.google.gson.annotations.SerializedName

class TranslationDto(
    @field:SerializedName("text") val translation: String?
)
