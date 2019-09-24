package com.getmorebrain.bitmark.parser

import javax.script.*;

fun main() {
    JavascriptListener().runJavascript()
}

class JavascriptListener {

    fun runJavascript() {
        val manager = ScriptEngineManager()
        val engine = manager.getEngineByName("javascript");

        engine.eval("var x = 10;");
        engine.eval("var y = 20;");
        engine.eval("var z = x + y;");
        engine.eval("print (z);");
    }
}