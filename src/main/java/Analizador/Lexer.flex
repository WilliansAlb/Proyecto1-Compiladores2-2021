package Analizador;
import java_cup.runtime.Symbol;
%%
%class Lexer
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
esp = [" "]+
tab = [\t]*
sal = [\n]+
%state STRING, CHAR, COMMENT_L, COMMENT_L2, COMMENT_M, SALTO
%{
    StringBuffer string = new StringBuffer();
    int indentados = 0;
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn, yytext());
    }
    public Symbol indent(String analizar, boolean tieneTab, boolean esEOF) {
        if (!esEOF) {
            if (tieneTab) {
                int espacios = 0;
                for (int i = 0; i < analizar.length(); i++) {
                    if (analizar.charAt(i) == '\t') {
                        espacios++;
                    }
                }
                if ((indentados - espacios) > 0) {
                    indentados--;
                    yypushback(espacios);
                    System.out.println("Se encuentra dedentado no1");
                    return new Symbol(sym.DEDENT, yyline, yycolumn, yytext());
                } else if ((indentados - espacios)==0) {
                    yybegin(YYINITIAL);
                    return null;
                } else if ((indentados - espacios) < 0) {
                    indentados++;
                    yypushback(espacios);
                    System.out.println("Se encuentra indentado");
                    return new Symbol(sym.INDENT, yyline, yycolumn, yytext());
                } else {
                    System.out.println("No sé como entró acá");
                    return null;
                }
            } else {
                if (indentados > 0) {
                    indentados--;
                    yypushback(1);
                    System.out.println("Se encuentra dedentado no2");
                    return new Symbol(sym.DEDENT, yyline, yycolumn, yytext());
                } else {
                    yybegin(YYINITIAL);
                    yypushback(1);
                    return null;
                }
            }
        } else {
            if (indentados > 0) {
                indentados--;
                System.out.println("Se encuentra dedentado no3");
                return new Symbol(sym.DEDENT, yyline, yycolumn, yytext());
            } else {
                return new java_cup.runtime.Symbol(sym.EOF);
            }
        }
    }
%}
%%
/*Palabras reservadas*/
<YYINITIAL> {
    ("Pista"|"PISTA"|"pista")               { return symbol(sym.PISTA); }
    ("Extiende"|"EXTIENDE"|"extiende")      { return symbol(sym.EXT); }
    ("Entero"|"ENTERO"|"entero")            { return symbol(sym.ENTERO); }
    ("Doble"|"DOBLE"|"doble")               { return symbol(sym.DECIMAL); }
    ("Boolean"|"BOOLEAN"|"boolean")         { return symbol(sym.BOOLEAN); }
    ("Caracter"|"CARACTER"|"caracter")      { return symbol(sym.CHAR); }
    ("Cadena"|"CADENA"|"cadena")            { return symbol(sym.CADENA); }
    ("Verdadero"|"VERDADERO"|"verdadero"|"TRUE"|"True"|"true")  { return symbol(sym.TRUE); }
    ("Falso"|"FALSO"|"falso"|"FALSE"|"False"|"false")           { return symbol(sym.FALSE); }
    ("Keep"|"KEEP"|"keep")                  { return symbol(sym.KEEP); }
    ("Var"|"VAR"|"var")                     { return symbol(sym.VAR); }
    ("Si"|"SI"|"si")                        { return symbol(sym.SI); }
    ("Sino"|"SINO"|"sino")                  { return symbol(sym.SINO); }
    ("Switch"|"SWITCH"|"switch")            { return symbol(sym.SWITCH); }
    ("Caso"|"CASO"|"caso")                  { return symbol(sym.CASO); }
    ("Salir"|"SALIR"|"salir")               { return symbol(sym.SALIR); }
    ("Continuar"|"CONTINUAR"|"continuar")   { return symbol(sym.CONT); }
    ("Default"|"DEFAULT"|"default")         { return symbol(sym.DEFAULT); }
    ("Para"|"PARA"|"para")                  { return symbol(sym.PARA); }
    ("Mientras"|"MIENTRAS"|"mientras")      { return symbol(sym.MIENTRAS); }
    ("HACER"|"Hacer"|"hacer")               { return symbol(sym.HACER); }
    ("RETORNA"|"Retorna"|"retorna")                             { return symbol(sym.RETORNA); }
    ("REPRODUCIR"|"Reproducir"|"reproducir")                    { return symbol(sym.REPRODUCIR); }
    ("do"|"do#"|"re"|"re#"|"mi"|"mi#"|"fa"|"fa#"|"sol"|"sol#"|"la"|"la#"|"si")  { return symbol(sym.NOTAS); }
    ("ESPERAR"|"Esperar"|"esperar")         { return symbol(sym.ESPERAR); }
    ("ORDENAR"|"Ordenar"|"ordenar")         { return symbol(sym.ORDENAR); }
    ("ASCENDENTE"|"Ascendente"|"ascendente")                    { return symbol(sym.ASC); }
    ("DESCENDENTE"|"Descendente"|"descendente")                 { return symbol(sym.DESC); }
    ("PARES"|"Pares"|"pares")               { return symbol(sym.PAR); }
    ("IMPARES"|"Impares"|"impares")         { return symbol(sym.IMPAR); }
    ("SUMARIZAR"|"Sumarizar"|"sumarizar")   { return symbol(sym.SUMARIZAR); }
    ("LONGITUD"|"Longitud"|"longitud")      { return symbol(sym.LONG); }
    ("MENSAJE"|"Mensaje"|"mensaje")         { return symbol(sym.MSG); }
    ("PRINCIPAL"|"Principal"|"principal")   { return symbol(sym.PRINCIPAL); }
    ("ARREGLO"|"Arreglo"|"arreglo")         { return symbol(sym.ARREGLO); }
    ("!")                                   { return symbol(sym.NOT); }
    ("!=")                                  { return symbol(sym.DIFF); }
    (">")                                   { return symbol(sym.MAYOR); }
    ("<")                                   { return symbol(sym.MENOR); }
    ("<-")                                  { yybegin(COMMENT_M); }
    (">>")                                  { yybegin(COMMENT_L); }
    (">=")                                  { return symbol(sym.MAYOR_I); }
    ("<=")                                  { return symbol(sym.MENOR_I); }
    ("!!")                                  { return symbol(sym.NULO); }
    ("&&")                                  { return symbol(sym.AND); }
    ("!&&")                                 { return symbol(sym.NAND); }
    ("||")                                  { return symbol(sym.OR); }
    ("!||")                                 { return symbol(sym.NOR); }
    ("&|")                                  { return symbol(sym.XOR); }
    ("=")                                   { return symbol(sym.ASIGNAR); }
    ("==")                                  { return symbol(sym.IGUAL); }
    ("+")                                   { return symbol(sym.SUMA); }
    ("-")                                   { return symbol(sym.RESTA); }
    ("*")                                   { return symbol(sym.POR); }
    ("/")                                   { return symbol(sym.ENTRE); }
    ("%")                                   { return symbol(sym.MOD); }
    ("^")                                   { return symbol(sym.POT); }
    ("+=")                                  { return symbol(sym.SUMA_S); }
    ("--")                                  { return symbol(sym.DEC); }
    ("++")                                  { return symbol(sym.INC); }
    \[                                   { return symbol(sym.COR_A); }
    \]                                   { return symbol(sym.COR_C); }
    ("(")                                   { return symbol(sym.PAR_A); }
    (")")                                   { return symbol(sym.PAR_C); }
    (":")                                   { return symbol(sym.DOS_P); }
    (",")                                   { return symbol(sym.COMA); }
    ("{")                                   { return symbol(sym.LLAVE_A); }
    ("}")                                   { return symbol(sym.LLAVE_C); }
    (";")                                   { return symbol(sym.PUNTOC); }
    {D}                                     { return symbol(sym.NUMERO); }
    {DEC}                                   { return symbol(sym.NUMERO_D); }
    {ID}                                    { return symbol(sym.ID); }
    "\""                                    { yybegin(STRING);}
    "'"                                     { yybegin(CHAR);}
    {sal}                                   { yybegin(SALTO); }   
}

