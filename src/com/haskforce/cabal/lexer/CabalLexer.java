package com.haskforce.cabal.lexer;

import com.intellij.lexer.FlexAdapter;

/**
 * Wraps the generated _CabalLexer to be used for syntax highlighting and parsing.
 */
public class CabalLexer extends FlexAdapter {
    public CabalLexer() {
        super(new _CabalLexer());
    }
}
