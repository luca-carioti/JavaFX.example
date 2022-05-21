package com.luca.bibliotecac;

import com.luca.entities.Genere;
import com.luca.exceptions.GenereAlreadyExistException;
import com.luca.exceptions.LetteraAlreadyExistException;
import com.luca.repository.DbGenereDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.sql.SQLException;

public class AddGController {
    @FXML
    private TextField nomeText;
    @FXML
    private TextField letteraText;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label errorLabel;
    private final DbGenereDao dbGenereDao=new DbGenereDao();


    public void aggiungi(ActionEvent actionEvent) {
        try {
            if (!dbGenereDao.existByName(nomeText.getText()) && !(dbGenereDao.existByLettera(letteraText.getText()))) {
                dbGenereDao.add(new Genere(nomeText.getText(), letteraText.getText()));
            }
        }catch(GenereAlreadyExistException e){errorLabel.setText("Il genere già esiste !");
        errorLabel.setTextFill(Color.RED);} catch (SQLException e) {
            e.printStackTrace();
        } catch (LetteraAlreadyExistException e) {
            errorLabel.setText("La lettera scelta esiste!");
            errorLabel.setTextFill(Color.RED);
        }catch (Exception e){e.printStackTrace();}
    }

    public void pulisci(ActionEvent actionEvent) {
        nomeText.clear();
        letteraText.clear();
    }
}
