package com.luca.bibliotecac;



import com.luca.entities.Genere;
import com.luca.entities.Libro;
import com.luca.repository.DbGenereDao;
import com.luca.repository.DbLibroDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class MainViewController implements Initializable {

    private final DbLibroDao dbLibroDao = new DbLibroDao();
    private final DbGenereDao dbGenereDao=new DbGenereDao();
    public static Libro selezionato;
    @FXML
    private  TableView<Libro> table;
    @FXML
    private TableColumn<Libro, String> titoloCol;
    @FXML
    private TableColumn<Libro, String> autoreCol;
    @FXML
    private TableColumn<Libro, String> casaCol;
    @FXML
    private TableColumn<Libro, String> codiceCol;
    @FXML
    private ComboBox<Genere> comboVisual;
    private Genere genSelezionato;
    @FXML
    private TextField searchBar;
    private ArrayList<Libro> libri=new ArrayList<>();
    private ArrayList<Genere> generiL=new ArrayList<>();
    @FXML
    private ListView<Genere> genereStart;
    @FXML
    private Button addGButton;
    @FXML
    private Button deleteGButton;
    @FXML
    private Button modifyGButton;
    public static Genere genListaSelezionato;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titoloCol.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        autoreCol.setCellValueFactory(new PropertyValueFactory<>("autore"));
        casaCol.setCellValueFactory(new PropertyValueFactory<>("casa_edi"));
        codiceCol.setCellValueFactory(new PropertyValueFactory<>("codLib"));
        Genere def=new Genere("TUTTI","e");
        ///comboVisual.setValue(def);
        comboVisual.getItems().add(def);
        comboVisual.getSelectionModel().select(0);
        genSelezionato=def;
        try {
            comboVisual.getItems().addAll(dbGenereDao.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            libri=dbLibroDao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        table.setItems(FXCollections.observableList(libri));
        SelectionModel<Libro> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((observableValue, libro, t1) -> {
            selezionato = selectionModel.getSelectedItem();
        });
        comboVisual.getSelectionModel().selectedItemProperty().addListener((observableValue, genere, t1) -> {
            //ArrayList<Libro> libri1 =new ArrayList<>();
            genSelezionato=comboVisual.getSelectionModel().getSelectedItem();
            if (genSelezionato.getNome().equals("TUTTI")){
                try {
                    libri =dbLibroDao.getAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    libri = dbLibroDao.getByGenere(genSelezionato.getCodice());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            table.setItems(FXCollections.observableList(libri));
        });

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Libro> observableList = FXCollections.observableList(libri);
            FilteredList<Libro> searchResult= new FilteredList( observableList, b-> true);
            searchResult.setPredicate(libro->{
                //se non si vuole cercare nulla quindi si mostrano tutti i risultati
                if(newValue == null || newValue.isEmpty() || newValue.isBlank()){
                    return true;
                }

                String searchKey= newValue.toLowerCase();

                if(libro.getTitolo().toLowerCase().indexOf(searchKey) >-1){
                    return true; //abbiamo trovato una corrispondenza nei libri tramite il titolo
                } else if(libro.getAutore().toLowerCase().indexOf(searchKey)>-1){
                    return true;
                }else if(libro.getCasa_edi().toLowerCase().indexOf(searchKey)>-1){
                    return true;
                }else if(libro.getCodLib().toLowerCase().indexOf(searchKey)>-1){
                    return true;
                }else {
                    return false; //nessuna corrispondenza è stata trovata
                }
            });
            SortedList<Libro> sortedResult= new SortedList<>(searchResult);
            sortedResult.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedResult);
        });

        try {
            generiL=dbGenereDao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        genereStart.setItems(FXCollections.observableList(generiL));
        genereStart.getSelectionModel().selectedItemProperty().addListener((observableValue, genere, t1) -> {
            genListaSelezionato=genereStart.getSelectionModel().getSelectedItem();
        });

    }

    public void ricarica() throws Exception{
        if(!genSelezionato.getNome().equals("TUTTI")){
            libri= dbLibroDao.getByGenere(genSelezionato.getCodice());
            table.setItems(FXCollections.observableList(libri));
        }else{
            libri= dbLibroDao.getAll();
            table.setItems(FXCollections.observableList(libri));
        }
    }

    public void showAddView(ActionEvent actionEvent) throws IOException {
        BibliotecaC.openAddDialog();
    }

    public void elimina(ActionEvent actionEvent) throws Exception {
        if(selezionato!=null){
        dbLibroDao.delete(selezionato);
        ricarica();}
    }

    public void openUpdateView(ActionEvent actionEvent) throws IOException {
        if(selezionato!=null){
        BibliotecaC.openUpdateDialog();}
    }

    public void showAddGView() throws IOException{
        BibliotecaC.openAddGDialog();
    }

    public void openUpdateGView() throws IOException{
        if (genListaSelezionato!=null){
            BibliotecaC.openUpdateGDialog();
        }
    }


    public void deleteG(ActionEvent actionEvent) throws Exception {
        if (genListaSelezionato!=null) {
            dbGenereDao.delete(genListaSelezionato);
        }
    }
}
