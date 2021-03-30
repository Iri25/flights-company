package repository.paging;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Pager<E>  {

    private Pageable pageable;
    private Iterable<E> elements;

    public Pager(Pageable pageable, Iterable<E> elements) {
        this.pageable = pageable;
        this.elements = elements;
    }

    public Page<E> paged() {
        Stream<E> result = StreamSupport.stream(elements.spliterator(), false)
                .skip(pageable.getPageNumber()  * pageable.getPageSize())
                .limit(pageable.getPageSize());
        return new PageImplementation<>(pageable, result);
    }
}
