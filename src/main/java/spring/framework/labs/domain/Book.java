package spring.framework.labs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import spring.framework.labs.domain.security.User;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String article;

    @OneToOne
    private Rating rating;

    @Type(type = "string-array")
    @Column(columnDefinition = "text[]")
    private String[] images;

    @ManyToMany(mappedBy = "authorBooks")
    @ToString.Exclude
    @JsonIgnore
    private Set<Author> authors = new HashSet<>();

    @ManyToOne
    private Category category;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(name = "book_publisher",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "publisher_id")})
    @ToString.Exclude
    @JsonIgnore
    private Set<Publisher> publishers = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

    @ManyToMany(mappedBy = "book_carts")
    @JsonIgnore
    @ToString.Exclude
    private Set<Cart> carts = new HashSet<>();

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
