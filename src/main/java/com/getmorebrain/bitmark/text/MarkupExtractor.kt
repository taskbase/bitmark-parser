package com.getmorebrain.bitmark.text

interface MarkupExtractor : BitmarktextListener {
    fun markup(): String
}