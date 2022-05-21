package com.luca.bibliotecac;

import com.luca.entities.Genere;
import com.luca.exceptions.GenereAlreadyExistException;
import com.luca.exceptions.LetteraAlreadyExistException;
import com.luca.repository.DbGenereDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateGController implements Initializable {
    @FXML
    private TextField nomeText;
    @FXML
    private TextField letteraText;
    @FXML
    private Button modifyButton;
    @FXML
    private Button clearButton;
    @FXML
    private Label resultLabel;
    private final DbGenereDao dbGenereDao=new DbGenereDao();

    public void modifica(ActionEvent actionEvent) {
        String nome=nomeText.getText();
        String lettera=letteraText.getText();
        int codice=MainViewController.genListaSelezionato.getCodice();
        try {
            dbGenereDao.update(new Genere(codice,nome,lettera));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void clear(ActionEvent actionEvent) {
        nomeText.clear();
        letteraText.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomeText.setText(MainViewController.genListaSelezionato.getNome());
        letteraText.setText(MainViewController.genListaSelezionato.getLettera());
    }
}
