package spring.framework.labs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        )
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String article;

    private Double rating;

    @Type(type = "string-array")
    @Column(columnDefinition = "text[]")
    private String[] images;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(name = "book_author",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")})
    @ToString.Exclude
    private Set<Author> authors = new HashSet<>();

    @Column
    private String category;

    @ManyToMany(mappedBy = "publisherBooks")
    @ToString.Exclude
    private Set<Publisher> publishers = new HashSet<>();

    @Column
    private String isbn;

    @Column
    private Integer yearOfPublishing;

    @Column
    private Integer numberOfPages;

    @Column
    private String format;

    @Column
    private Double weight;

    @Column
    private Long price;

    @Column
    private String name;

    @Column(columnDefinition="TEXT")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return id != null && Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
