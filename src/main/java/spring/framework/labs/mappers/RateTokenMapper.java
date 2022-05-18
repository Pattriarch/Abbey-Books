package spring.framework.labs.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import spring.framework.labs.domain.RateToken;
import spring.framework.labs.domain.Rating;
import spring.framework.labs.domain.dtos.RateTokenDTO;
import spring.framework.labs.domain.dtos.RatingDTO;

@Mapper
public interface RateTokenMapper {
    RateTokenMapper INSTANCE = Mappers.getMapper(RateTokenMapper.class);

    RateTokenDTO rateTokenToRateTokenDTO(RateToken rateToken);

    RateToken rateTokenDTOToRateToken(RateTokenDTO rateTokenDTO);
}
