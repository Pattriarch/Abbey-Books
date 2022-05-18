package spring.framework.labs.domain;

import lombok.*;
import spring.framework.labs.domain.security.User;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rateTokens")
public class RateToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private Integer rate;

    @ManyToOne
    private Rating rating;
}
