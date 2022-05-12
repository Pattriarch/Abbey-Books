package spring.framework.labs.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.labs.domain.Author;
import spring.framework.labs.domain.dtos.AuthorDTO;

@Mapper
@Transactional
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorDTO authorToAuthorDTO(Author author);

    Author authorDTOToAuthor(AuthorDTO author);
}
