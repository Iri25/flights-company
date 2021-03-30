import domain.Flight;
import domain.validators.FlightValidator;
import repository.memory.InFileFlightRepository;
import repository.paging.PagingRepository;
import service.FlightService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

//        PagingRepository<Long, Flight> flightPagingRepository;
//        FlightService flightService;

//        flightPagingRepository =  new InFileFlightRepository("data/flights.txt", new FlightValidator());
//        flightService = new FlightService(flightPagingRepository);
//        flightService.getAll().forEach(System.out::println);
//        flightService.setPageSize(5);
//
//        System.out.println("Elements on page 5");
//        flightService.getFlightOnPage(5).stream()
//                .forEach(System.out::println);
//        System.out.println("Elements on next page");
//        flightService.getNextFlight().stream()
//                .forEach(System.out::println);

        MainApp.main(args);
    }
}
