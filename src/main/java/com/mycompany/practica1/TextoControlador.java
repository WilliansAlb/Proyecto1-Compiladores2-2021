/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica1;

import Analizador.Lexer;
import Analizador.parser;
import Interprete.Expresion;
import Interprete.Pista;
import Interprete.Primitivo;
import Interprete.Programa;
import Interprete.Reproduccion;
import Interprete.Termino;
import Tablas.Simbolo;
import Tablas.Simbolos;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java_cup.runtime.Symbol;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
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
    CodeArea codeArea1;
    @FXML
    public LineChart myChart;
    @FXML
    public Button play;
    @FXML
    public StackPane area;
    @FXML
    public StackPane area1;
    @FXML
    public ProgressBar progreso;
    @FXML
    public Label pasado;
    @FXML
    public Label faltante;
    @FXML
    public Label cancion;
    @FXML
    public Button btn_revisar;
    @FXML
    public Button btn_consola;
    @FXML
    public Button parar;
    @FXML
    public TextArea consola;
    @FXML
    public TextArea consola1;
    @FXML
    public ImageView repro;
    @FXML
    public ImageView parar_img;
    @FXML
    public ImageView con;
    public TextField donde;
    public TextField donde1;
    public Reproduccion actual;
    @FXML
    public ListView<Lista> listas;
    @FXML
    public TabPane tabs;
    @FXML
    public ListView<Cancion> canciones_lista;
    @FXML
    public ListView<Cancion> canciones;
    public final ObservableList<Lista> data = FXCollections.observableArrayList();
    public final ObservableList<Cancion> data_cancion = FXCollections.observableArrayList();
    public final ObservableList<Cancion> data_lista_cancion = FXCollections.observableArrayList();
    public String lista_escogida = "";

    Task nuevo;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CodePista cp = new CodePista();
        CodeLista cl = new CodeLista();
        codeArea = cp.obtener();
        codeArea1 = cl.obtener();

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

        codeArea1.addEventHandler(KeyEvent.KEY_PRESSED, KE
                -> {
            if (KE.getCode() == KeyCode.ENTER) {
                int caretPosition = codeArea1.getCaretPosition();
                int currentParagraph = codeArea1.getCurrentParagraph();
                Matcher m0 = whiteSpace.matcher(codeArea1.getParagraph(currentParagraph - 1).getSegments().get(0));
                if (m0.find()) {
                    Platform.runLater(() -> codeArea1.insertText(caretPosition, m0.group()));
                }
            }
            donde1.setText("Linea: " + (codeArea1.getCurrentParagraph() + 1) + " Columna: " + codeArea1.getCaretColumn());
        });

        codeArea.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent MO) -> {
            donde.setText("Linea: " + (codeArea.getCurrentParagraph() + 1) + " Columna: " + codeArea.getCaretColumn());
        });
        codeArea1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent MO) -> {
            donde1.setText("Linea: " + (codeArea1.getCurrentParagraph() + 1) + " Columna: " + codeArea1.getCaretColumn());
        });
        codeArea.replaceText(0, 0, COMENTARIOS);

        donde.setText("Linea: " + (codeArea.getCurrentParagraph() + 1) + " Columna: " + codeArea.getCaretColumn());
        donde1.setText("Linea: " + (codeArea1.getCurrentParagraph() + 1) + " Columna: " + codeArea1.getCaretColumn());
        donde.setEditable(false);
        area.getChildren().add(new VirtualizedScrollPane<>(codeArea));
        donde1.setEditable(false);
        area1.getChildren().add(new VirtualizedScrollPane<>(codeArea1));
        codeArea.setBackground(new Background(new BackgroundFill(Paint.valueOf("lightgray"), CornerRadii.EMPTY, Insets.EMPTY)));
        codeArea1.setBackground(new Background(new BackgroundFill(Paint.valueOf("lightgray"), CornerRadii.EMPTY, Insets.EMPTY)));
        consola.setVisible(false);
        consola.managedProperty().bind(consola.visibleProperty());
        consola1.setVisible(false);
        consola1.managedProperty().bind(consola1.visibleProperty());
        parar.setVisible(false);
        parar.managedProperty().bind(parar.visibleProperty());
        data.clear();
        data.add(new Lista("Romanticas", 12, "lista.png"));
        data.add(new Lista("Dramaticas", 10, "lista.png"));
        for (int i = 0; i < 10; i++) {
            data.add(new Lista("Romanticas" + i, 12, "lista.png"));
            data.add(new Lista("Dramaticas" + i, 10, "lista.png"));
        }
        listas.setCellFactory(new Callback<ListView<Lista>, ListCell<Lista>>() {
            @Override
            public ListCell<Lista> call(ListView<Lista> param) {
                ListCell<Lista> cell = new ListCell<Lista>() {
                    @Override
                    protected void updateItem(Lista pa, boolean s) {
                        super.updateItem(pa, s);
                        if (pa != null) {
                            Image img = new Image(App.class.getResource(pa.getImg()).toExternalForm());
                            BorderPane b = new BorderPane();
                            Label l = new Label("No. Canciones: " + pa.getCanciones());
                            Label iden = new Label(pa.getId());
                            l.setFont(new Font(10.0));
                            iden.setPrefWidth(200.00);
                            iden.setTextAlignment(TextAlignment.LEFT);
                            l.setTextAlignment(TextAlignment.RIGHT);
                            iden.setTooltip(new Tooltip(pa.getId()));
                            ImageView imgView = new ImageView(img);
                            imgView.setPreserveRatio(false);
                            imgView.setFitWidth(20.00);
                            imgView.setFitHeight(20.00);
                            b.setCenter(iden);
                            b.setLeft(imgView);
                            b.setRight(l);
                            setGraphic(b);
                            b.addEventFilter(
                                    MouseEvent.MOUSE_PRESSED,
                                    new EventHandler<MouseEvent>() {
                                public void handle(final MouseEvent mouseEvent) {
                                    lista_escogida = iden.getText();
                                }
                            });
                        }
                    }
                };
                return cell;
            }
        });
        listas.setItems(data);
        data_cancion.add(new Cancion("Rythim_night", "1:30", null));
        data_cancion.add(new Cancion("Rythim_day", "1:30", null));
        data_cancion.add(new Cancion("Rythim_afternoon", "1:30", null));
        data_lista_cancion.add(new Cancion("Rythim_night", "1:30", null));
        data_lista_cancion.add(new Cancion("Rythim_day", "1:30", null));
        data_lista_cancion.add(new Cancion("Rythim_afternoon", "1:30", null));
        canciones.setCellFactory(new Callback<ListView<Cancion>, ListCell<Cancion>>() {
            @Override
            public ListCell<Cancion> call(ListView<Cancion> param) {
                ListCell<Cancion> cell = new ListCell<Cancion>() {
                    @Override
                    protected void updateItem(Cancion pa, boolean s) {
                        super.updateItem(pa, s);
                        if (pa != null) {
                            Image img = new Image(App.class.getResource(pa.getImg()).toExternalForm());
                            Label l = new Label("Duracion: " + pa.getDuracion());
                            Label iden = new Label(pa.getId());
                            l.setFont(new Font(10.0));
                            l.setTextAlignment(TextAlignment.RIGHT);
                            iden.setPrefWidth(500.00);
                            iden.setTextAlignment(TextAlignment.LEFT);
                            BorderPane b = new BorderPane();
                            ImageView imgView = new ImageView(img);
                            imgView.setPreserveRatio(false);
                            imgView.setFitWidth(20.00);
                            imgView.setFitHeight(20.00);
                            b.setLeft(imgView);
                            b.setCenter(iden);
                            b.setRight(l);
                            setGraphic(b);
                            b.addEventFilter(
                                    MouseEvent.MOUSE_PRESSED,
                                    new EventHandler<MouseEvent>() {
                                public void handle(final MouseEvent mouseEvent) {
                                    lista_escogida = iden.getText();
                                }
                            });
                        }
                    }
                };
                return cell;
            }
        });
        canciones.setItems(data_cancion);
        canciones_lista.setCellFactory(new Callback<ListView<Cancion>, ListCell<Cancion>>() {
            @Override
            public ListCell<Cancion> call(ListView<Cancion> param) {
                ListCell<Cancion> cell = new ListCell<Cancion>() {
                    @Override
                    protected void updateItem(Cancion pa, boolean s) {
                        super.updateItem(pa, s);
                        if (pa != null) {
                            Image img = new Image(App.class.getResource(pa.getImg()).toExternalForm());
                            Label l = new Label("Duracion: " + pa.getDuracion());
                            Label iden = new Label(pa.getId());
                            l.setFont(new Font(10.0));
                            l.setTextAlignment(TextAlignment.RIGHT);
                            iden.setPrefWidth(200.00);
                            iden.setTextAlignment(TextAlignment.LEFT);
                            BorderPane b = new BorderPane();
                            ImageView imgView = new ImageView(img);
                            imgView.setPreserveRatio(false);
                            imgView.setFitWidth(20.00);
                            imgView.setFitHeight(20.00);
                            b.setLeft(imgView);
                            b.setCenter(iden);
                            b.setRight(l);
                            setGraphic(b);
                            b.addEventFilter(
                                    MouseEvent.MOUSE_PRESSED,
                                    new EventHandler<MouseEvent>() {
                                public void handle(final MouseEvent mouseEvent) {
                                    lista_escogida = iden.getText();
                                }
                            });
                        }
                    }
                };
                return cell;
            }
        });
        canciones_lista.setItems(data_lista_cancion);

        try {
            leer_texto();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void crear() {
        SingleSelectionModel<Tab> selectionModel = tabs.getSelectionModel();
        selectionModel.select(1);
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
        s.agregar_sistema("$consola", "$consola", consola);
        p.interpretar(s, false);
        Simbolo rep = s.obtener("$reproducir");
        Simbolo msg = s.obtener("$mensaje");
        if (rep != null) {
            empezar_reproduccion(rep);
        } else {
            System.out.println("error");
        }
    }

    private void empezar_reproduccion(Simbolo rep) {
        if (rep.getDatos().get(0) != null) {
            actual = (Reproduccion) rep.getDatos().get(0);
            if (actual.getCanales() != null) {
                repro.setImage(new Image(App.class.getResource("pause.png").toExternalForm()));
                myChart.getData().clear();
                int nuevo2 = actual.max() * 50;
                int nuevo3 = nuevo2 / 1000;
                int minutos = (int) (nuevo3 / 60);
                int sec = (int) (nuevo3 % 60);
                String tiempo_pasado = (sec > 9) ? minutos + ":" + sec : minutos + ":0" + sec;
                faltante.setText(tiempo_pasado);
                cancion.setText(actual.getId());
                nuevo = new Task(myChart, progreso, pasado, actual);
                nuevo.setDaemon(true);
                nuevo.start();
                iniciando = false;
            }
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

    boolean iniciando = true;

    @FXML
    private void iniciar2() {
        if (iniciando) {
            repro.setImage(new Image(App.class.getResource("pause.png").toExternalForm()));
            myChart.getData().clear();
            nuevo = new Task(myChart, progreso, pasado);
            nuevo.setDaemon(true);
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

    @FXML
    private void play_seleccion() {
        Reproduccion r = new Reproduccion();
        boolean pasa = false;
        for (int i = 0; i < data_cancion.size(); i++) {
            if (data_cancion.get(i).getId().equals(lista_escogida)) {
                r = data_cancion.get(i).getReproductor();
                pasa = true;
                break;
            }
        }
        if (pasa) {
            repro.setImage(new Image(App.class.getResource("pause.png").toExternalForm()));
            myChart.getData().clear();
            int nuevo2 = r.max() * 50;
            int nuevo3 = nuevo2 / 1000;
            int minutos = (int) (nuevo3 / 60);
            int sec = (int) (nuevo3 % 60);
            String tiempo_pasado = (sec > 9) ? minutos + ":" + sec : minutos + ":0" + sec;
            faltante.setText(tiempo_pasado);
            cancion.setText(r.getId());
            nuevo = new Task(myChart, progreso, pasado, r);
            nuevo.setDaemon(true);
            nuevo.start();
            iniciando = false;
        }
    }

    private void escribir_texto(String texto) throws IOException {
        try (Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("C:/Users/willi/OneDrive/Documentos/NetBeansProjects/GCIC/src/main/webapp/pages/generados/temp.jsp"), "UTF-8"))) {
            out.write(texto);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TextoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void leer_texto() throws IOException {
        File nuevo = new File("src/main/resources/pistas.txt");
        parser par = new parser(new Lexer(new FileReader(nuevo)));
        try {
            par.parse();
            Programa program = par.programa;
            Simbolos tabla = new Simbolos();
            program.interpretar(tabla, true);
            List<Pista> lista = (List) tabla.obtener("$pistas").getDatos();
            data_cancion.clear();
            for (Pista p : lista) {
                data_cancion.add(new Cancion(p.getId(), p.obtener_duracion(), p.getRep()));
            }
        } catch (Exception ex) {
            System.out.println("Error por: " + ex.toString());
        }
    }

}
