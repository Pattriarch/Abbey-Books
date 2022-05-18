package spring.framework.labs.domain.dtos;

import lombok.Data;
import spring.framework.labs.domain.Rating;
import spring.framework.labs.domain.security.User;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;

@Data
public class RateTokenDTO {
    private Long id;

    private User user;

    private Integer rate;

    private Rating rating;
}
