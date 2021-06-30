import java_cup.runtime.Symbol;

%%
%unicode
%line
%column
%cup
%{
StringBuffer string;
%}

/* main character classes */
LineTerminator = \r|\n|\r\n

WhiteSpace = \t|\x20

/* identifiers */
Id = [a-zA-Z_][a-zA-Z0-9_]*

/* integer literals */
Int_const = 0|[1-9]+[0-9]*
Float_const = (0|[1-9]+)[1-9]*[0-9]*\.[0-9]+
//String_const = \"(.)*\"
Comments = \/\*([^*]|[\r\n]|(\*+([^*/]|[\r\n])))*\*\/
CommentsInc= \/\*

%state STRING
%%

<YYINITIAL> {

   \"                             {string = new StringBuffer(); yybegin(STRING);}
  "int"                           { System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.INT,yytext());}
  "float"                         { System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.FLOAT,yytext() );}
  "if"                            {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.IF,yytext());}
   "fi"                           { System.out.println(yytext() + " LINEA: " + (yyline + 1));return new Symbol(sym.FI,yytext());}
  "else"                          {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.ELSE,yytext());}
  "then"                          {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.THEN,yytext());}
  "while"                         {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.WHILE,yytext());}
   "do"                           {System.out.println(yytext() + " LINEA: " + (yyline + 1));return new Symbol(sym.DO,yytext());}
    "od"                          {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.OD,yytext());}
     "readln"                     {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.READ,yytext());}
      "write"                     {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.WRITE,yytext());}
       "null"                     {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.NULL,yytext()); }
        "true"                    {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.TRUE,yytext());}
         "false"                  {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.FALSE,yytext()); }
          "elif"                  {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.ELIF,yytext()); }
           "void"                 {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.VOID,yytext());}
            "corp"                {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.CORP,yytext());}
             "proc"               {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.PROC,yytext()); }
              "bool"              {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.BOOL,yytext());  }
               "string"           {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.STRING,yytext()); }

  /* separators */
  "("                            { return new Symbol(sym.LPAR,yytext());}
  ")"                            { return new Symbol(sym.RPAR,yytext());}
  ";"                            { return new Symbol(sym.SEMI,yytext());}
  ","                            { return new Symbol(sym.COMMA,yytext());}
  ":"                            { return new Symbol(sym.COLON,yytext());}

  /* operators */
  "="                            { return new Symbol(sym.EQ,yytext());  }
  ">"                            { return new Symbol(sym.GT,yytext());  }
  "<"                            { return new Symbol(sym.LT,yytext());  }
  "<>"                           { return new Symbol(sym.NE,yytext());  }
  "<="                           { return new Symbol(sym.LE,yytext());  }
  ">="                           { return new Symbol(sym.GE,yytext());  }
  ":="                           { return new Symbol(sym.ASSIGN,yytext()); }
  "->"                            { return new Symbol(sym.RETURN,yytext()); }

  "+"                            { return new Symbol(sym.PLUS,yytext());  }
  "-"                            { return new Symbol(sym.MINUS,yytext());  }
  "*"                            { return new Symbol(sym.TIMES,yytext());  }
  "/"                            { return new Symbol(sym.DIV,yytext());  }
  "&&"                            { return new Symbol(sym.AND,yytext());  }
  "||"                            { return new Symbol(sym.OR,yytext());  }
  "!"                            { return new Symbol(sym.NOT,yytext());  }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
  {LineTerminator}                  {}

  /* identifiers */
  {Id}                   {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.ID,yytext());  }
   {Float_const}                   {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.FLOAT_CONST,yytext());  }
    {Int_const}                   {System.out.println(yytext() + " LINEA: " + (yyline + 1)); return new Symbol(sym.INT_CONST,yytext());  }


    {Comments}                       {}
    {CommentsInc}                    {throw new Error("Commento non terminato"+" "+"linea"+" "+(yyline+1));}
}

<STRING> {
    \" { yybegin(YYINITIAL); System.out.println(string.toString()); return new Symbol(sym.STRING_CONST, string.toString()); }
    [^\n\r\"\\]+ { string.append( yytext() ); }
    \\t { string.append("\\t"); }
    \\n { string.append("\\n"); }
    \\r { string.append("\\r"); }
    \\\" { string.append("\""); }
    \\ { string.append("\\"); }
      <<EOF>> {throw new Error("Stringa non terminata"+" "+"linea"+" "+(yyline+1));}
    }
<<EOF>>                          { return new Symbol(sym.EOF,yytext());  }
