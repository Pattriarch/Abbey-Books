package spring.framework.labs.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import spring.framework.labs.domain.Publisher;
import spring.framework.labs.domain.dtos.PublisherDTO;

@Mapper
public interface PublisherMapper {
    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    PublisherDTO publisherToPublisherDTO(Publisher publisher);

    Publisher publisherDTOToPublisher(PublisherDTO publisherDTO);
}
