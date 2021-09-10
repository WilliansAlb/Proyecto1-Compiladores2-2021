/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica1;

import Analizador.Lexer;
import Analizador.LexerLista;
import Analizador.parser;
import Analizador.parserLista;
import Interprete.Errores;
import Interprete.Expresion;
import Interprete.Pista;
import Interprete.Primitivo;
import Interprete.Programa;
import Interprete.Reproduccion;
import Interprete.Termino;
import Tablas.Simbolo;
import Tablas.Simbolos;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyledTextArea;
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
    CodeArea codeAreaError;
    int error_actual = 0;
    @FXML
    public LineChart myChart;
    @FXML
    public Button play;
    @FXML
    public StackPane area;
    @FXML
    public StackPane area1;
    @FXML
    public StackPane area_error;
    @FXML
    public ProgressBar progreso;
    @FXML
    public Label pasado;
    @FXML
    public Label numero_error;
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
    public ImageView repro;
    @FXML
    public ImageView parar_img;
    @FXML
    public TableView<Errores> tabla;
    @FXML
    public ImageView con;
    public TextField donde;
    public TextField donde1;
    public TextField donde_error;
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
    public String cancion_escogida = "";
    public ArrayList<Cancion> cans = new ArrayList<>();
    public ArrayList<Lista> lis = new ArrayList<>();
    public final ObservableList<Errores> datos = FXCollections.observableArrayList();
    public ArrayList<Errores> listado_errores = new ArrayList<>();
    public boolean modificando_pista = false;
    public boolean modificando_lista = false;
    Task nuevo;

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
        codeArea.getStylesheets().add(getClass().getResource("codeArea.css").toExternalForm());
        codeArea1.getStylesheets().add(getClass().getResource("codeArea.css").toExternalForm());
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
        parar.setVisible(false);
        parar.managedProperty().bind(parar.visibleProperty());
        data.clear();
        data.add(new Lista("Romanticas", null, true, false));
        for (int i = 0; i < 10; i++) {
            data.add(new Lista("Romanticas" + i, null, false, true));
        }
        listas.setCellFactory(new Callback<ListView<Lista>, ListCell<Lista>>() {
            @Override
            public ListCell<Lista> call(ListView<Lista> param) {
                ListCell<Lista> cell = new ListCell<Lista>() {
                    @Override
                    protected void updateItem(Lista pa, boolean s) {
                        super.updateItem(pa, s);
                        if (pa != null) {
                            Image img = new Image(App.class.getResource(pa.IMG_LISTA).toExternalForm());
                            HBox h1 = new HBox();
                            Label iden = new Label(pa.getId());
                            iden.setMaxWidth(1000.00);
                            iden.setTextAlignment(TextAlignment.LEFT);
                            iden.setTooltip(new Tooltip(pa.getId()));
                            HBox h = new HBox();
                            if (pa.isCircular()) {
                                Image img2 = new Image(App.class.getResource(pa.IMG_CIRCULAR).toExternalForm());
                                ImageView imgT = new ImageView(img2);
                                imgT.setPreserveRatio(false);
                                imgT.setFitWidth(20.00);
                                imgT.setFitHeight(20.00);
                                Label im1 = new Label();
                                im1.setGraphic(imgT);
                                im1.setTooltip(new Tooltip("Lista circular"));
                                h.getChildren().add(im1);
                            }
                            if (pa.isRandom()) {
                                Image img2 = new Image(App.class.getResource(pa.IMG_RANDOM).toExternalForm());
                                ImageView imgT = new ImageView(img2);
                                imgT.setPreserveRatio(false);
                                imgT.setFitWidth(20.00);
                                imgT.setFitHeight(20.00);
                                Label im1 = new Label();
                                im1.setGraphic(imgT);
                                im1.setTooltip(new Tooltip("Lista aleatoria"));
                                h.getChildren().add(im1);
                            }
                            HBox.setHgrow(iden, Priority.ALWAYS);

                            ImageView imgView = new ImageView(img);
                            imgView.setPreserveRatio(false);
                            imgView.setFitWidth(20.00);
                            imgView.setFitHeight(20.00);
                            h1.getChildren().add(imgView);
                            h1.getChildren().add(iden);
                            h1.getChildren().add(h);
                            setGraphic(h1);
                            h1.addEventFilter(
                                    MouseEvent.MOUSE_PRESSED,
                                    new EventHandler<MouseEvent>() {
                                public void handle(final MouseEvent mouseEvent) {
                                    lista_escogida = iden.getText();
                                    if (!lista_escogida.equalsIgnoreCase("No has creado ninguna lista")) {
                                        rellenar_canciones_lista(iden.getText());
                                    } else {
                                        data_lista_cancion.clear();
                                    }
                                }
                            });
                        } else {
                            setGraphic(null);
                        }
                    }
                };
                return cell;
            }
        });
        listas.setItems(data);
        data_cancion.add(new Cancion("Rythim_night", "1:30", null, ""));
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
                            iden.setMaxWidth(1000);
                            iden.setMinWidth(50);
                            l.setTextAlignment(TextAlignment.RIGHT);
                            iden.setTextAlignment(TextAlignment.LEFT);
                            HBox h = new HBox();
                            HBox.setHgrow(iden, Priority.ALWAYS);
                            HBox.setHgrow(l, Priority.ALWAYS);
                            ImageView imgView = new ImageView(img);
                            imgView.setPreserveRatio(false);
                            imgView.setFitWidth(20.00);
                            imgView.setFitHeight(20.00);
                            h.getChildren().add(imgView);
                            h.getChildren().add(iden);
                            h.getChildren().add(l);
                            setGraphic(h);
                            h.addEventFilter(
                                    MouseEvent.MOUSE_PRESSED,
                                    new EventHandler<MouseEvent>() {
                                public void handle(final MouseEvent mouseEvent) {
                                    cancion_escogida = iden.getText();
                                }
                            });
                        } else {
                            setGraphic(null);
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
                            Label iden = new Label(pa.getId());
                            iden.setMaxWidth(1000);
                            iden.setMinWidth(50);
                            iden.setTextAlignment(TextAlignment.LEFT);
                            HBox h = new HBox();
                            HBox.setHgrow(iden, Priority.ALWAYS);
                            ImageView imgView = new ImageView(img);
                            imgView.setPreserveRatio(false);
                            imgView.setFitWidth(20.00);
                            imgView.setFitHeight(20.00);
                            h.getChildren().add(imgView);
                            h.getChildren().add(iden);
                            setGraphic(h);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
                return cell;
            }
        });
        canciones_lista.setItems(data_lista_cancion);
        llenar_canciones();
        rellenar_listas();

        TableColumn<Errores, String> tipo = new TableColumn<>("tipo");
        tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Errores, String> razon = new TableColumn<>("razon");
        razon.setCellValueFactory(new PropertyValueFactory<>("razon"));

        TableColumn<Errores, Integer> linea = new TableColumn<>("linea");
        linea.setCellValueFactory(new PropertyValueFactory<>("linea"));

        TableColumn<Errores, Integer> columna = new TableColumn<>("columna");
        columna.setCellValueFactory(new PropertyValueFactory<>("columna"));
        datos.add(new Errores("Semantico", "Una razon x", 10, 10));

        linea.prefWidthProperty().bind(tabla.widthProperty().multiply(0.1));
        columna.prefWidthProperty().bind(tabla.widthProperty().multiply(0.1));
        tipo.prefWidthProperty().bind(tabla.widthProperty().multiply(0.15));
        razon.prefWidthProperty().bind(tabla.widthProperty().multiply(0.65));
        tabla.getColumns().addAll(linea, columna, tipo, razon);
        tabla.setItems(datos);
        Servidor s = new Servidor(data_cancion);
        s.setDaemon(true);
        s.start();
    }

    private void rellenar_canciones_lista(String text) {
        data_lista_cancion.clear();
        for (int i = 0; i < lis.size(); i++) {
            if (lis.get(i).getId().equalsIgnoreCase(text)) {
                if (lis.get(i).getCanciones() != null) {
                    for (int j = 0; j < lis.get(i).getCanciones().size(); j++) {
                        data_lista_cancion.add(new Cancion(lis.get(i).getCanciones().get(j), "--:--", null, "texto"));
                    }
                }
            }
        }
        if (data_lista_cancion.isEmpty()) {
            data_lista_cancion.add(new Cancion("No contiene pistas esta lista", "--:--", null, "texto"));
        }
    }

    @FXML
    private void crear() {
        SingleSelectionModel<Tab> selectionModel = tabs.getSelectionModel();
        selectionModel.select(1);
    }

    @FXML
    private void modificar_lista() {
        Lista temp_lista = new Lista();
        for (int i = 0; i < lis.size(); i++) {
            if (lis.get(i).getId().equalsIgnoreCase(lista_escogida)) {
                temp_lista = lis.get(i);
            }
        }
        String codigo = "{\n\tlista:\n\t{\n\t\tnombre: \"" + temp_lista.getId() + "\",\n\t\tcircular: " + temp_lista.isCircular() + ",\n\t\trandom: " + temp_lista.isRandom();
        if (temp_lista.getCanciones() != null) {
            if (!temp_lista.getCanciones().isEmpty()) {
                codigo += ",\n\t\tpistas: " + Arrays.toString(temp_lista.getCanciones().toArray()) + "\n\t}\n}";
            } else {
                codigo += "\n\t}\n}";
            }
        } else {
            codigo += "\n\t}\n}";
        }
        codeArea1.replaceText(codigo);
        cambiar_a_tab(2);
    }

    @FXML
    private void revisar() throws IOException, Exception {
        listado_errores.clear();
        error_actual = 0;
        parser par = new parser(new Lexer(new StringReader(codeArea.getText())));
        par.parse();
        if (par.errores.isEmpty()) {
            Programa program = par.programa;
            iniciar2(program);
        } else {
            for (int i = 0; i < par.errores.size(); i++) {
                listado_errores.add(par.errores.get(i));
            }
            iniciar_area_error(false);
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Ha ocurrido un error, se te mostrara la tab de errores");
            datos.set(0, listado_errores.get(0));
            numero_error.setText("error " + 1 + " de " + listado_errores.size());
            a.show();
            cambiar_a_tab(3);
        }
    }

    int temp_recurrencias = 0;

    @FXML
    private void revisarLista() throws IOException, Exception {
        listado_errores.clear();
        error_actual = 0;
        parserLista par = new parserLista(new LexerLista(new StringReader(codeArea1.getText())));
        par.parse();
        if (par.lista_errores.isEmpty()) {
            Lista l = par.la_lista;
            ArrayList<String> t_no = new ArrayList<>();
            int te = 0;
            int te2 = 0;
            if (l.getCanciones() != null) {
                while (te < l.getCanciones().size() && te2 < l.getCanciones().size()) {
                    String com = l.getCanciones().get(te2);
                    boolean existe = false;
                    for (int i = 0; i < cans.size(); i++) {
                        if (com.equals(cans.get(i).getId())) {
                            te++;
                            existe = true;
                            break;
                        }
                    }
                    if (!existe) {
                        t_no.add(com);
                    }
                    te2++;
                }
                if (te == te2) {
                    temp_recurrencias = 0;
                    for (int i = 0; i < lis.size(); i++) {
                        if (lis.get(i).getId().equals(l.getId())) {
                            temp_recurrencias = i;
                            break;
                        }
                    }
                    if (temp_recurrencias != 0) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Ya existe esta lista");
                        alert.setContentText("Sobreescribir la lista " + l.getId() + "?");
                        ButtonType okButton = new ButtonType("Si", ButtonBar.ButtonData.YES);
                        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                        alert.getButtonTypes().setAll(okButton, noButton);
                        alert.showAndWait().ifPresent(type -> {
                            if (type == okButton) {
                                data.set(temp_recurrencias, l);
                                lis.set(temp_recurrencias, l);
                                escribir_binario_lista();
                                rellenar_listas();
                                Alert a = new Alert(AlertType.INFORMATION);
                                a.setContentText("Fue sobreescrita la lista");
                                a.show();
                                cambiar_a_tab(0);
                            }
                        });
                    } else {
                        data.add(l);
                        lis.add(l);
                        escribir_binario_lista();
                        rellenar_listas();
                        Alert a = new Alert(AlertType.INFORMATION);
                        a.setContentText("Fue agregada la lista");
                        a.show();
                        cambiar_a_tab(0);
                    }
                } else {
                    Alert a = new Alert(AlertType.ERROR);
                    a.setContentText("Ha ocurrido un error, se te mostrara la tab de erorres");
                    a.show();
                    listado_errores = new ArrayList<>();
                    listado_errores.add(new Errores("Semantico", "La lista tiene canciones que no existen " + Arrays.toString(t_no.toArray()) + "", 0, 0));
                    datos.set(0, listado_errores.get(0));
                    numero_error.setText("error " + 1 + " de " + listado_errores.size());
                    iniciar_area_error(true);
                    cambiar_a_tab(3);
                }
            } else {
                temp_recurrencias = 0;
                for (int i = 0; i < lis.size(); i++) {
                    if (lis.get(i).getId().equals(l.getId())) {
                        temp_recurrencias = i;
                        break;
                    }
                }
                if (temp_recurrencias != 0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Ya existe esta lista");
                    alert.setContentText("Sobreescribir la lista " + l.getId() + "?");
                    ButtonType okButton = new ButtonType("Si", ButtonBar.ButtonData.YES);
                    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                    alert.getButtonTypes().setAll(okButton, noButton);
                    alert.showAndWait().ifPresent(type -> {
                        if (type == okButton) {
                            data.set(temp_recurrencias, l);
                            lis.set(temp_recurrencias, l);
                            escribir_binario_lista();
                            rellenar_listas();
                            Alert a = new Alert(AlertType.INFORMATION);
                            a.setContentText("Fue sobreescrita la lista");
                            a.show();
                            cambiar_a_tab(0);
                        }
                    });
                } else {
                    data.add(l);
                    lis.add(l);
                    escribir_binario_lista();
                    rellenar_listas();
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setContentText("Fue agregada la lista sin pistas");
                    a.show();
                    cambiar_a_tab(0);
                }
            }
        } else {
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Ha ocurrido un error, se te mostrara la tab de errores");
            a.show();
            listado_errores = par.lista_errores;
            datos.set(0, listado_errores.get(0));
            numero_error.setText("error " + 1 + " de " + listado_errores.size());
            iniciar_area_error(true);
            cambiar_a_tab(3);
        }
    }

    /**
     * Método que muestra los errores con un editor de codigo y una tabla
     *
     * @param isLista
     */
    private void iniciar_area_error(boolean isLista) {
        if (isLista) {
            CodeLista cl = new CodeLista();
            codeAreaError = cl.obtener();
            codeAreaError.replaceText(codeArea1.getText());
            if (listado_errores.get(0).getLinea() - 1 > 0) {
                codeAreaError.moveTo(codeAreaError.position(listado_errores.get(0).getLinea() - 1, 0).toOffset());
            } else {
                codeAreaError.moveTo(codeAreaError.position(0, 0).toOffset());
            }
            codeAreaError.setBackground(new Background(new BackgroundFill(Paint.valueOf("lightgray"), CornerRadii.EMPTY, Insets.EMPTY)));
            donde_error.setText("Linea: " + (codeAreaError.getCurrentParagraph() + 1) + " Columna: " + codeAreaError.getCaretColumn());
            area_error.getChildren().add(new VirtualizedScrollPane<>(codeAreaError));
        } else {
            CodePista cl = new CodePista();
            codeAreaError = cl.obtener();
            codeAreaError.replaceText(codeArea.getText());
            if (listado_errores.get(0).getLinea() - 1 > 0) {
                codeAreaError.moveTo(codeAreaError.position(listado_errores.get(0).getLinea() - 1, 0).toOffset());
            } else {
                codeAreaError.moveTo(codeAreaError.position(0, 0).toOffset());
            }
            codeAreaError.setBackground(new Background(new BackgroundFill(Paint.valueOf("lightgray"), CornerRadii.EMPTY, Insets.EMPTY)));
            donde_error.setText("Linea: " + (codeAreaError.getCurrentParagraph() + 1) + " Columna: " + codeAreaError.getCaretColumn());
            area_error.getChildren().add(new VirtualizedScrollPane<>(codeAreaError));
        }
        codeAreaError.getStylesheets().add(getClass().getResource("codeAreaError.css").toExternalForm());
        codeAreaError.setDisable(false);
        codeAreaError.setEditable(false);
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

    /**
     * Inicia la ejecución del codigo encontrado
     *
     * @param p El programa encontrado
     */
    private void iniciar(Programa p) {
        Simbolos tabla = new Simbolos();
        agregarPistas(tabla);
        tabla.agregar_sistema("$consola", "$consola", consola);
        List<Object> ls = new LinkedList<>();
        tabla.agregar(new Simbolo("$errores", "$errores", null, ls, 0));
        String res = p.interpretar_unica_cancion(tabla);
        if (res.equalsIgnoreCase("mas")) {
            Alert a = new Alert(AlertType.WARNING);
            a.setContentText("El editor está destinado solo a soportar una pista por ocasión");
            a.show();
        } else {
            Simbolo msg = tabla.obtener("$mensaje");
            Simbolo pista = tabla.obtener("$pistas");
            Pista ultima = (Pista) pista.getDatos().get(pista.getDatos().size() - 1);
            for (int i = 0; i < cans.size(); i++) {
                if (cans.get(i).getId().equalsIgnoreCase(ultima.getId())) {
                    //cans.get(i).setTexto(codeArea.getText());
                    //cans.get(i).setReproductor(ultima.getRep());
                    //cans.get(i).setDuracion(ultima.obtener_duracion());
                    break;
                }
            }
            if (comprobar_unica_pista(ultima.getId()) != -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Sobreescribir pista");
                alert.setContentText("Sobreescribir la pista " + ultima.getId() + " ?");
                ButtonType okButton = new ButtonType("Si", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(okButton, noButton);
                alert.showAndWait().ifPresent(type -> {
                    if (type == okButton) {
                        cans.get(comprobar_unica_pista(ultima.getId())).setReproductor(ultima.getRep());
                        cans.get(comprobar_unica_pista(ultima.getId())).setTexto(codeArea.getText());
                        cans.get(comprobar_unica_pista(ultima.getId())).setDuracion(ultima.obtener_duracion());
                        escribir_binario();
                        rellenar_canciones();
                        cambiar_a_tab(0);
                        if (ultima.getRep() != null) {
                            Alert a = new Alert(AlertType.INFORMATION);
                            a.setContentText("Pista modificada y reproduciendo");
                            a.show();
                            empezar_reproduccion(pista);
                        } else {
                            Alert a = new Alert(AlertType.INFORMATION);
                            a.setContentText("Pista modificada, dado que no tiene metodo principal, no se reproduce nada");
                            a.show();
                        }
                    } else if (type == noButton) {
                        Alert a = new Alert(AlertType.INFORMATION);
                        a.setContentText("No se realizó ningún cambio");
                        a.show();
                    } else {
                        System.out.println("weno saber");
                    }
                });
            } else {
                cans.add(new Cancion(ultima.getId(), ultima.obtener_duracion(), ultima.getRep(), codeArea.getText()));
                escribir_binario();
                rellenar_canciones();
                cambiar_a_tab(0);
                if (ultima.getRep() != null) {
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setContentText("Pista creada y reproduciendo");
                    a.show();
                    empezar_reproduccion(pista);
                } else {
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setContentText("Pista creada, dado que no tiene metodo principal, no se reproduce nada");
                    a.show();
                }
            }
        }
    }

    private void iniciar2(Programa p) {
        Simbolos table = new Simbolos();
        agregarPistas2(table);
        List<Object> ls = new LinkedList<>();
        table.agregar(new Simbolo("$errores", "$errores", null, ls, 0));
        table.agregar_sistema("$consola", "$consola", consola);
        table.agregar(new Simbolo("$pista", "$pista", null, new LinkedList<>(), 0));
        String res = p.interpretar2(table);
        Simbolo errs = table.obtener("$errores");
        if (errs.getDatos().isEmpty()) {
            if (res.equalsIgnoreCase("mas")) {
                Alert a = new Alert(AlertType.WARNING);
                a.setContentText("El editor está destinado solo a soportar una pista por ocasión");
                a.show();
            } else {
                Simbolo pista = table.obtener("$pista");
                Pista ultima = (Pista) pista.getDatos().get(0);
                if (comprobar_unica_pista(ultima.getId()) != -1) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Sobreescribir pista");
                    alert.setContentText("Sobreescribir la pista " + ultima.getId() + " ?");
                    ButtonType okButton = new ButtonType("Si", ButtonBar.ButtonData.YES);
                    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                    alert.getButtonTypes().setAll(okButton, noButton);
                    alert.showAndWait().ifPresent(type -> {
                        if (type == okButton) {
                            cans.get(comprobar_unica_pista(ultima.getId())).setReproductor(ultima.getRep());
                            cans.get(comprobar_unica_pista(ultima.getId())).setTexto(codeArea.getText());
                            cans.get(comprobar_unica_pista(ultima.getId())).setDuracion(ultima.obtener_duracion());
                            escribir_binario();
                            rellenar_canciones();
                            cambiar_a_tab(0);
                            if (ultima.getRep() != null) {
                                Alert a = new Alert(AlertType.INFORMATION);
                                a.setContentText("Pista modificada y reproduciendo");
                                a.show();
                                empezar_reproduccion(pista);
                            } else {
                                Alert a = new Alert(AlertType.INFORMATION);
                                a.setContentText("Pista modificada, dado que no tiene metodo principal, no se reproduce nada");
                                a.show();
                            }
                        } else if (type == noButton) {
                            Alert a = new Alert(AlertType.INFORMATION);
                            a.setContentText("No se realizó ningún cambio");
                            a.show();
                        } else {
                            System.out.println("weno saber");
                        }
                    });
                } else {
                    cans.add(new Cancion(ultima.getId(), ultima.obtener_duracion(), ultima.getRep(), codeArea.getText()));
                    escribir_binario();
                    rellenar_canciones();
                    cambiar_a_tab(0);
                    if (ultima.getRep() != null) {
                        Alert a = new Alert(AlertType.INFORMATION);
                        a.setContentText("Pista creada y reproduciendo");
                        a.show();
                        empezar_reproduccion(pista);
                    } else {
                        Alert a = new Alert(AlertType.INFORMATION);
                        a.setContentText("Pista creada, dado que no tiene metodo principal, no se reproduce nada");
                        a.show();
                    }
                }
            }
        } else {
            for (int i = 0; i < errs.getDatos().size(); i++) {
                listado_errores.add((Errores) errs.getDatos().get(i));
            }
            iniciar_area_error(false);
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Ha ocurrido un error, se te mostrara la tab de errores");
            datos.set(0, listado_errores.get(0));
            numero_error.setText("error " + 1 + " de " + listado_errores.size());
            a.show();
            cambiar_a_tab(3);
        }
    }

    private int comprobar_unica_pista(String texto) {
        for (int i = 0; i < cans.size(); i++) {
            if (cans.get(i).getId().equals(texto)) {
                return i;
            }
        }
        return -1;
    }

    private void agregarPistas(Simbolos tabla) {
        List<Object> objetos = new LinkedList<>();
        String todotexto = "";
        for (Cancion pi : cans) {
            todotexto += pi.getTexto();
        }
        if (!todotexto.isEmpty()) {
            parser p = new parser(new Lexer(new StringReader(todotexto)));
            try {
                p.parse();
                Programa pa = p.programa;
                pa.interpretar(tabla, true);
                Simbolo pistass = tabla.obtener("$pistas");
                tabla.agregar_sistema("$consola", "$consola", consola);
                tabla.removeAll(tabla);
                System.out.println("termina");
                tabla.agregar(pistass);
            } catch (Exception ex) {
                Logger.getLogger(TextoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            tabla.agregar(new Simbolo("$pistas", "$pistas", null, objetos, 0));
        }
    }

    private void agregarPistas2(Simbolos tabla) {
        ArrayList<Object> lis_pistas = new ArrayList<>();
        tabla.agregar(new Simbolo("$keep", "$keep", null, lis_pistas, 0));
        for (Cancion pi : cans) {
            if (!pi.getTexto().equalsIgnoreCase(">>Fue creada en el piano de la app movil")) {
                parser pasd = new parser(new Lexer(new StringReader(pi.getTexto())));
                try {
                    pasd.parse();
                } catch (Exception ex) {
                    System.out.println("problema con el agregarPistas2");
                }
                Programa pepe = pasd.programa;
                pepe.obtener_keeps(tabla);
            }
        }
    }

    private void leer_binario() {
        // Creamos un objeto de tipo fila para asignarle un archivo
        File archivo = new File("src/main/resources/pistas.dat");
        try {
            // Para poder leer utilizaremos un FileInputStream pasandole
            // como referencia el archivo de tipo File.
            FileInputStream fis = new FileInputStream(archivo);
            // Declaramos una variable objeto del tipo ObjectInputStream
            ObjectInputStream leer;
            // Creamos un bucle para leer la información
            // Mientras haya bytes en el archivo.
            while (fis.available() > 0) {
                leer = new ObjectInputStream(fis);
                // En una variable objeto de tipo Persona almacenaremos
                // el objeto leido de tipo Object convertido en un objeto
                // de tipo persona
                cans = (ArrayList) leer.readObject();
            }

        } catch (Exception e) {
            System.out.println("Error al leer el archivo. "
                    + e.getMessage());
            if (e.getMessage() == null) {
                escribir_binario();
            }
        }
    }

    private void escribir_binario() {
        // Creamos un objeto de tipo fila para asignarle un archivo
        File archivo = new File("src/main/resources/pistas.dat");
        try {
            // Para poder escribir utilizaremos un FileOutputStream pasandole
            // como referencia el archivo de tipo File.
            FileOutputStream fos = new FileOutputStream(archivo);

            // Y crearemos también una instancia del tipo ObjectOutputStream
            // al que le pasaremos por parámetro
            // el objeto de tipo FileOutputStream
            ObjectOutputStream escribir = new ObjectOutputStream(fos);

            // Escribimos los objetos en el archivo.
            escribir.writeObject(cans);
            // Cerramos los objetos para no consumir recursos.
            escribir.close();
            fos.close();
        } catch (Exception e) {
            System.out.println("Error al escribir en el archivo. "
                    + e.getMessage());
        }
    }

    private void leer_binario_lista() {
        // Creamos un objeto de tipo fila para asignarle un archivo
        File archivo = new File("src/main/resources/listas.dat");
        try {
            // Para poder leer utilizaremos un FileInputStream pasandole
            // como referencia el archivo de tipo File.
            FileInputStream fis = new FileInputStream(archivo);
            // Declaramos una variable objeto del tipo ObjectInputStream
            ObjectInputStream leer;
            // Creamos un bucle para leer la información
            // Mientras haya bytes en el archivo.
            while (fis.available() > 0) {
                leer = new ObjectInputStream(fis);
                // En una variable objeto de tipo Persona almacenaremos
                // el objeto leido de tipo Object convertido en un objeto
                // de tipo persona
                lis = (ArrayList) leer.readObject();
            }

        } catch (Exception e) {
            System.out.println("Error al leer el archivo. "
                    + e.getMessage());
            if (e.getMessage() == null) {
                escribir_binario_lista();
            }
        }
    }

    private void escribir_binario_lista() {
        // Creamos un objeto de tipo fila para asignarle un archivo
        File archivo = new File("src/main/resources/listas.dat");
        try {
            // Para poder escribir utilizaremos un FileOutputStream pasandole
            // como referencia el archivo de tipo File.
            FileOutputStream fos = new FileOutputStream(archivo);

            // Y crearemos también una instancia del tipo ObjectOutputStream
            // al que le pasaremos por parámetro
            // el objeto de tipo FileOutputStream
            ObjectOutputStream escribir = new ObjectOutputStream(fos);

            // Escribimos los objetos en el archivo.
            escribir.writeObject(lis);
            // Cerramos los objetos para no consumir recursos.
            escribir.close();
            fos.close();
        } catch (Exception e) {
            System.out.println("Error al escribir en el archivo. "
                    + e.getMessage());
        }
    }

    private void empezar_reproduccion(Simbolo rep) {
        Reproduccion re = ((Pista) rep.getDatos().get(rep.getDatos().size() - 1)).getRep();
        if (re != null) {
            actual = re;
            if (actual.getCanales() != null) {
                repro.setImage(new Image(App.class.getResource("pausar.png").toExternalForm()));
                cancion_escogida = actual.getId();
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
        } else {
            Alert a = new Alert(AlertType.CONFIRMATION);
            a.setContentText("Canción agregada correctamente, pero no tiene metodo main");
            a.show();
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
        if (!cancion_escogida.isEmpty()) {
            if (iniciando) {
                Reproduccion r = obtener_actual();
                if (r != null) {
                    repro.setImage(new Image(App.class.getResource("pausar.png").toExternalForm()));
                    cancion.setText(r.getId());
                    myChart.getData().clear();
                    nuevo = new Task(myChart, progreso, pasado, r);
                    nuevo.setDaemon(true);
                    nuevo.start();
                    iniciando = false;
                } else {
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setContentText("La canción elegida no tiene metodo principal, por lo tanto no se reproduce nada");
                    a.show();
                }
            } else {
                if (reproduciendo) {
                    repro.setImage(new Image(App.class.getResource("reproducir.png").toExternalForm()));
                    parar.setVisible(true);
                    nuevo.suspenderhilo();
                    reproduciendo = !reproduciendo;
                } else {
                    repro.setImage(new Image(App.class.getResource("pausar.png").toExternalForm()));
                    parar.setVisible(false);
                    nuevo.renaudarhilo();
                    reproduciendo = !reproduciendo;
                }
            }
        } else {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setContentText("Elige una canción");
            a.show();
        }
    }

    @FXML
    private void parar() {
        repro.setImage(new Image(App.class.getResource("reproducir.png").toExternalForm()));
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
        if (!cancion_escogida.isEmpty()) {
            for (int i = 0; i < data_cancion.size(); i++) {
                if (data_cancion.get(i).getId().equals(cancion_escogida) && !data_cancion.get(i).getDuracion().equalsIgnoreCase("--:--")) {
                    r = data_cancion.get(i).getReproductor();
                    pasa = true;
                    break;
                }
            }
            if (pasa) {
                repro.setImage(new Image(App.class.getResource("pausar.png").toExternalForm()));
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
            } else {
                Alert a = new Alert(AlertType.INFORMATION);
                a.setContentText("Canción seleccionada sin método main");
                a.show();
            }
        } else {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setContentText("Elige una canción primero");
            a.show();
        }
    }

    private Reproduccion obtener_actual() {
        Reproduccion r = new Reproduccion();
        boolean pasa = false;
        if (!cancion_escogida.isEmpty()) {
            for (int i = 0; i < data_cancion.size(); i++) {
                if (data_cancion.get(i).getId().equals(cancion_escogida) && !data_cancion.get(i).getDuracion().equalsIgnoreCase("--:--")) {
                    return data_cancion.get(i).getReproductor();
                }
            }
        }
        return null;
    }

    @FXML
    private void modificar() {
        int pos = -1;
        for (int i = 0; i < data_cancion.size(); i++) {
            if (data_cancion.get(i).getId().equals(cancion_escogida)) {
                pos = i;
                break;
            }
        }
        if (pos != -1) {
            codeArea.replaceText(data_cancion.get(pos).getTexto());
            cambiar_a_tab(1);
        } else {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setContentText("Canción seleccionada sin método main");
            a.show();
        }
    }

    @FXML
    private void abrir_archivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir archivo de texto");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Archivos de texto", "*.txt"));
        File archivo = fileChooser.showOpenDialog(cancion.getContextMenu());
        FileReader fr = null;
        BufferedReader br = null;
        if (archivo != null) {
            try {
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);

                // Lectura del fichero
                String linea;
                String texto_completo = "";
                while ((linea = br.readLine()) != null) {
                    texto_completo += linea + "\n";
                }
                codeArea.replaceText(texto_completo.substring(0, texto_completo.length() - 1));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TextoControlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TextoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void guardar_archivo() {
        if (!codeArea.getText().isEmpty()) {
            try {
                FileWriter myWriter = new FileWriter("predefinido.txt");
                myWriter.write(codeArea.getText());
                myWriter.close();
                Alert a = new Alert(AlertType.INFORMATION);
                a.setContentText("Se ha escrito correctamente el archivo predefinido.txt");
                a.show();
            } catch (IOException e) {
                e.printStackTrace();
                Alert a = new Alert(AlertType.INFORMATION);
                a.setContentText("Se ha escrito correctamente el archivo predefinido.txt");
                a.show();
            }
        } else {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setContentText("Escribe antes de guardar");
            a.show();
        }
    }

    @FXML
    private void limpiar_texto() {
        codeArea.replaceText("");
    }

    @FXML
    private void abrir_archivo2() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir archivo de texto");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Archivos de texto", "*.txt"));
        File archivo = fileChooser.showOpenDialog(cancion.getContextMenu());
        FileReader fr = null;
        BufferedReader br = null;
        if (archivo != null) {
            try {
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);

                // Lectura del fichero
                String linea;
                String texto_completo = "";
                while ((linea = br.readLine()) != null) {
                    texto_completo += linea + "\n";
                }
                codeArea1.replaceText(texto_completo.substring(0, texto_completo.length() - 1));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TextoControlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TextoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void guardar_archivo2() {
        if (!codeArea1.getText().isEmpty()) {
            try {
                FileWriter myWriter = new FileWriter("predefinido_lista.txt");
                myWriter.write(codeArea1.getText());
                myWriter.close();
                Alert a = new Alert(AlertType.INFORMATION);
                a.setContentText("Se ha escrito correctamente el archivo predefinido_lista.txt");
                a.show();
            } catch (IOException e) {
                e.printStackTrace();
                Alert a = new Alert(AlertType.INFORMATION);
                a.setContentText("Se ha escrito correctamente el archivo predefinido_lista.txt");
                a.show();
            }
        } else {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setContentText("Escribe antes de guardar");
            a.show();
        }
    }

    @FXML
    private void limpiar_texto2() {
        codeArea1.replaceText("");
    }

    private void cambiar_a_tab(int tab) {
        SingleSelectionModel<Tab> selectionModel = tabs.getSelectionModel();
        selectionModel.select(tab);
    }

    private void llenar_canciones() {
        leer_binario();
        data_cancion.clear();
        for (Cancion pi : cans) {
            data_cancion.add(pi);
        }
        if (cans.isEmpty()) {
            data_cancion.add(new Cancion("No has creado ninguna", "--:--", null, ""));
        }
    }

    private void rellenar_canciones() {
        data_cancion.clear();
        for (Cancion pi : cans) {
            data_cancion.add(pi);
        }
        if (cans.isEmpty()) {
            data_cancion.add(new Cancion("No has creado ninguna", "--:--", null, ""));
        } else {
            canciones.setItems(data_cancion);
        }
    }

    private void rellenar_listas() {
        leer_binario_lista();
        data.clear();
        for (Lista pi : lis) {
            data.add(pi);
        }
        if (lis.isEmpty()) {
            data.add(new Lista("No has creado ninguna", null, false, false));
        } else {
            listas.setItems(data);
        }
    }

    @FXML
    private void eliminar_pista() {
        int pos = -1;
        for (int i = 0; i < data_cancion.size(); i++) {
            if (data_cancion.get(i).getId().equals(cancion_escogida)) {
                pos = i;
                break;
            }
        }
        if (pos > 0) {
            data_cancion.remove(pos);
            cans.remove(pos);
            escribir_binario();
            String nombre = "";
            for (int i = 0; i < lis.size(); i++) {
                if (lis.get(i).getCanciones() != null) {
                    if (lis.get(i).getCanciones().contains(cancion_escogida)) {
                        for (int j = 0; j < lis.get(i).getCanciones().size(); j++) {
                            if (lis.get(i).getCanciones().get(j).equals(cancion_escogida)) {
                                lis.get(i).getCanciones().remove(j);
                                nombre = lis.get(i).getId();
                                break;
                            }
                        }
                    }
                }
            }
            escribir_binario_lista();
            rellenar_listas();
            rellenar_canciones_lista(nombre);
        } else if (pos == 0) {
            if (cans.size() > 1) {
                data_cancion.remove(pos);
                cans.remove(pos);
                escribir_binario();
            } else {
                data_cancion.add(new Cancion("No has creado ninguna", "--:--", null, ""));
                data_cancion.remove(0);
                cans.remove(pos);
                escribir_binario();
            }
            String nombre = "";
            for (int i = 0; i < lis.size(); i++) {
                if (lis.get(i).getCanciones() != null) {
                    if (lis.get(i).getCanciones().contains(cancion_escogida)) {
                        for (int j = 0; j < lis.get(i).getCanciones().size(); j++) {
                            if (lis.get(i).getCanciones().get(j).equals(cancion_escogida)) {
                                lis.get(i).getCanciones().remove(j);
                                nombre = lis.get(i).getId();
                                break;
                            }
                        }
                    }
                }
            }
            escribir_binario_lista();
            rellenar_listas();
            rellenar_canciones_lista(nombre);
        } else {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setContentText("Elige primero una canción a eliminar");
            a.show();
        }
    }

    @FXML
    private void eliminar_lista() {
        int pos = -1;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(lista_escogida)) {
                pos = i;
                break;
            }
        }
        if (pos > 0) {
            data.remove(pos);
            lis.remove(pos);
            escribir_binario_lista();
        } else if (pos == 0) {
            if (data.size() > 1) {
                data.remove(pos);
                lis.remove(pos);
                escribir_binario_lista();
            } else {
                data.add(new Lista("No has creado ninguna lista", null, false, false));
                data.remove(0);
                lis.remove(pos);
                escribir_binario_lista();
            }
        } else {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setContentText("Elige primero una lista a eliminar");
            a.show();
        }
        data_lista_cancion.clear();
    }

    @FXML
    private void anterior_error() {
        if (!listado_errores.isEmpty()) {
            if (error_actual == 0) {
                error_actual = listado_errores.size() - 1;
                datos.set(0, listado_errores.get(error_actual));
                numero_error.setText("error " + (error_actual + 1) + " de " + listado_errores.size());
                donde_error.setText("Linea: " + listado_errores.get(error_actual).getLinea() + " Columna: " + listado_errores.get(error_actual).getColumna());
                if (listado_errores.get(error_actual).getLinea() - 1 > 0) {
                    codeAreaError.moveTo(codeAreaError.position(listado_errores.get(error_actual).getLinea() - 1, 0).toOffset());
                } else {
                    codeAreaError.moveTo(codeAreaError.position(0, 0).toOffset());
                }
            } else {
                error_actual--;
                datos.set(0, listado_errores.get(error_actual));
                numero_error.setText("error " + (error_actual + 1) + " de " + listado_errores.size());
                donde_error.setText("Linea: " + listado_errores.get(error_actual).getLinea() + " Columna: " + listado_errores.get(error_actual).getColumna());
                if (listado_errores.get(error_actual).getLinea() - 1 > 0) {
                    codeAreaError.moveTo(codeAreaError.position(listado_errores.get(error_actual).getLinea() - 1, 0).toOffset());
                } else {
                    codeAreaError.moveTo(codeAreaError.position(0, 0).toOffset());
                }
            }
        }
    }

    @FXML
    private void siguiente_error() {
        if (!listado_errores.isEmpty()) {
            if (error_actual == listado_errores.size() - 1) {
                error_actual = 0;
                datos.set(0, listado_errores.get(error_actual));
                numero_error.setText("error " + (error_actual + 1) + " de " + listado_errores.size());
                donde_error.setText("Linea: " + listado_errores.get(error_actual).getLinea() + " Columna: " + listado_errores.get(error_actual).getColumna());
                if (listado_errores.get(error_actual).getLinea() - 1 > 0) {
                    codeAreaError.moveTo(codeAreaError.position(listado_errores.get(error_actual).getLinea() - 1, 0).toOffset());
                } else {
                    codeAreaError.moveTo(codeAreaError.position(0, 0).toOffset());
                }
            } else {
                error_actual++;
                datos.set(0, listado_errores.get(error_actual));
                numero_error.setText("error " + (error_actual + 1) + " de " + listado_errores.size());
                donde_error.setText("Linea: " + listado_errores.get(error_actual).getLinea() + " Columna: " + listado_errores.get(error_actual).getColumna());
                if (listado_errores.get(error_actual).getLinea() - 1 > 0) {
                    codeAreaError.moveTo(codeAreaError.position(listado_errores.get(error_actual).getLinea() - 1, 0).toOffset());
                } else {
                    codeAreaError.moveTo(codeAreaError.position(0, 0).toOffset());
                }
            }
        }
    }
}
