package repository.memory;

import domain.Entity;
import domain.validators.Validator;
import repository.Repository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID,E> {

    private Validator<E> validator;
    Map<ID,E> entities;

    /**
     * constructor with parameters
     * @param validator
     */
    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities = new HashMap<ID,E>();
    }

    /**
     * findOne
     * @param id -the id of the entity to be returned
     *           id must not be null
     * @return the entity with @param id
     */
    @Override
    public E findOne(ID id){
        if (id == null)
            throw new IllegalArgumentException("Id must be not null!");
        return entities.get(id);
    }

    /**
     * findAll
     * @return all entities
     */
    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    /**
     * save
     * @param entity
     *         entity must be not null
     * @return @param entity saved
     */
    @Override
    public E save(E entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entity must be not null!");
        validator.validate(entity);
        if(entities.get(entity.getId()) != null) {
            return entity;
        }
        else entities.put(entity.getId(),entity);
        return null;
    }

    /**
     * delete
     * @param id
     *      id must be not null
     * @return all entities without the entity with @param id
     */
    @Override
    public E delete(ID id) {
        if (id == null)
            throw new IllegalArgumentException("Id must be not null!");
        E entity = entities.get(id);
        entities.remove(id);
        return entity;
    }

    /**
     * update
     * @param entity
     *          entity must not be null
     * @return all entities with @param entity updated
     */
    @Override
    public E update(E entity) {

        if(entity == null)
            throw new IllegalArgumentException("Entity must be not null!");
        validator.validate(entity);

        entities.put(entity.getId(),entity);

        if(entities.get(entity.getId()) != null) {
            entities.put(entity.getId(),entity);
            return null;
        }
        return entity;
    }

}
