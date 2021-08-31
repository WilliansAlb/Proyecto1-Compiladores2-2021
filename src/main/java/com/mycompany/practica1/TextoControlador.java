/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica1;

import Analizador.Lexer;
import Analizador.parser;
import Interprete.Expresion;
import Interprete.Primitivo;
import Interprete.Programa;
import Interprete.Reproduccion;
import Interprete.Termino;
import Tablas.Simbolo;
import Tablas.Simbolos;
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
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    boolean reproduciendo = true;
    CodeArea codeArea;
    @FXML
    public LineChart myChart;
    @FXML
    public Button play;
    @FXML
    public StackPane area;
    @FXML
    public ProgressBar progreso;
    @FXML
    public Label pasado;
    @FXML
    public Label faltante;
    @FXML
    public Button btn_revisar;
    @FXML
    public Button btn_consola;
    @FXML
    public Button parar;
    @FXML
    public TextArea consola;
    @FXML
    public ImageView repro;
    @FXML
    public ImageView parar_img;
    @FXML
    public ImageView con;

    public TextField donde;
    public Reproduccion actual;

    Task nuevo;

    private static final String[] KEYWORDS = new String[]{
        "Pista", "PISTA", "pista", "extiende", "EXTIENDE", "Extiende", "Entero", "entero", "ENTERO",
        "doble", "DOBLE", "Doble", "Boolean", "boolean", "BOOLEAN", "Caracter", "CARACTER", "caracter",
        "cadena", "CADENA", "Cadena", "verdadero", "Verdadero", "VERDADERO", "True", "true", "TRUE",
        "False", "false", "FALSE", "falso", "Falso", "FALSO", "Keep", "keep", "KEEP", "var", "VAR", "Var",
        "Si", "SI", "si", "Sino", "SINO", "sino", "Salir", "SALIR", "salir", "Continuar", "continuar",
        "CONTINUAR", "Switch", "switch", "SWITCH", "Caso", "CASO", "caso", "default", "Default", "DEFAULT",
        "Para", "para", "PARA", "Mientras", "mientras", "MIENTRAS", "hacer", "HACER", "Hacer", "retorna",
        "Retorna", "RETORNA", "Reproducir", "reproducir", "REPRODUCIR", "Esperar", "ESPERAR", "esperar",
        "Ordenar", "ORDENAR", "ordenar", "Ascendente", "ASCENDENTE", "ascendente", "DESCENDENTE",
        "descendente", "Descendente", "Pares", "pares", "PARES", "impares", "IMPARES", "Impares",
        "primos", "Primos", "PRIMOS", "Sumarizar", "sumarizar", "SUMARIZAR", "Longitud", "LONGITUD",
        "longitud", "Mensaje", "MENSAJE", "mensaje", "Principal", "PRINCIPAL", "principal"
    };
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
            if (KE.getCode() == KeyCode.ENTER) {
                int caretPosition = codeArea.getCaretPosition();
                int currentParagraph = codeArea.getCurrentParagraph();
                Matcher m0 = whiteSpace.matcher(codeArea.getParagraph(currentParagraph - 1).getSegments().get(0));
                if (m0.find()) {
                    Platform.runLater(() -> codeArea.insertText(caretPosition, m0.group()));
                }
            }
            donde.setText("Linea: " + (codeArea.getCurrentParagraph() + 1) + " Columna: " + codeArea.getCaretColumn());
        });

        codeArea.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent MO) -> {
            donde.setText("Linea: " + (codeArea.getCurrentParagraph() + 1) + " Columna: " + codeArea.getCaretColumn());
        });
        String loop = "";
        codeArea.replaceText(0, 0, COMENTARIOS);
        //codeArea.replaceText("");
        //codeArea.setPrefSize(1000, 600);
        donde.setText("Linea: " + (codeArea.getCurrentParagraph() + 1) + " Columna: " + codeArea.getCaretColumn());
        donde.setEditable(false);
        area.getChildren().add(new VirtualizedScrollPane<>(codeArea));
        codeArea.setBackground(new Background(new BackgroundFill(Paint.valueOf("#0d0030"), CornerRadii.EMPTY, Insets.EMPTY)));
        consola.setVisible(false);
        consola.managedProperty().bind(consola.visibleProperty());
        parar.setVisible(false);
        parar.managedProperty().bind(parar.visibleProperty());
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
        //System.out.println(codeArea.getText());
        parser par = new parser(new Lexer(new StringReader(codeArea.getText())));
        par.parse();
        Programa program = par.programa;
        iniciar(program);
        //leer(par.sumando);
        //Lexer n = new Lexer(new StringReader(codeArea.getText()));
        //escribir(n);
    }

    @FXML
    private void ver_consola() throws IOException, Exception {
        if (btn_consola.getText().equalsIgnoreCase("ver consola")) {
            con.setImage(new Image(App.class.getResource("ocultar.png").toExternalForm()));
            btn_consola.setText("OCULTAR CONSOLA");
            consola.setVisible(true);
        } else {
            con.setImage(new Image(App.class.getResource("ver.png").toExternalForm()));
            btn_consola.setText("VER CONSOLA");
            consola.setVisible(false);
        }
    }

    private void iniciar(Programa p) {
        Simbolos s = new Simbolos();
        p.interpretar(s);
        Simbolo rep = s.obtener("$reproducir");
        //empezar_reproduccion(rep);
    }
    
    private void empezar_reproduccion(Simbolo rep){
        if (rep.getDatos().get(0) != null) {
            actual = (Reproduccion) rep.getDatos().get(0);
            repro.setImage(new Image(App.class.getResource("pause.png").toExternalForm()));
            myChart.getData().clear();
            int nuevo2 = actual.max()/2;
            int minutos = (int) (nuevo2/ 120);
            int sec = (int) (nuevo2 % 120);
            String tiempo_pasado = (sec > 9) ? minutos + ":" + sec : minutos + ":0" + sec;
            faltante.setText(tiempo_pasado);
            nuevo = new Task(myChart, progreso, pasado, actual);
            nuevo.start();
            iniciando = false;
        }
    }

    private void escribir(Lexer n) throws IOException {
        while (true) {
            Symbol s = n.next_token();
            if (s.value == null) {
                return;
            }
            System.out.println(s.value.toString() + " - " + s.sym);
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
    boolean iniciando = true;

    @FXML
    private void iniciar2() {
        if (iniciando) {
            repro.setImage(new Image(App.class.getResource("pause.png").toExternalForm()));
            myChart.getData().clear();
            nuevo = new Task(myChart, progreso, pasado);
            nuevo.start();
            iniciando = false;
        } else {
            if (reproduciendo) {
                repro.setImage(new Image(App.class.getResource("play.png").toExternalForm()));
                parar.setVisible(true);
                nuevo.suspenderhilo();
                System.out.println("hola");
                reproduciendo = !reproduciendo;
            } else {
                repro.setImage(new Image(App.class.getResource("pause.png").toExternalForm()));
                parar.setVisible(false);
                nuevo.renaudarhilo();
                reproduciendo = !reproduciendo;
            }
        }
    }

    @FXML
    private void parar() {
        repro.setImage(new Image(App.class.getResource("play.png").toExternalForm()));
        nuevo.pausarhilo();
        iniciando = true;
        parar.setVisible(false);
        myChart.getData().removeAll(Collections.singleton(myChart.getData().setAll()));
        progreso.setProgress(0);
        pasado.setText("0:00");
    }

}
