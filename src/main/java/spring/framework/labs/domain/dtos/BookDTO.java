package spring.framework.labs.domain.dtos;


import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import spring.framework.labs.domain.*;
import spring.framework.labs.domain.security.User;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class BookDTO {
    private Long id;

    @NotBlank
    private String article;

    private Rating rating;

    private String[] images;

    private Set<Author> authors;

    private Category category;

    private Set<Publisher> publishers;

    private Set<User> users = new HashSet<>();

    private Set<Cart> carts = new HashSet<>();

    @NotEmpty(message = "Поле с isbn не может быть пустым")
    private String isbn;

    @Max(2022)
    private Integer yearOfPublishing;

    @Min(1)
    private Integer numberOfPages;

    @NotEmpty(message = "Поле с форматом не может быть пустым")
    private String format;

    @Min(0)
    private Double weight;

    @Min(1)
    private Long price;

    @NotEmpty(message = "Поле с именем не может быть пустым")
    private String name;

    @NotEmpty(message = "Поле с описанием не может быть пустым")
    private String description;
}
