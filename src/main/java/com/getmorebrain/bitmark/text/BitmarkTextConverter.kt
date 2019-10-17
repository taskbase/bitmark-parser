package com.getmorebrain.bitmark.text

interface BitmarkTextConverter {
    fun convert(bitmarkText: String): String
}

class DefaultBitmarkTextConverter : BitmarkTextConverter {
    override fun convert(bitmarkText: String): String = bitmarkText
}
