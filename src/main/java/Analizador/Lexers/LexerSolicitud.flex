package Analizador;
import java_cup.runtime.Symbol;
%%
%class LexerSolicitud
%type java_cup.runtime.Symbol
%cup
%unicode
%line
%column
%char
%public
D= [0-9]+
espacio=[\r]+
DEC = [0-9]+\.[0-9]+
ID = [a-zA-Z$_][a-zA-Z0-9$_]*
NOTA = ([Dd][Oo]|[Rr][Ee]|[Mm][Ii]|[Ff][Aa]|[Ll][Aa]|[Ss][Oo][Ll]|[Ss][Ii])
SOSTENIDA = [#]?
esp = [" "]+
tab = [\t]+
sal = [\n]+
%state STRING
%{
    String str = "";
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn, yytext());
    }
%}
%%
/*Palabras reservadas*/
<YYINITIAL> {
    ("Solicitud"|"SOLICITUD"|"solicitud")               { return symbol(symSolicitud.SOLICITUD); }
    ("Tipo"|"TIPO"|"tipo")                              { return symbol(symSolicitud.TIPO); }
    ("Lista"|"LISTA"|"lista")                           { return symbol(symSolicitud.LISTA); }
    ("Pista"|"PISTA"|"pista")                           { return symbol(symSolicitud.PISTA); }
    ("Pistanueva"|"PISTANUEVA"|"pistanueva")            { return symbol(symSolicitud.PISTANUEVA); }
    ("sin"|"Sin"|"SIN")                                 { return symbol(symSolicitud.SIN); }
    ("datos"|"DATOS"|"Datos")                           { return symbol(symSolicitud.DATOS); }
    ("canal"|"CANAL"|"Canal")                           { return symbol(symSolicitud.CANAL); }
    ("nota"|"NOTA"|"Nota")                              { return symbol(symSolicitud.NOTA); }
    ("duracion"|"DURACION"|"Duracion")                  { return symbol(symSolicitud.DURACION); }
    ("nombre"|"NOMBRE"|"Nombre")                        { return symbol(symSolicitud.NOMBRE); }
    ("octava"|"OCTAVA"|"Octava")                        { return symbol(symSolicitud.OCTAVA); }
    ({NOTA}{SOSTENIDA})                                 { return symbol(symSolicitud.NOTAS); }
    ({D})                                               { return symbol(symSolicitud.NUMERO); }
    ("<")                                               { return symbol(symSolicitud.MENOR_Q); }
    ("</")                                               { return symbol(symSolicitud.FIN); }
    (">")                                               { return symbol(symSolicitud.MAYOR_Q); }
    "\""                                                { str = ""; yybegin(STRING);}
    {ID}                                                { return symbol(symSolicitud.ID); }
}

<STRING>{
    \"                              { yybegin(YYINITIAL);
                                       return symbol(symSolicitud.STRING,
                                       str); }
    [^\"]+                      { str+=yytext(); }
}
{tab} {/*ignore*/}
{esp} {/*ignore*/}
{espacio}           {/*Ignore*/}
{sal}           {/*Ignore*/}
[^]                           { return new Symbol(sym.ERRORLEX,yycolumn,yyline,yytext()); }