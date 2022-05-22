package spring.framework.labs.services;

import spring.framework.labs.domain.dtos.AuthorDTO;

public interface AuthorService extends CrudService<AuthorDTO, Long> {
    AuthorDTO findByName(String name);
}
