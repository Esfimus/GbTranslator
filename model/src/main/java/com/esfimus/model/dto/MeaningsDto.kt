package com.esfimus.model.dto

import com.google.gson.annotations.SerializedName

class MeaningsDto(
    @field:SerializedName("translation") val translationDto: TranslationDto?,
    @field:SerializedName("imageUrl") val imageUrl: String?
)