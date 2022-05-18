package spring.framework.labs.domain.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import spring.framework.labs.domain.Book;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class PublisherDTO {
    private Long id;

    @NotEmpty(message = "Поле с названием не может быть пустым")
    private String name;

    private Set<Book> publisherBooks;
}
