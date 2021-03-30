package service;

import domain.Flight;
import repository.paging.Page;
import repository.paging.Pageable;
import repository.paging.PageableImplementation;
import repository.paging.PagingRepository;
import utils.events.ChangeEventType;
import utils.events.FlightChangeEvent;
import utils.observer.Observable;
import utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FlightService implements Observable<FlightChangeEvent> {
    private PagingRepository<Long, Flight> pagingRepository;

    public FlightService(PagingRepository<Long, Flight> pagingRepository) {

        this.pagingRepository = pagingRepository;
    }

    private <T> Iterable <T> filter(Iterable <T> list, Predicate<T> cond)
    {
        List<T> result=new ArrayList<>();
        list.forEach((T x)->{if (cond.test(x)) result.add(x);});
        return result;
    }

    public Flight addFlight(Flight messageTask) {
        Flight flight = pagingRepository.save(messageTask);
        if(flight == null) {
            notifyObservers(new FlightChangeEvent(ChangeEventType.ADD,flight));
        }
        return flight;
    }

    public Flight deleteFlight(Flight flight){
        Flight task = pagingRepository.delete(flight.getId());
        if(task != null) {
            notifyObservers(new FlightChangeEvent(ChangeEventType.DELETE, task));
        }
        return task;
    }

    public Flight updateFlight(Flight flightNew) {
        Flight flightOld = pagingRepository.findOne(flightNew.getId());
        if(flightOld != null) {
            Flight flighUpdate = pagingRepository.update(flightNew);
            notifyObservers(new FlightChangeEvent(ChangeEventType.UPDATE, flightNew, flightOld));
            return flighUpdate;
        }
        return flightOld;
    }

    public Iterable<Flight> getAll(){
        return pagingRepository.findAll();
    }

    private List<Observer<FlightChangeEvent>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<FlightChangeEvent> e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<FlightChangeEvent> e) {
        //observers.remove(e);
    }

    @Override
    public void notifyObservers(FlightChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }

    private int page = 0;
    private int size = 1;
    private Pageable pageable;

    public void setPageSize(int size) {
        this.size = size;
    }

//    public void setPageable(Pageable pageable) {
//        this.pageable = pageable;
//    }

    public Set<Flight> getNextFlight() {
//        Pageable pageable = new PageableImplementation(this.page, this.size);
//        Page<MessageTask> studentPage = repo.findAll(pageable);
//        this.page++;
//        return studentPage.getContent().collect(Collectors.toSet());
        this.page++;
        return getFlightOnPage(this.page);
    }

    public Set<Flight> getFlightOnPage(int page) {
        this.page = page;
        Pageable pageable = new PageableImplementation(page, this.size);
        Page<Flight> studentPage = pagingRepository.findAll(pageable);
        return studentPage.getContent().collect(Collectors.toSet());
    }
}

