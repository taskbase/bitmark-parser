package com.getmorebrain.bitmark.model

abstract class Bit(
    val type: String,
    @Transient open val format: String = "text",
    @Transient open val instruction: String?,
    @Transient open val hint: String?,
    @Transient open val body: String?,
    @Transient open val image: String?,
    @Transient open val audio: String?,
    @Transient open val article: String?
)