/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica1;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.Paragraph;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.collection.ListModification;

/**
 *
 * @author willi
 */
public class CodePista {
    private static final String[] KEYWORDS = new String[]{
        "Pista", "PISTA", "pista", "extiende", "EXTIENDE", "Extiende", "Entero", "entero", "ENTERO",
        "doble", "DOBLE", "Doble", "Boolean", "boolean", "BOOLEAN", "Caracter", "CARACTER", "caracter",
        "cadena", "CADENA", "Cadena", "verdadero", "Verdadero", "VERDADERO", "True", "true", "TRUE",
        "False", "false", "FALSE", "falso", "Falso", "FALSO", "Keep", "keep", "KEEP", "var", "VAR", "Var",
        "Si", "SI", "si", "Sino", "SINO", "sino", "Salir", "SALIR", "salir", "Continuar", "continuar",
        "CONTINUAR", "Switch", "switch", "SWITCH", "Caso", "CASO", "caso", "default", "Default", "DEFAULT",
        "Para", "para", "PARA", "Mientras", "mientras", "MIENTRAS", "hacer", "HACER", "Hacer", "retorna",
        "Retorna", "RETORNA", "Ascendente", "ASCENDENTE", "ascendente", "DESCENDENTE",
        "descendente", "Descendente", "Pares", "pares", "PARES", "impares", "IMPARES", "Impares",
        "primos", "Primos", "PRIMOS", "Principal", "PRINCIPAL", "principal"
    };
    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String NUMBER_PATTERN = "[0-9]+\\.?[0-9]*";
    private static final String ID_PATTERN = "([aA-zZ]|_)([aA-zZ]|[0-9]|_)*";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String CHAR_PATTERN = "'([^\'\\\\]|\\\\.)*'";
    private static final String COMMENT_PATTERN = ">>[^\n]*" + "|" + "<--*[\\s\\S]*?--*>";  // for visible paragraph processing (line by line)
private static final String COMENTARIOS = ">>prueba comentario de linea\n"
            + ">>prueba comentario de linea\n"
            + "<- prueba comentario\n"
            + "de lineas a ver si funciona\n"
            + "->\n"
            + "PISTA komm EXTIENDE Neon, Genesis\n"
            + "	var entero a = 10\n"
            + "	keep var entero a09 = 22\n"
            + "	var cadena ca = \"hola\"\n"
            + "	Principal()\n"
            + "		a = 23\n"
            + "		si (a<5)\n"
            + "			var entero a3 = a+10\n"
            + "			var entero a4 = 2\n"
            + "			var entero a5 = 5\n"
            + "		sino si (true)\n"
            + "			var entero a34 = 34\n"
            + "			var entero a104 = 34\n"
            + "			var entero a32 = true\n"
            + "			si (a32>0)\n"
            + "				var entero a65 = 5\n"
            + "				a = 25\n"
            + "				var entero a219 = 10\n"
            + "			var entero a70 = a65\n"
            + "		Reproducir(re,2,1000,3)\n"
            + "		Reproducir(fa,5,2000,2)\n"
            + "		Reproducir(mi,2,2000,1)\n"
            + "		switch(ca)\n"
            + "			caso \"ho\"\n"
            + "				Mensaje(\"hola perro esto pasa\")\n"
            + "			caso \"hola\"\n"
            + "				Mensaje(\"Correcto sin salida\")\n"
            + "			caso \"hello\"\n"
            + "				Mensaje(\"Otro más\")\n"
            + "				salir\n"
            + "			default\n"
            + "				Mensaje(\"No tendría que pasar\")\n"
            + "				Mensaje(\"No tendría que pasar2\")";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
            + "|(?<PAREN>" + PAREN_PATTERN + ")"
            + "|(?<BRACE>" + BRACE_PATTERN + ")"
            + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
            + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
            + "|(?<STRING>" + STRING_PATTERN + ")"
            + "|(?<ID>" + ID_PATTERN + ")"
            + "|(?<NUMBER>" + NUMBER_PATTERN + ")"
            + "|(?<CHAR>" + CHAR_PATTERN + ")"
            + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    public CodePista() {
        
    }
    
