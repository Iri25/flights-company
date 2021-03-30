package domain.validators;

import domain.Client;
import domain.Flight;

public class FlightValidator implements Validator<Flight> {
    @Override
    public void validate(Flight entity) throws ValidationException {
        StringBuilder errors = new StringBuilder();

        if (entity.getId() <= 0)
            errors.append("Flight ID must be positive!\n");

        if (entity.getFrom().isEmpty())
            errors.append("Flight from must not be empty!\n");

        if (entity.getSeats() <= 0)
            errors.append("Flight seats must be positive!\n");

        if (errors.length() > 0)
            throw new ValidationException(errors.toString());
    }
}