<SALTO>{
    [^"\t"">>""<-"]                                { Symbol retorno = indent(yytext(),false,false); if (retorno!=null) {return retorno; }; }
    {tab}                                   { Symbol retorno = indent(yytext(),true,false); if (retorno!=null) {return retorno; }; }
    {tab}">>"                                    { yybegin(COMMENT_L2); }
    {tab}"<-"                                    { yybegin(COMMENT_M); }
}

<STRING>{
    \"                              { yybegin(YYINITIAL);
                                       return symbol(sym.STRING,
                                       string.toString()); }
    [^\""#\"""##""#t""#n""#r""#\""]+ { string.append( yytext() ); }
    "#t"                            { string.append('\t'); }
    "#n"                            { string.append('\n'); }
    "#r"                            { string.append('\r'); }
    "#\""                           { string.append('\"'); }
    "#\\"                           { string.append('\\'); }
    "##"                            { string.append('#'); }
}
<CHAR>{
    "'"                              { yybegin(YYINITIAL);}
    "##"                             { return symbol(sym.CARACTER,"#"); }
    "#'"                             { return symbol(sym.CARACTER,"'"); }
    "#\\"                            { return symbol(sym.CARACTER,"\\"); }
    "#t"                             { return symbol(sym.CARACTER,"\t"); }
    "#n"                             { return symbol(sym.CARACTER,"\n"); }
    "#r"                             { return symbol(sym.CARACTER,"\r"); }
    ([aA-zZ]|[0-9]|"."|"%"|"!"|"?"|"¿"|"&"|"("|")")                             { return symbol(sym.CARACTER); }
    [^"'"]+                          { return symbol(sym.STRING); }
}
<COMMENT_L>{
    {sal}                    { yybegin(YYINITIAL); }
    [^\n]                               {}
}
<COMMENT_M>{
    "->"                              { yybegin(YYINITIAL); }
    [^"->"]                               {}
}
<COMMENT_L2>{
    {sal}                             { yybegin(SALTO); }
    [^\n]                               {}
}
{esp} {/*ignore*/}
{espacio}           {/*Ignore*/}
[^]                           { return new Symbol(sym.ERRORLEX,yycolumn,yyline,yytext()); }
