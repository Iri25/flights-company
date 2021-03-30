package controller;

import domain.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.Service;
import utils.events.Event;
import utils.observer.Observer;

import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class MainController implements Observer {

    @FXML
    private TextField usernameTextField;

    @FXML
    private Label WelcomeLabel;

    @Override
    public void update(Event e) {

    }

    Service service;

    public void setService(Service service) {
        this.service = service;

        service.addObserver( this);
        WelcomeLabel.setText("Welcome! ");
    }


    @FXML
    public void handleLogin() throws IOException {
        String username = usernameTextField.getText();
        if ((username.isEmpty()))
            MessageAlert.showErrorMessage(null, "You must insert a username!\n");
        else {
            List<Client> login = service.Login(username);
            if (login.isEmpty())
                MessageAlert.showErrorMessage(null, "Username invalid!\n");
            else {
                Stage stage = new Stage();
                Stage addClientStage = new Stage();
                for (Client client:login) {
                    stage.setTitle(client.getName());

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/views/dataview.fxml"));
                    AnchorPane layout = loader.load();
                    stage.setScene(new Scene(layout));

                    DataController dataController = loader.getController();
                    dataController.setService(service, client);
                    stage.show();
                }
            }
        }
    }

}
