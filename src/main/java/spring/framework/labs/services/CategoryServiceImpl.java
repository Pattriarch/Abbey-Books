package spring.framework.labs.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.labs.domain.Category;
import spring.framework.labs.domain.dtos.CategoryDTO;
import spring.framework.labs.exceptions.ResourceNotFoundException;
import spring.framework.labs.mappers.CategoryMapper;
import spring.framework.labs.repositories.CategoryRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Set<CategoryDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public CategoryDTO findById(Long aLong) {
        return categoryRepository.findById(aLong)
                .map(categoryMapper::categoryToCategoryDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        return saveAndReturnDTO(categoryMapper.categoryDTOToCategory(categoryDTO));
    }

    private CategoryDTO saveAndReturnDTO(Category category) {
        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.categoryToCategoryDTO(savedCategory);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO, Long aLong) {
        return categoryRepository.findById(aLong).map(category -> {

            if (categoryDTO.getBooks() != null) {
                category.setBooks(categoryDTO.getBooks());
            }

            if (categoryDTO.getName() != null) {
                category.setName(categoryDTO.getName());
            }

            return categoryMapper.categoryToCategoryDTO(categoryRepository.save(category));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void delete(CategoryDTO categoryDTO) {
        categoryRepository.delete(categoryMapper.categoryDTOToCategory(categoryDTO));
    }

    @Override
    public void deleteById(Long aLong) {
        categoryRepository.deleteById(aLong);
    }

    @Override
    public CategoryDTO findByName(String name) {
        return categoryMapper.categoryToCategoryDTO(categoryRepository.findFirstByName(name));
    }

    @Override
    public List<CategoryDTO> findAllLimitedFive() {
        return findAll().stream().limit(5).toList();
    }
}
