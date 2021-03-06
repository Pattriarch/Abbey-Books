package spring.framework.labs.domain.dtos;

import lombok.*;
import spring.framework.labs.domain.Book;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class AuthorDTO {
    private Long id;

    private Set<Book> authorBooks;

    @NotEmpty(message = "Поле с именем не может быть пустым")
    private String name;

    private String description;

    private String image;
}
