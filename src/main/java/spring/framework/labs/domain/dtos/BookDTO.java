package spring.framework.labs.domain.dtos;


import lombok.Data;
import spring.framework.labs.domain.*;
import spring.framework.labs.domain.security.User;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Data
public class BookDTO {
    private Long id;

    @NotBlank(message = "Поле с артиклем не может быть пустым")
    private String article;

    private Rating rating;

    private String[] images;

    private Set<Author> authors;

    private Category category;

    private Set<Publisher> publishers;

    private Set<User> bookUsers = new HashSet<>();

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
