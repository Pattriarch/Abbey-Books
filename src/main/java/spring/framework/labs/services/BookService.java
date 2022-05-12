package spring.framework.labs.services;

import spring.framework.labs.domain.dtos.BookDTO;

public interface BookService extends CrudService<BookDTO, Long> {
    BookDTO findByName(String name);
}
