package com.getmorebrain.bitmark.model

data class ClozeBit(
    override val format: String = "text",
    override val instruction: String? = null,
    override val hint: String? = null,
    override val image: String? = null,
    override val audio: String? = null,
    override val article: String? = null,
    override val body: String? = null,
    val item: String? = null,
    val gaps: Map<String, ClozeGap> = emptyMap()
) : Bit(
    type = BitType.CLOZE.typeId,
    format = format,
    instruction = instruction,
    hint = hint,
    body = body,
    image = image,
    audio = audio,
    article = article
) {

    data class ClozeGap(
        val solutions: List<String> = emptyList(),
        val instruction: String? = null,
        val hint: String? = null,
        val isExample: Boolean = false,
        val example: String? = null
    )
}
