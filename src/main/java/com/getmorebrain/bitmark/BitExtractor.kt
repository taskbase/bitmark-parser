package com.getmorebrain.bitmark

import com.getmorebrain.bitmark.model.Bit

interface BitExtractor : BitmarkListener {

    fun bits(): List<Bit>

}