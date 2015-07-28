package com.haskforce.cabal.psi;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

public interface CabalTypes {
    IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
    IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;

    IElementType INDENT = new CabalTokenType("INDENT");
    IElementType NEWLINE = new CabalTokenType("NEWLINE");

    IElementType COMMENT = new CabalTokenType("COMMENT");
    IElementType COLON = new CabalTokenType(":");
    IElementType VALUE_LINE = new CabalTokenType("VALUE_LINE");
    IElementType COMPONENT_TYPE = new CabalTokenType("COMPONENT_TYPE");
    IElementType COMPONENT_NAME = new CabalTokenType("COMPONENT_NAME");

    IElementType KEY_VALUE_PAIR = new CabalElementType("KEY_VALUE_PAIR");
    IElementType KEY = new CabalElementType("KEY");
    IElementType VALUE = new CabalElementType("VALUE");
    IElementType COMPONENT = new CabalElementType("COMPONENT");
    IElementType CONDITIONAL = new CabalElementType("CONDITIONAL");
}
