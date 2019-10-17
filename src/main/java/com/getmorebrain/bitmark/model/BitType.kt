package com.getmorebrain.bitmark.model

enum class BitType(val typeId: String, val className: String) {
    CLOZE("cloze", ClozeBit::class.java.name)
}
