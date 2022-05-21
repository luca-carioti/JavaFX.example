package com.luca.bibliotecac;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class BibliotecaC extends Application {
    private static Stage mainStage;
    private static BorderPane homeView;
    private static BorderPane menuView;
    private static Stage addStage;
    @Override
    public void start(Stage stage) throws Exception {
        mainStage=new Stage();
        mainStage.setTitle("Biblioteca");
        mainStage.setResizable(true);
        showHomeView();
        showMenuView();
    }


    public static void showMenuView() throws IOException{
        FXMLLoader loader=new FXMLLoader(BibliotecaC.class.getResource("MenuPane.fxml"));
        menuView=loader.load();
        homeView.setCenter(menuView);
    }

    public static void showHomeView() throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(BibliotecaC.class.getResource("HomePane.fxml"));
        homeView=fxmlLoader.load();
        mainStage=new Stage();
        Scene scene=new Scene(homeView);
        mainStage.setScene(scene);
        mainStage.show();
    }
    public static void showMainView() throws IOException{
        FXMLLoader loader=new FXMLLoader(BibliotecaC.class.getResource("MainView.fxml"));
        TabPane mainView = loader.load();
        homeView.setCenter(mainView);
    }





    public static void openAddDialog() throws IOException{
        BorderPane addDialog;
        FXMLLoader loader=new FXMLLoader(BibliotecaC.class.getResource("AddPane.fxml"));
        addDialog=loader.load();
        addStage=new Stage();
        Scene scene=new Scene(addDialog);
        addStage.setScene(scene);
        addStage.initModality(Modality.WINDOW_MODAL);
        addStage.initOwner(mainStage);
        addStage.setResizable(false);
        addStage.showAndWait();

    }

    public static void openAddGDialog() throws IOException{
        BorderPane addDialog;
        FXMLLoader loader=new FXMLLoader(BibliotecaC.class.getResource("AddPaneG.fxml"));
        addDialog=loader.load();
        addStage=new Stage();
        Scene scene=new Scene(addDialog);
        addStage.setScene(scene);
        addStage.initModality(Modality.WINDOW_MODAL);
        addStage.initOwner(mainStage);
        addStage.setResizable(false);
        addStage.showAndWait();
    }

    public static void openUpdateDialog() throws IOException{
        BorderPane addDialog;
        FXMLLoader loader=new FXMLLoader(BibliotecaC.class.getResource("UpdatePane.fxml"));
        addDialog=loader.load();
        Stage stage=new Stage();
        Scene scene=new Scene(addDialog);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);
        stage.showAndWait();
    }

    public static void openUpdateGDialog() throws IOException{
        BorderPane addDialog;
        FXMLLoader loader=new FXMLLoader(BibliotecaC.class.getResource("UpdatePaneG.fxml"));
        addDialog=loader.load();
        Stage stage=new Stage();
        Scene scene=new Scene(addDialog);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);
        stage.showAndWait();
    }





    private static void showCodiceWarning() throws IOException {
        BorderPane addDialog;
        FXMLLoader loader=new FXMLLoader(BibliotecaC.class.getResource("CodiceWarning.fxml"));
        addDialog=loader.load();
        Stage stage=new Stage();
        Scene scene=new Scene(addDialog);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(addStage);
        stage.showAndWait();
    }

    public static void showSearchDialog() throws IOException{
        BorderPane addDialog;
        FXMLLoader loader=new FXMLLoader(BibliotecaC.class.getResource("SearchViewBook.fxml"));
        addDialog=loader.<BorderPane>load();
        Stage stage=new Stage();
        Scene scene=new Scene(addDialog);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);
        stage.showAndWait();
    }


    public static void main(String...args){
        launch(args);
    }
}
