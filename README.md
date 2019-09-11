# Bitmark Parser

This project contains the ANTLR specification for the Bitmark parser.
Bitmark is a markup language that follows the content-first principle.

## Building
This project is using the [ANTLR gradle plugin](https://docs.gradle.org/current/userguide/antlr_plugin.html).

Type the following to generate the Java source of the parser:
```
./gradlew generateGrammarSource
```

