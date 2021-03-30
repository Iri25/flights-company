import controller.MainController;
import domain.*;
import domain.validators.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.Repository;
import repository.file.*;
import service.Service;

import java.io.IOException;

public class MainApp extends Application {

    Repository<String, Client> clientRepository;
    Repository<Long, Flight> flightRepository;
    Repository<Long, Ticket> ticketRepository;

    Service service;

    @Override
    public void start(Stage primaryStage) throws Exception {

        String fileNameClient = "data/clients.txt";
        String fileNameFlight = "data/flights.txt";
        String fileNameTicket = "data/tickets.txt";

        clientRepository = new ClientFile(fileNameClient, new ClientValidator());
        flightRepository = new FlightFile(fileNameFlight, new FlightValidator());
        ticketRepository = new TicketFile(fileNameTicket, new TicketValidator());

        service = new Service(clientRepository, flightRepository, ticketRepository);

        Stage stage = new Stage();
         try{
             initWindow(stage);
             stage.setTitle("Login");
             stage.show();
         }
         catch(IOException ioException){
             ioException.printStackTrace();
         }
    }

    private void initWindow(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("views/mainview.fxml"));
        AnchorPane layout = loader.load();
        primaryStage.setScene(new Scene(layout));

        MainController mainController = loader.getController();
        mainController.setService(service);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
