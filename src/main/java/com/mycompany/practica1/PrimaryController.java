package com.mycompany.practica1;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.web.HTMLEditor;

public class PrimaryController {

    @FXML
    public HTMLEditor htmlEditor;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void obtener_texto() throws IOException {
        System.out.println(getText(htmlEditor.getHtmlText()) + "");
    }

    public static String getText(String htmlText) {

        String result = "";

        Pattern pattern = Pattern.compile("<[^>]*>");
        Matcher matcher = pattern.matcher(htmlText);
        final StringBuffer text = new StringBuffer(htmlText.length());

        while (matcher.find()) {
            matcher.appendReplacement(
                    text,
                    " ");
        }

        matcher.appendTail(text);

        result = text.toString().trim();

        return result;
    }
}
