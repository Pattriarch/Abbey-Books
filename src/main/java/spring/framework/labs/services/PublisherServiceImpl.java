package spring.framework.labs.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.framework.labs.domain.Publisher;
import spring.framework.labs.domain.dtos.PublisherDTO;
import spring.framework.labs.exceptions.ResourceNotFoundException;
import spring.framework.labs.mappers.PublisherMapper;
import spring.framework.labs.repositories.PublisherRepository;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PublisherServiceImpl implements PublisherService {

    private final PublisherMapper publisherMapper;
    private final PublisherRepository publisherRepository;

    @Override
    public Set<PublisherDTO> findAll() {
        return publisherRepository.findAll()
                .stream()
                .map(publisherMapper::publisherToPublisherDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public PublisherDTO findById(Long aLong) {
        return publisherRepository.findById(aLong)
                .map(publisherMapper::publisherToPublisherDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public PublisherDTO save(PublisherDTO publisherDTO) {
        return saveAndReturnDTO(publisherMapper.publisherDTOToPublisher(publisherDTO));
    }

    private PublisherDTO saveAndReturnDTO(Publisher publisher) {
        Publisher savedPublisher = publisherRepository.save(publisher);

        return publisherMapper.publisherToPublisherDTO(savedPublisher);
    }

    @Override
    public PublisherDTO update(PublisherDTO publisherDTO, Long aLong) {
        return publisherRepository.findById(aLong).map(publisher -> {

            if (publisherDTO.getPublisherBooks() != null) {
                publisher.setPublisherBooks(publisher.getPublisherBooks());
            }

            return publisherMapper.publisherToPublisherDTO(publisherRepository.save(publisher));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void delete(PublisherDTO publisherDTO) {
        publisherRepository.delete(publisherMapper.publisherDTOToPublisher(publisherDTO));
    }

    @Override
    public void deleteById(Long aLong) {
        publisherRepository.deleteById(aLong);
    }

    @Override
    public PublisherDTO findByName(String name) {
        return publisherMapper.publisherToPublisherDTO(publisherRepository.findFirstByName(name));
    }
}
