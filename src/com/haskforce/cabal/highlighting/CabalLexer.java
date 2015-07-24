package com.haskforce.cabal.highlighting;

import com.intellij.lexer.FlexAdapter;

/**
 * Created by crobbins on 8/2/14.
 */
public class CabalLexer extends FlexAdapter {
    public CabalLexer() {
        super(new _CabalLexer());
    }
}
