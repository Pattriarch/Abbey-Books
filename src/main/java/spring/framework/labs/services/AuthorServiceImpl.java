package spring.framework.labs.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.labs.domain.Author;
import spring.framework.labs.domain.dtos.AuthorDTO;
import spring.framework.labs.exceptions.ResourceNotFoundException;
import spring.framework.labs.mappers.AuthorMapper;
import spring.framework.labs.repositories.AuthorRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;

    @Override
    public Set<AuthorDTO> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::authorToAuthorDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public AuthorDTO findById(Long aLong) {
        return authorRepository.findById(aLong)
                .map(authorMapper::authorToAuthorDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public AuthorDTO save(AuthorDTO authorDTO) {
        return saveAndReturnDTO(authorMapper.authorDTOToAuthor(authorDTO));
    }

    private AuthorDTO saveAndReturnDTO(Author author) {
        Author savedAuthor = authorRepository.save(author);

        return authorMapper.authorToAuthorDTO(savedAuthor);
    }

    @Override
    public AuthorDTO update(AuthorDTO authorDTO, Long aLong) {
        return authorRepository.findById(aLong).map(author -> {

            if (authorDTO.getAuthorBooks() != null) {
                author.setAuthorBooks(authorDTO.getAuthorBooks());
            }

            if (authorDTO.getDescription() != null) {
                author.setDescription(authorDTO.getDescription());
            }

            if (authorDTO.getImage() != null) {
                author.setImage(authorDTO.getImage());
            }

            return authorMapper.authorToAuthorDTO(authorRepository.save(author));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void delete(AuthorDTO authorDTO) {
        authorRepository.delete(authorMapper.authorDTOToAuthor(authorDTO));
    }

    @Override
    public void deleteById(Long aLong) {
        authorRepository.deleteById(aLong);
    }

    @Override
    public AuthorDTO findByName(String name) {
        return authorMapper.authorToAuthorDTO(authorRepository.findFirstByName(name));
    }
}
