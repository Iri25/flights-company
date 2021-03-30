package service;

import domain.*;
import repository.Repository;
import utils.events.Event;
import utils.observer.Observable;
import utils.observer.Observer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service implements Observable {

    private Repository<String, Client> clientRepository;
    private Repository<Long, Flight> flightRepository;
    private Repository<Long, Ticket> ticketRepository;

    /**
     * constructor with parameters
     * @param clientRepository
     * @param flightRepository
     * @param ticketRepository
     */
    public Service(Repository<String, Client> clientRepository, Repository<Long, Flight> flightRepository, Repository<Long, Ticket> ticketRepository) {
        this.clientRepository = clientRepository;
        this.flightRepository = flightRepository;
        this.ticketRepository = ticketRepository;

    }

    private List<Observer> observers = new ArrayList<Observer>();

    @Override
    public void addObserver(Observer e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(Event t) {
        observers.stream().forEach(x -> x.update(t));
    }

    public Iterable<Client> getAllClients() { return clientRepository.findAll(); }

    public Iterable<Flight> getAllFligts() {
        return flightRepository.findAll();
    }

    public Iterable<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Client getOneClient(String id) {
        return clientRepository.findOne(id);
    }

    public  List<Client> Login(String username) {
         return StreamSupport.stream(getAllClients().spliterator(), false)
                 .filter(x -> x.getId().equals(username))
                .collect(Collectors.toList());
    }

    public List<Flight> getFlights(LocalDate fromDate, String from, String to) {
        return StreamSupport.stream(getAllFligts().spliterator(), false)
                .filter(x -> x.getDepartureLong().compareTo(fromDate) >= 0)
                .filter(x -> x.getFrom().equals(from))
                .filter(x -> x.getTo().equals(to))
                .collect(Collectors.toList());
    }

    public void buyTickets(Client client, Flight flight){
        Long freeId = 0L;
        for(Ticket ticket: getAllTickets()) {
            freeId++;
            if (!freeId.equals(ticket.getId())) {
                break;
            }
        }
        freeId++;

        Ticket ticketBuy = new Ticket(client.getId(), flight.getId(), LocalDateTime.now());
        ticketBuy.setId(freeId);
        ticketRepository.save(ticketBuy);

    }

    public Integer getSeatsAvailable(Flight flight) {
        Iterable<Ticket> tickets = getAllTickets();
        int seatUnavailable = 0;
        for (Ticket ticket:tickets) {
            if(ticket.getFlightId().equals(flight.getId()))
                seatUnavailable ++;
        }
        flight.setSeatsAvailable(flight.getSeatsAvailable() - seatUnavailable);
        return flight.getSeatsAvailable();
    }

    public Integer getSeatsAvailableUpdate(Flight flight) {
        flight.setSeatsAvailable(flight.getSeatsAvailable() - 1);
        return flight.getSeatsAvailable();
    }

}
