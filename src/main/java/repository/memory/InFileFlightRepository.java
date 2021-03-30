package repository.memory;

import domain.Flight;
import domain.validators.Validator;
import repository.file.AbstractFileRepository;

import java.time.LocalDate;
import java.util.List;

public class InFileFlightRepository extends AbstractFileRepository<Long, Flight> {
    public InFileFlightRepository(String fileName, Validator<Flight> validator) {
        super(fileName, validator);
    }

    /**
     * extractEntity
     * @param attributes
     * @return an entity of ticket having a specified list of @param attributes
     */
    @Override
    public Flight extractEntity(List<String> attributes) throws IllegalArgumentException {
        Flight flight = new Flight(attributes.get(1), attributes.get(2), LocalDate.parse(attributes.get(3)), LocalDate.parse(attributes.get(4)), Integer.parseInt(attributes.get(5)));
        flight.setId(Long.parseLong(attributes.get(0)));
        return flight;
    }

    /**
     * createEntityAsString
     * @param entity
     * @return an entity of friendship as string
     */
    @Override
    protected String createEntityAsString(Flight entity) {
        return entity.getId() + ";" + entity.getFrom() + ";" + entity.getTo() + ";" +  entity.getDepartureLong() + ";" + entity.getLandingTime() + ";" + entity.getSeats();
    }
}
