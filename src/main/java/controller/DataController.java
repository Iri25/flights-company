package controller;

import domain.Client;
import domain.Entity;
import domain.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import repository.paging.Pageable;
import repository.paging.PageableImplementation;
import repository.paging.Pager;
import service.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javafx.scene.control.*;
import utils.events.Event;
import utils.observer.Observable;
import utils.observer.Observer;

public class DataController {

    @FXML
    private DatePicker startDay;

    @FXML
    private ComboBox<String> fromComboBox;

    @FXML
    private ComboBox<String> toComboBox;

    @FXML
    private Pagination pagination;
    private final int pageSize = 5;

    @FXML
    private TableView<Flight> flightTableView;
    @FXML
    private TableColumn<Flight, String> id;
    @FXML
    private TableColumn<Flight, String> from;
    @FXML
    private TableColumn<Flight, String> to;
    @FXML
    private TableColumn<Flight, String> departureLong;
    @FXML
    private TableColumn<Flight, String> landingTime;
    @FXML
    private TableColumn<Flight, String> seats;
    @FXML
    private TableColumn<Flight, String> seatsAvailable;

    Service service;
    Client client;

    ObservableList<Flight> modelFlight = FXCollections.observableArrayList();
    ObservableList<String> modelFrom = FXCollections.observableArrayList();
    ObservableList<String> modelTo = FXCollections.observableArrayList();

    public void setService(Service service, Client client) {
        this.service = service;
        this.client = client;

        initToModel();
        initFromModel();
    }

    @FXML
    public void initialize() {
        initializeFromComboBox();
        initializeToComboBox();
    }
    private void initializeFromComboBox() {
        fromComboBox.setItems(modelFrom);
    }

    private void initializeToComboBox() {
        toComboBox.setItems(modelTo);
    }

    private void initFromModel() {
        List<String> from = new ArrayList<String>();
        for (Flight flight : service.getAllFligts()) {
            from.add(flight.getFrom());
        }
        modelFrom.setAll(from);
    }

    private void initToModel() {
        List<String> to = new ArrayList<String>();
        for (Flight flight : service.getAllFligts()) {
            to.add(flight.getTo());
        }
        modelTo.setAll(to);
    }

    private void initializeFlightTable() {
        id.setCellValueFactory(new PropertyValueFactory<Flight, String>("IdString"));
        from.setCellValueFactory(new PropertyValueFactory<Flight, String>("FromString"));
        to.setCellValueFactory(new PropertyValueFactory<Flight, String>("ToString"));
        departureLong.setCellValueFactory(new PropertyValueFactory<Flight, String>("DepartureLongString"));
        landingTime.setCellValueFactory(new PropertyValueFactory<Flight, String>("LandingTimeString"));
        seats.setCellValueFactory(new PropertyValueFactory<Flight, String>("SeatsString"));
        seatsAvailable.setCellValueFactory(new PropertyValueFactory<Flight, String>("SeatsAvailableString"));
        flightTableView.setItems(modelFlight);

    }

    private void initFlightModel(LocalDate fromDate, String from, String to) {
        if (fromDate == null)
            MessageAlert.showErrorMessage(null, "You must select a date!\n");
        else {
            List<Flight> flights = service.getFlights(fromDate, from, to);
            List<Flight> flightsUpdate = new ArrayList<>();
            for (Flight flight: flights) {
                if(flight.getSeats() == flight.getSeatsAvailable())
                    flight.setSeatsAvailable(service.getSeatsAvailable(flight));
                else
                    flight.setSeatsAvailable(service.getSeatsAvailableUpdate(flight));
                flightsUpdate.add(flight);
            }
            modelFlight.setAll(flightsUpdate);
        }
    }

    @FXML
    public void handleSearch() {
        LocalDate fromDate = startDay.getValue();
        String from = fromComboBox.getValue();
        String to = toComboBox.getValue();
        initFlightModel(fromDate, from, to);
        initializeFlightTable();

        populatePages(service.getFlights(fromDate, from, to));
    }

    @FXML
    public void handleBuy() {
        Flight flightSelected = flightTableView.getSelectionModel().getSelectedItem();
        System.out.println("zbor" + flightSelected.toString());
        service.buyTickets(client, flightSelected);

        LocalDate fromDate = startDay.getValue();
        String from = fromComboBox.getValue();
        String to = toComboBox.getValue();
        initFlightModel(fromDate, from, to);
        initializeFlightTable();
    }

    public void populatePages(Iterable<Flight> contentPages){
        Iterable<Flight> flightIterable = contentPages;
        List<Flight> flightList = StreamSupport.stream(flightIterable.spliterator(), false).collect(Collectors.toList());
        //pagination.setPageCount((flightList.size() / pageSize) + 1);
        pagination.setPageCount(pageSize);
        for(int i = 0; i < pagination.getMaxPageIndicatorCount(); i++){
            pagination.setPageFactory((pageIndex) -> {
                Pageable pageable = new PageableImplementation(pageIndex, pageSize);
                Pager<Flight> pager = new Pager<>(pageable, contentPages);
                List<Flight> flights = StreamSupport.stream(pager.paged().getContent().spliterator(), false).collect(Collectors.toList());
                modelFlight.setAll(flights);
                flightTableView.setItems(modelFlight);
                return flightTableView;
            });
        }
    }
}