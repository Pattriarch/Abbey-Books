package spring.framework.labs.services;

import spring.framework.labs.domain.dtos.PublisherDTO;

public interface PublisherService extends CrudService<PublisherDTO, Long> {
    PublisherDTO findByName(String name);
}
