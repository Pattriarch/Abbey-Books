package spring.framework.labs.domain.dtos;

import lombok.Data;
import spring.framework.labs.domain.Book;
import spring.framework.labs.domain.RateToken;

import java.util.HashSet;
import java.util.Set;

@Data
public class RatingDTO {
    private Long id;

    private Set<RateToken> rateTokens = new HashSet<>();

    private Book book;

    private Double value;
}
