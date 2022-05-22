package spring.framework.labs.domain.dtos;

import lombok.Data;
import spring.framework.labs.domain.Book;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class CategoryDTO {
    private Long id;

    @NotEmpty(message = "Поле с названием не может быть пустым")
    private String name;

    private Set<Book> books;
}
