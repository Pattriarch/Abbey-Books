package spring.framework.labs.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.domain.security.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);
}
