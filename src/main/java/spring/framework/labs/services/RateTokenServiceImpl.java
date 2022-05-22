package spring.framework.labs.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.labs.domain.RateToken;
import spring.framework.labs.domain.dtos.BookDTO;
import spring.framework.labs.domain.dtos.RateTokenDTO;
import spring.framework.labs.exceptions.ResourceNotFoundException;
import spring.framework.labs.mappers.RateTokenMapper;
import spring.framework.labs.repositories.RateTokenRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RateTokenServiceImpl implements RateTokenService {

    private final RateTokenMapper rateTokenMapper;
    private final RateTokenRepository rateTokenRepository;

    @Override
    public Set<RateTokenDTO> findAll() {
        return rateTokenRepository.findAll()
                .stream()
                .map(rateTokenMapper::rateTokenToRateTokenDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public RateTokenDTO findById(Long aLong) {
        return rateTokenRepository.findById(aLong)
                .map(rateTokenMapper::rateTokenToRateTokenDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public RateTokenDTO save(RateTokenDTO rateTokenDTO) {
        return saveAndReturnDTO(rateTokenMapper.rateTokenDTOToRateToken(rateTokenDTO));
    }

    private RateTokenDTO saveAndReturnDTO(RateToken rateToken) {
        RateToken savedRateToken = rateTokenRepository.save(rateToken);

        return rateTokenMapper.rateTokenToRateTokenDTO(savedRateToken);
    }

    @Override
    public RateTokenDTO update(RateTokenDTO rateTokenDTO, Long aLong) {
        return rateTokenRepository.findById(aLong).map(rateToken -> {

            if (rateTokenDTO.getRate() != null) {
                rateToken.setRate(rateTokenDTO.getRate());
            }

            if (rateTokenDTO.getRating() != null) {
                rateToken.setRating(rateTokenDTO.getRating());
            }

            if (rateTokenDTO.getUser() != null) {
                rateToken.setUser(rateTokenDTO.getUser());
            }

            return rateTokenMapper.rateTokenToRateTokenDTO(rateTokenRepository.save(rateToken));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void delete(RateTokenDTO rateTokenDTO) {
        rateTokenRepository.delete(rateTokenMapper.rateTokenDTOToRateToken(rateTokenDTO));
    }

    @Override
    public void deleteById(Long aLong) {
        rateTokenRepository.deleteById(aLong);
    }

    @Override
    public RateTokenDTO save(RateTokenDTO rateTokenDTO, Long rate, Integer userId, BookDTO book) {

        Optional<RateTokenDTO> optionalRateToken = findTokenByUserAndBook(userId, book);

        if (optionalRateToken.isPresent()) {
            optionalRateToken.get().setRate(Math.toIntExact(rate));
            rateTokenDTO = update(optionalRateToken.get(), optionalRateToken.get().getId());
        } else {
            rateTokenDTO = save(rateTokenDTO);
        }

        book.getRating().setValue((double) book.getRating().getRateTokens().stream().mapToInt(RateToken::getRate).sum() / (double) book.getRating().getRateTokens().size());


        return rateTokenDTO;
    }

    Optional<RateTokenDTO> findTokenByUserAndBook(Integer userId, BookDTO book) {
        return findAll().stream()
                .filter(rateTokenDTO1 -> Objects.equals(rateTokenDTO1.getUser().getId(), userId))
                .filter(rateTokenDTO1 -> Objects.equals(rateTokenDTO1.getRating().getId(), book.getId()))
                .findFirst();
    }
}
