package spring.framework.labs.services;

import spring.framework.labs.domain.dtos.CategoryDTO;

import java.util.List;

public interface CategoryService extends CrudService<CategoryDTO, Long> {
    CategoryDTO findByName(String name);

    List<CategoryDTO> findAllLimitedFive();
}
