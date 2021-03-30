package repository.file;

import domain.Flight;
import domain.Ticket;
import domain.validators.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TicketFile extends AbstractFileRepository<Long, Ticket> {

    /**
     * constructor with paramaters @param fileName, @param validator
     * @param fileName
     * @param validator
     */
    public TicketFile(String fileName, Validator<Ticket> validator) {
        super(fileName, validator);
        loadData();
    }

    /**
     * extractEntity
     * @param attributes
     * @return an entity of hotel having a specified list of @param attributes
     */
    @Override
    public Ticket extractEntity(List<String> attributes) throws IllegalArgumentException {
        Ticket ticket = new Ticket(attributes.get(1), Long.parseLong(attributes.get(2)), LocalDateTime.parse(attributes.get(3)));
        ticket.setId(Long.parseLong(attributes.get(0)));
        return ticket;
    }

    /**
     * createEntityAsString
     * @param entity
     * @return an entity of friendship as string
     */
    @Override
    protected String createEntityAsString(Ticket entity) {
        return entity.getId() + ";" + entity.getUsername() + ";" + entity.getFlightId() + ";" + entity.getPurchaseTime();
    }
}
