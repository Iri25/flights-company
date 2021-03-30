package domain.validators;

import domain.Client;

public class ClientValidator implements Validator<Client> {

    @Override
    public void validate(Client entity) throws ValidationException {
        StringBuilder errors = new StringBuilder();

        if (entity.getId().isEmpty())
            errors.append("Client ID mmust not be empty!\n");

        if (entity.getName().isEmpty())
            errors.append("Client name must not be empty!\n");

        if (errors.length() > 0)
            throw new ValidationException(errors.toString());
    }
}
