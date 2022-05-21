package com.luca.bibliotecac;


import com.luca.entities.Genere;
import com.luca.entities.Libro;
import com.luca.repository.DbGenereDao;
import com.luca.repository.DbLibroDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateViewContorller implements Initializable {
    @FXML
    private TextField autoreText;
    @FXML
    private TextField titoloText;
    @FXML
    private TextField casaText;
    @FXML
    private ComboBox<Genere> genereCombo;
    @FXML
    private TextField codiceText;
    @FXML
    private Button updateButton;
    @FXML
    private Button clearButton;
    @FXML
    private Label resultLabel;
    private DbGenereDao dbGenereDao=new DbGenereDao();
    private Genere genSelezionato;
    private final DbLibroDao dbLibroDao=new DbLibroDao();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Libro selezionato= MainViewController.selezionato;
        Genere genere;
        autoreText.setText(selezionato.getAutore());
        titoloText.setText(selezionato.getTitolo());
        casaText.setText(selezionato.getCasa_edi());
        codiceText.setText(selezionato.getCodLib());

        try {
            genereCombo.getItems().addAll(dbGenereDao.getAll());
            genereCombo.getSelectionModel().select(0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        genSelezionato=genereCombo.getItems().get(0);
        genereCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Genere>() {
            @Override
            public void changed(ObservableValue<? extends Genere> observableValue, Genere genere, Genere t1) {
                genSelezionato=genereCombo.getSelectionModel().getSelectedItem();
            }
        });


    }

    public void modifica(ActionEvent actionEvent) {
        int codice=MainViewController.selezionato.getCodice();
        String titolo=titoloText.getText();
        String autore= autoreText.getText();
        String casa=casaText.getText();
        int genere=genSelezionato.getCodice();
        String codLib=codiceText.getText();
        try {
            dbLibroDao.update(new Libro(titolo,autore,casa,codLib,genere,codice));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void clear(ActionEvent actionEvent) {
        autoreText.clear();
        titoloText.clear();
        casaText.clear();
        codiceText.clear();
    }
}
