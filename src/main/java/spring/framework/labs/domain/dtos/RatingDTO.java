package spring.framework.labs.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import spring.framework.labs.domain.Book;
import spring.framework.labs.domain.RateToken;
import spring.framework.labs.domain.security.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
public class RatingDTO {
    private Long id;

    private Set<RateToken> rateTokens = new HashSet<>();

    private Book book;

    private Double value;
}
