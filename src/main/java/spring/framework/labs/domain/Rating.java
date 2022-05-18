package spring.framework.labs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;
import spring.framework.labs.domain.security.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "rating")
    @ToString.Exclude
    @JsonIgnore
    private Set<RateToken> rateTokens = new HashSet<>();

    @OneToOne(
            mappedBy = "rating",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    @ToString.Exclude
    private Book book;

    private Double value;
}