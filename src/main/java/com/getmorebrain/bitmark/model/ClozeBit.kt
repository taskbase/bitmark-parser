package com.getmorebrain.bitmark.model

open class Bit(
        val type: String
)

data class ClozeBit(
        val format: String = "text",
        val image: String? = null,
        val audio: String? = null,
        val article: String? = null,
        val item: String? = null,
        val instruction: String? = null,
        val hint: String? = null,
        val body: String? = null,
        val gaps: Map<String, ClozeGap> = emptyMap()) : Bit(type = "cloze") {

    data class ClozeGap(
            val solutions: List<String> = emptyList(),
            val instruction: String? = null,
            val hint: String? = null,
            val isExample: Boolean = false,
            val example: String? = null
    )
}

/*
{
    "bitmark": "This sentence is a [_cloze][_gap text][!noun] with [_2][?1 or 2] gaps including an instruction for the first and a hint for the second gap.",
    "bit": {
        "type": "cloze",
        "format": "text",
        "item": "",
        "instruction": "",
        "hint": "",
        "body": "This sentence is a {0} with {1} gaps including an instruction for the first and a hint for the second gap.",
        "gaps": {
            "{0}": {
                "solutions":["cloze", "gap text"],
                "instruction": "noun",
                "isExample": false,
                "example": ""
            },
            "{1}": {
                "solutions":["2"],
                "hint":"1 or 2",
                "isExample": false,
                "example": ""
            }
        }
    },
    ...
}
 */