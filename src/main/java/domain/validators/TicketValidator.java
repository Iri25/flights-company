package domain.validators;

import domain.Ticket;

public class TicketValidator implements Validator<Ticket> {

    @Override
    public void validate(Ticket entity) throws ValidationException {
        StringBuilder errors = new StringBuilder();

        if (entity.getId() <= 0)
            errors.append("Ticket ID must be positive!\n");

        if (entity.getUsername().isEmpty())
            errors.append("Username must not be empty!!\n");

        if (entity.getFlightId() <= 0)
            errors.append("Flight ID must be positive!\n");

        if (errors.length() > 0)
            throw new ValidationException(errors.toString());
    }
}