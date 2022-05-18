package spring.framework.labs.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import spring.framework.labs.domain.Rating;
import spring.framework.labs.domain.dtos.RatingDTO;

@Mapper
public interface RatingMapper {
    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    RatingDTO ratingRatingToDTO(Rating rating);

    Rating ratingDTOToRating(RatingDTO ratingDTO);
}
