package repository.paging;

import domain.Entity;
import domain.validators.Validator;
import repository.Repository;

public interface PagingRepository<ID , E extends Entity<ID>> extends Repository<ID, E> {

    Page<E> findAll (Pageable pageable);
}