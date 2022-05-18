package spring.framework.labs.services;

import org.springframework.data.domain.Page;
import spring.framework.labs.domain.dtos.PublisherDTO;

public interface PublisherService extends CrudService<PublisherDTO, Long> {
    PublisherDTO findByName(String name);

    Page<PublisherDTO> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
