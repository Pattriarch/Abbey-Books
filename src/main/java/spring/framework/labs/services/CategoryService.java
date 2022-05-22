package spring.framework.labs.services;

import spring.framework.labs.domain.dtos.CategoryDTO;

public interface CategoryService extends CrudService<CategoryDTO, Long> {
    CategoryDTO findByName(String name);
}
