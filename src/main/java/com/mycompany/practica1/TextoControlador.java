/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica1;

import Analizador.Lexer;
import Analizador.parser;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java_cup.runtime.Symbol;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import org.fxmisc.flowless.VirtualizedScrollPane;
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
public class TextoControlador implements Initializable {
    CodeArea codeArea;
    @FXML
    public LineChart myChart;
    @FXML
    public StackPane area;
    @FXML
    public Button btn_revisar;
    
    public TextField donde;
    
    Task nuevo;

    private static final String[] KEYWORDS = new String[]{
        "Pista","PISTA","pista","extiende","EXTIENDE","Extiende","Entero","entero","ENTERO"
            ,"doble","DOBLE","Doble","Boolean","boolean","BOOLEAN","Caracter","CARACTER","caracter"
            ,"cadena","CADENA","Cadena","verdadero","Verdadero","VERDADERO","True","true","TRUE"
            ,"False","false","FALSE","falso","Falso","FALSO","Keep","keep","KEEP","var","VAR","Var"
            ,"Si","SI","si","Sino","SINO","sino","Salir","SALIR","Salir","Continuar","continuar"
            ,"CONTINUAR","Switch","switch","SWITCH","Caso","CASO","caso","default","Default","DEFAULT"
            ,"Para","para","PARA","Mientras","mientras","MIENTRAS","hacer","HACER","Hacer","retorna"
            ,"Retorna","RETORNA","Reproducir","reproducir","REPRODUCIR","Esperar","ESPERAR","esperar"
            ,"Ordenar","ORDENAR","ordenar","Ascendente","ASCENDENTE","ascendente","DESCENDENTE"
            ,"descendente","Descendente","Pares","pares","PARES","impares","IMPARES","Impares"
            ,"primos","Primos","PRIMOS","Sumarizar","sumarizar","SUMARIZAR","Longitud","LONGITUD"
            ,"longitud","Mensaje","MENSAJE","mensaje","Principal","PRINCIPAL","principal"
    };
    
    private static final String SI_PRUEBA = "si (verdadero)\n\ta1 10\n\ta3 20\nsino si (falso)\n\ta3 12\nsino\n\ta3 6"; 

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String NUMBER_PATTERN = "[0-9]+\\.?[0-9]*";
    private static final String ID_PATTERN = "([aA-zZ]|_)([aA-zZ]|[0-9]|_)*";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/" // for whole text processing (text blocks)
            + "|" + "/\\*[^\\v]*" + "|" + "^\\h*\\*([^\\v]*|/)";  // for visible paragraph processing (line by line)

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
            + "|(?<PAREN>" + PAREN_PATTERN + ")"
            + "|(?<BRACE>" + BRACE_PATTERN + ")"
            + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
            + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
            + "|(?<STRING>" + STRING_PATTERN + ")"
            + "|(?<ID>" + ID_PATTERN + ")"
            + "|(?<NUMBER>" + NUMBER_PATTERN + ")"
            + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codeArea = new CodeArea();

        // add line numbers to the left of area
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.setContextMenu(new DefaultContextMenu());

        codeArea.getVisibleParagraphs().addModificationObserver(
                new VisibleParagraphStyler<>(codeArea, this::computeHighlighting)
        );

        // auto-indent: insert previous line's indents on enter
        final Pattern whiteSpace = Pattern.compile("^\\s+");
        codeArea.addEventHandler(KeyEvent.KEY_PRESSED, KE
                -> {
            donde.setText("Linea: " + (codeArea.getCurrentParagraph() + 1) + " Columna: " + codeArea.getCaretColumn());
        });

        codeArea.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent MO) -> {
            donde.setText("Linea: " + (codeArea.getCurrentParagraph() + 1) + " Columna: " + codeArea.getCaretColumn());
        });
        String loop = "";
        codeArea.replaceText(0, 0, SI_PRUEBA);
        //codeArea.replaceText("");
        //codeArea.setPrefSize(1000, 600);
        donde.setText("Linea: " + (codeArea.getCurrentParagraph() + 1) + " Columna: " + codeArea.getCaretColumn());
        donde.setEditable(false);
        area.getChildren().add(new VirtualizedScrollPane<>(codeArea));
        codeArea.setBackground(new Background(new BackgroundFill(Paint.valueOf("#0d0030"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private StyleSpans<Collection<String>> computeHighlighting(String text) {
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
    
    @FXML
    private void revisar() throws IOException, Exception {
        System.out.println(codeArea.getText());
        parser par = new parser(new Lexer(new StringReader(codeArea.getText())));
        
        par.parse();
        for (int i = 0; i < 10; i++) {
            System.out.println("-----------");
        }
        Lexer n = new Lexer(new StringReader(codeArea.getText()));
        while(true){
            Symbol s = n.next_token();
            if (s.value==null){
                return;
            }
            System.out.println(s.value.toString());
        }
        
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
    @FXML
    private void iniciar(){
        System.out.println("inicia");
        myChart.getData().clear();
        nuevo = new Task(myChart);
        nuevo.start();
    }
    @FXML
    private void parar(){
        System.out.println("para");
        nuevo.kill();
    }
}
