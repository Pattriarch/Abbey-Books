package spring.framework.labs.domain.dtos;


import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import spring.framework.labs.domain.Author;
import spring.framework.labs.domain.Publisher;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class BookDTO {
    private Long id;

//    @NotBlank
    private String article;

//    @Min(1)
//    @Max(5)
    private Double rating;

    private String[] images;

    private Set<Author> authors;

//    @NotBlank
    private String category;

    private Set<Publisher> publishers;

//    @NotBlank
    private String isbn;

//    @Max(2022)
    private Integer yearOfPublishing;

//    @Min(1)
    private Integer numberOfPages;

//    @NotNull
    private String format;

//    @Min(0)
    private Double weight;

//    @Min(1)
    private Long price;

//    @NotBlank
    private String name;

//    @NotBlank
    private String description;
}
