package spring.framework.labs.domain.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import spring.framework.labs.domain.Book;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class PublisherDTO {
    private Long id;

//    @NotBlank
    private String name;

    private Set<Book> publisherBooks;
}
