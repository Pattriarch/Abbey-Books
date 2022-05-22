package spring.framework.labs.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.labs.domain.Rating;
import spring.framework.labs.domain.dtos.RatingDTO;
import spring.framework.labs.exceptions.ResourceNotFoundException;
import spring.framework.labs.mappers.RatingMapper;
import spring.framework.labs.repositories.RatingRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RatingServiceImpl implements RatingService {

    private final RatingMapper ratingMapper;
    private final RatingRepository ratingRepository;

    @Override
    public Set<RatingDTO> findAll() {
        return ratingRepository.findAll()
                .stream()
                .map(ratingMapper::ratingRatingToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public RatingDTO findById(Long aLong) {
        return ratingRepository.findById(aLong)
                .map(ratingMapper::ratingRatingToDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public RatingDTO save(RatingDTO ratingDTO) {
        return saveAndReturnDTO(ratingMapper.ratingDTOToRating(ratingDTO));
    }

    private RatingDTO saveAndReturnDTO(Rating rating) {
        Rating savedRating = ratingRepository.save(rating);

        return ratingMapper.ratingRatingToDTO(savedRating);
    }

    @Override
    public RatingDTO update(RatingDTO ratingDTO, Long aLong) {
        return ratingRepository.findById(aLong).map(rating -> {

            if (ratingDTO.getBook() != null) {
                rating.setBook(rating.getBook());
            }

            if (ratingDTO.getRateTokens() != null) {
                rating.setRateTokens(rating.getRateTokens());
            }

            if (ratingDTO.getValue() != null) {
                rating.setValue(rating.getValue());
            }

            return ratingMapper.ratingRatingToDTO(ratingRepository.save(rating));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void delete(RatingDTO ratingDTO) {
        ratingRepository.delete(ratingMapper.ratingDTOToRating(ratingDTO));
    }

    @Override
    public void deleteById(Long aLong) {
        ratingRepository.deleteById(aLong);
    }
}
