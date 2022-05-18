package spring.framework.labs.services;

import spring.framework.labs.domain.dtos.BookDTO;
import spring.framework.labs.domain.dtos.CategoryDTO;
import spring.framework.labs.domain.dtos.RateTokenDTO;

public interface RateTokenService extends CrudService<RateTokenDTO, Long> {
     RateTokenDTO save(RateTokenDTO rateTokenDTO, Long rate, Integer userId, BookDTO book);
}
