package com.luca.bibliotecac;

import javafx.event.ActionEvent;

import java.io.IOException;

public class HomeController {
    public void goHome(ActionEvent actionEvent) throws IOException {
        BibliotecaC.showMenuView();
    }
}
