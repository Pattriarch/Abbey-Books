package spring.framework.labs.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.labs.domain.Cart;
import spring.framework.labs.domain.dtos.CartDTO;

@Mapper
@Transactional
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartDTO cartToCartDTO(Cart cart);

    Cart cartDTOToCart(CartDTO cart);
}
