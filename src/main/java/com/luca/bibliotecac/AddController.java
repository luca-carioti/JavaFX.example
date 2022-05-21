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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class AddController implements Initializable {
    @FXML
    private TextField titoloText;
    @FXML
    private TextField autoreText;
    @FXML
    private ComboBox<Genere> genereCombo;
    @FXML
    private TextField codiceText;
    @FXML
    private TextField casaText;
    @FXML
    private Label errorLabel;
    private Genere selezionato;

    private DbLibroDao dbLibroDao=new DbLibroDao();
    private DbGenereDao dbGenereDao=new DbGenereDao();

    public String ricavaCodice(String genere) throws Exception{
        int id=dbGenereDao.getCodiceByName(genere);

        ArrayList<Libro> libri= dbLibroDao.getByGenere(id);
        if(libri.size()!=0){
        String codVecchio=libri.get(libri.size()-1).getCodLib();
        StringTokenizer str=new StringTokenizer(codVecchio," ");

        if(str.countTokens()==2){
            String lettera=str.nextToken();
            String numero=str.nextToken();
            int num=Integer.parseInt(numero)+1;
            return lettera+" "+num;
        }
        else{
            return "Codice illegibile";
        }
       }
        else{
            return "Niente";
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Genere> generi=new ArrayList<>();
        try {
             generi=dbGenereDao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        genereCombo.getItems().addAll(generi);
        genereCombo.getSelectionModel().select(0);
        genereCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Genere>() {
            @Override
            public void changed(ObservableValue<? extends Genere> observableValue, Genere genere, Genere t1) {
                selezionato=genereCombo.getSelectionModel().getSelectedItem();
                try {
                    codiceText.setText(ricavaCodice(selezionato.getNome()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private boolean compilati(){
        return (!(titoloText.getText().equals("") || titoloText.getText().equals(" ")) &&
                !(autoreText.getText().equals("") || autoreText.getText().equals(" ")) &&
                !(casaText.getText().equals("") || casaText.getText().equals(" ")) &&
                !(codiceText.getText().equals("") || codiceText.equals(" ")));
    }

    private boolean codValido(String codice) throws Exception {
        return dbLibroDao.getById(codice)==null;
    }

    public void aggiungi(ActionEvent actionEvent) throws Exception {
        Libro libro;
        String codice=codiceText.getText();
        if(compilati() && codValido(codice)){
            libro=new Libro(titoloText.getText(),autoreText.getText(),casaText.getText(),codiceText.getText(),selezionato.getCodice(),0);
            dbLibroDao.add(libro);
            errorLabel.setText(codice + " aggiunto correttamente !");
            errorLabel.setTextFill(Color.GREEN);
        }else{
            errorLabel.setText("Dati non corretti");
            errorLabel.setTextFill(Color.RED);
        }
    }

    public void pulisci(ActionEvent actionEvent) {
        titoloText.clear();
        autoreText.clear();
        casaText.clear();
    }
}
