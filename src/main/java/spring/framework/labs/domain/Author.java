package spring.framework.labs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToMany(mappedBy = "authors")
    @ToString.Exclude
    @JsonIgnore
    private Set<Book> authorBooks = new HashSet<>();

    public Author(Long id, Set<Book> authorBooks , String name, String description, String image) {
        this.id = id;
        this.authorBooks = authorBooks;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    @Column
    private String name;

    @Column(columnDefinition="TEXT")
    private String description;

    @Column
    private String image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Author author = (Author) o;
        return id != null && Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
