package com.getmorebrain.bitmark.model

data class MarkBit(
    override val format: String = "text",
    override val instruction: String? = null,
    override val hint: String? = null,
    override val image: String? = null,
    override val audio: String? = null,
    override val article: String? = null,
    override val body: String? = null,
    val marks: Map<String, Mark> = emptyMap()
) : Bit(
    type = BitType.MARK.typeId,
    format = format,
    instruction = instruction,
    hint = hint,
    body = body,
    image = image,
    audio = audio,
    article = article
) {

    data class Mark(
        val text: String,
        val mark: String? = null,
        val instruction: String? = null,
        val hint: String? = null,
        val isExample: Boolean = false
    )
}
