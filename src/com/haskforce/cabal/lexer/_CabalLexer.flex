package com.haskforce.cabal.lexer;

import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static com.haskforce.cabal.psi.CabalTypes.*;

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
ALPHA=[A-Za-z]
NUMBER=[0-9]
KEY_PATTERN=[A-Za-z0-9\-]+\ *:
END_OF_LINE_COMMENT=[\ \t\f]*--[^\r\n]*
COLON=:
COMPONENT_TYPE=(executable|library|benchmark|test-suite|flag)
CONDITIONAL=(if [^\n\r]+|else)

%state WAITING_VALUE
%state WAITING_COMPONENT

%%

<YYINITIAL> {
  {END_OF_LINE_COMMENT}                   { yybegin(YYINITIAL); return COMMENT; }
  {COLON}                                 { yybegin(WAITING_VALUE); return COLON; }
  {KEY_PATTERN}                           { yypushback(1); yybegin(YYINITIAL); return KEY; }
  {COMPONENT_TYPE}                        { yybegin(WAITING_COMPONENT); return COMPONENT_TYPE; }
  {CONDITIONAL}                           { yybegin(YYINITIAL); return CONDITIONAL; }
  {WHITE_SPACE}+                          { yybegin(YYINITIAL); return INDENT; }
  {CRLF}                                  { yybegin(YYINITIAL); return NEWLINE; }
}

<WAITING_VALUE> {
  {CRLF} {WHITE_SPACE}* ({KEY_PATTERN} | {COMPONENT_TYPE} | {CONDITIONAL})
                                          { yypushback(yylength()); yybegin(YYINITIAL); return EMPTY; }
  {CRLF}                                  { return NEWLINE; }
  {WHITE_SPACE}+                          { return WHITE_SPACE; }
  {END_OF_LINE_COMMENT}                   { return COMMENT; }
  {NUMBER}+                               { return NUMBER; }
  ({ALPHA} | {NUMBER})+                   { return VALUE_WORD; }
  "."                                     { return DOT; }
  ","                                     { return COMMA; }
  "<"                                     { return LT; }
  "<="                                    { return LTE; }
  ">"                                     { return GT; }
  ">="                                    { return GTE; }
  "=="                                    { return EQ; }
  "&&"                                    { return AND; }
  "||"                                    { return OR; }
  [^]                                     { return VALUE_WORD; }
}

<WAITING_COMPONENT> {
  {WHITE_SPACE}+                          { return WHITE_SPACE; }
  [^\ \t\f\r\n]+                          { yybegin(YYINITIAL); return COMPONENT_NAME; }
  {CRLF}                                  { yybegin(YYINITIAL); return NEWLINE; }
}

[^]                                       { return BAD_CHARACTER; }
