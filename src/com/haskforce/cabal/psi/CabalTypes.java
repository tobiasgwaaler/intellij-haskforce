package com.haskforce.cabal.psi;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

public interface CabalTypes {
    IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
    IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;

    CabalTokenType EMPTY = new CabalTokenType("EMPTY");
    CabalTokenType INDENT = new CabalTokenType("INDENT");
    CabalTokenType NEWLINE = new CabalTokenType("NEWLINE");

    CabalTokenType COMMENT = new CabalTokenType("COMMENT");
    CabalTokenType VALUE_WORD = new CabalTokenType("VALUE_WORD");
    CabalTokenType NUMBER = new CabalTokenType("NUMBER");
    CabalTokenType COLON = new CabalTokenType(":");
    CabalTokenType DOT = new CabalTokenType(".");
    CabalTokenType COMMA = new CabalTokenType(",");
    CabalTokenType LT = new CabalTokenType("<");
    CabalTokenType LTE = new CabalTokenType("<=");
    CabalTokenType GT = new CabalTokenType(">");
    CabalTokenType GTE = new CabalTokenType(">=");
    CabalTokenType EQ = new CabalTokenType("==");
    CabalTokenType AND = new CabalTokenType("&&");
    CabalTokenType OR = new CabalTokenType("||");
    CabalTokenType NOT = new CabalTokenType("!");

    CabalElementType KEY = new CabalElementType("KEY");
    CabalElementType VALUE = new CabalElementType("VALUE");
    CabalElementType COMPONENT = new CabalElementType("COMPONENT");
    CabalElementType COMPONENT_TYPE = new CabalElementType("COMPONENT_TYPE");
    CabalElementType COMPONENT_NAME = new CabalElementType("COMPONENT_NAME");
    CabalElementType COMPONENT_BODY = new CabalElementType("COMPONENT_BODY");
    CabalElementType CONDITIONAL = new CabalElementType("CONDITIONAL");

    CabalFieldType PACKAGE_NAME = new CabalFieldType("PACKAGE_NAME");
    CabalFieldType PACKAGE_VERSION = new CabalFieldType("PACKAGE_VERSION");
    CabalFieldType CUSTOM_FIELD = new CabalFieldType("CUSTOM_FIELD");

    CabalValueType FREEFORM = new CabalValueType("FREEFORM");
    CabalValueType NUMBERS = new CabalValueType("NUMBERS");
}
