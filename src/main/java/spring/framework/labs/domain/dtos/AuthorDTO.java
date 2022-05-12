package spring.framework.labs.domain.dtos;

import lombok.*;
import spring.framework.labs.domain.Book;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class AuthorDTO {
    private Long id;

    private Set<Book> authorBooks;

//    @NotBlank
    private String name;

//    @NotBlank
    private String description;

//    @NotBlank
    private String image;
}