    public CodeArea obtener(){
        CodeArea n = new CodeArea();
        n.getVisibleParagraphs().addModificationObserver(
                new VisibleParagraphStyler<>(n, this::computeHighlighting)
        );
        
        // add line numbers to the left of area
        //n.setParagraphGraphicFactory(LineNumberFactory.get(n));
        n.setContextMenu(new DefaultContextMenu());
        IntFunction<Node> numberFactory = LineNumberFactory.get(n);
        IntFunction<Node> arrowFactory = new ArrowFactory(n.currentParagraphProperty());
        IntFunction<Node> graphicFactory = line -> {
            HBox hbox = new HBox(
                numberFactory.apply(line),
                arrowFactory.apply(line));
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.getChildren().get(0).setStyle("-fx-background-color: #3f474f;-fx-text-fill: white;");
            return hbox;
        };
        n.setParagraphGraphicFactory(graphicFactory);
        n.replaceText(COMENTARIOS);
        return n;
    }
    
    private class VisibleParagraphStyler<PS, SEG, S> implements Consumer<ListModification<? extends Paragraph<PS, SEG, S>>> {

        private final GenericStyledArea<PS, SEG, S> area;
        private final Function<String, StyleSpans<S>> computeStyles;
        private int prevParagraph, prevTextLength;

        public VisibleParagraphStyler(GenericStyledArea<PS, SEG, S> area, Function<String, StyleSpans<S>> computeStyles) {
            this.computeStyles = computeStyles;
            this.area = area;
        }

        @Override
        public void accept(ListModification<? extends Paragraph<PS, SEG, S>> lm) {
            if (lm.getAddedSize() > 0) {
                int paragraph = Math.min(area.firstVisibleParToAllParIndex() + lm.getFrom(), area.getParagraphs().size() - 1);
                String text = area.getText(paragraph, 0, paragraph, area.getParagraphLength(paragraph));

                if (paragraph != prevParagraph || text.length() != prevTextLength) {
                    int startPos = area.getAbsolutePosition(paragraph, 0);
                    Platform.runLater(() -> area.setStyleSpans(startPos, computeStyles.apply(text)));
                    prevTextLength = text.length();
                    prevParagraph = paragraph;
                }
            }
        }
    }

    
    public StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass
                    = matcher.group("KEYWORD") != null ? "keyword"
                    : matcher.group("PAREN") != null ? "paren"
                    : matcher.group("BRACE") != null ? "brace"
                    : matcher.group("BRACKET") != null ? "bracket"
                    : matcher.group("SEMICOLON") != null ? "semicolon"
                    : matcher.group("STRING") != null ? "string"
                    : matcher.group("NUMBER") != null ? "number"
                    : matcher.group("ID") != null ? "id"
                    : matcher.group("CHAR") != null ? "char"
                    : matcher.group("COMMENT") != null ? "comment"
                    : null;
            /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
    
    
    private class DefaultContextMenu extends ContextMenu {

        private MenuItem fold, unfold, print;

        public DefaultContextMenu() {
            fold = new MenuItem("Fold selected text");
            fold.setOnAction(AE -> {
                hide();
                fold();
            });

            unfold = new MenuItem("Unfold from cursor");
            unfold.setOnAction(AE -> {
                hide();
                unfold();
            });

            print = new MenuItem("Print");
            print.setOnAction(AE -> {
                hide();
                print();
            });

            getItems().addAll(fold, unfold, print);
        }

        /**
         * Folds multiple lines of selected text, only showing the first line
         * and hiding the rest.
         */
        private void fold() {
            ((CodeArea) getOwnerNode()).foldSelectedParagraphs();
        }

        /**
         * Unfold the CURRENT line/paragraph if it has a fold.
         */
        private void unfold() {
            CodeArea area = (CodeArea) getOwnerNode();
            area.unfoldParagraphs(area.getCurrentParagraph());
        }

        private void print() {
            System.out.println(((CodeArea) getOwnerNode()).getText());
        }
    }
}
