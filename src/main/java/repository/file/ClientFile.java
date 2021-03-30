package repository.file;

import domain.Client;
import domain.validators.Validator;

import java.util.List;

public class ClientFile extends AbstractFileRepository<String, Client> {

    /**
     * constructor with paramaters @param fileName, @param validator
     * @param fileName
     * @param validator
     */
    public ClientFile(String fileName, Validator<Client> validator) {
        super(fileName, validator);
        loadData();
    }

    /**
     * extractEntity
     * @param attributes
     * @return an entity of client having a specified list of @param attributes
     */
    @Override
    public Client extractEntity(List<String> attributes) throws IllegalArgumentException {
        Client client = new Client(attributes.get(1));
        client.setId(attributes.get(0));
        return client;
    }

    /**
     * createEntityAsString
     * @param entity
     * @return an entity of friendship as string
     */
    @Override
    protected String createEntityAsString(Client entity) {
        return entity.getId() + ";" + entity.getName();
    }
}
