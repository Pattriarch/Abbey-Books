package spring.framework.labs.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.labs.domain.Category;
import spring.framework.labs.domain.dtos.CategoryDTO;

@Mapper
@Transactional
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categoryToCategoryDTO(Category category);

    Category categoryDTOToCategory(CategoryDTO categoryDTO);
}
