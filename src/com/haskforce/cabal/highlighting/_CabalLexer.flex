package com.haskforce.cabal.highlighting;

import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import com.haskforce.cabal.psi.CabalTypes;

%%

%{
  public _CabalLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _CabalLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode
%ignorecase
%eof{ return;
%eof}

CRLF= \n|\r|\r\n
WHITE_SPACE=[\ \t\f]
KEY_PATTERN=[A-Za-z0-9\-]+\ *:
END_OF_LINE_COMMENT=--[^\r\n]*[\r\n]+
COLON=:
COMPONENT_TYPE=(executable|library|benchmark|test-suite|flag)
CONDITIONAL=(if [^\n\r]+|else)

%state WAITING_VALUE
%state WAITING_COMPONENT

%%

<YYINITIAL> {END_OF_LINE_COMMENT}                   { yybegin(YYINITIAL); return CabalTypes.COMMENT; }
<YYINITIAL> {COLON} ({CRLF} | {WHITE_SPACE})+       { yybegin(WAITING_VALUE); return CabalTypes.COLON; }
<YYINITIAL> {KEY_PATTERN}                           { yypushback(1); yybegin(YYINITIAL); return CabalTypes.KEY; }
<YYINITIAL> {COMPONENT_TYPE}                        { yybegin(WAITING_COMPONENT); return CabalTypes.COMPONENT_TYPE; }
<YYINITIAL> {CONDITIONAL}                           { yybegin(YYINITIAL); return CabalTypes.CONDITIONAL; }
<YYINITIAL> {WHITE_SPACE}+                          { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
<YYINITIAL> {CRLF}                                  { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

<WAITING_VALUE> {CRLF} {WHITE_SPACE}* ({KEY_PATTERN} | {COMPONENT_TYPE} | {CONDITIONAL})
                                                    { yypushback(yylength()); yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
<WAITING_VALUE> {CRLF} {WHITE_SPACE}*               { return TokenType.WHITE_SPACE; }
<WAITING_VALUE> {END_OF_LINE_COMMENT}               { yybegin(YYINITIAL); return CabalTypes.COMMENT; }
<WAITING_VALUE> [^\r\n]+                            { return CabalTypes.VALUE_LINE; }

<WAITING_COMPONENT> {WHITE_SPACE}+                  { return TokenType.WHITE_SPACE; }
<WAITING_COMPONENT> [^\r\n]+                        { yybegin(YYINITIAL); return CabalTypes.COMPONENT_NAME; }
<WAITING_COMPONENT> {CRLF}                          { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

[^]                                                 { return TokenType.BAD_CHARACTER; }
