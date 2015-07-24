package com.haskforce.cabal.psi;

import com.intellij.psi.tree.IElementType;

public interface CabalTypes {
    IElementType COMMENT = new CabalTokenType("COMMENT");
    IElementType KEY_VALUE_PAIR = new CabalElementType("KEY_VALUE_PAIR");
    IElementType KEY = new CabalElementType("KEY");
    IElementType COLON = new CabalElementType("COLON");
    IElementType VALUE_LINE = new CabalTokenType("VALUE_LINE");
    IElementType VALUE = new CabalElementType("VALUE");
    IElementType COMPONENT = new CabalElementType("COMPONENT");
    IElementType COMPONENT_TYPE = new CabalTokenType("COMPONENT_TYPE");
    IElementType COMPONENT_NAME = new CabalTokenType("COMPONENT_NAME");
    IElementType CONDITIONAL = new CabalElementType("CONDITIONAL");
}
