package spring.framework.labs.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.labs.domain.Cart;
import spring.framework.labs.domain.Category;
import spring.framework.labs.domain.dtos.CartDTO;
import spring.framework.labs.domain.dtos.CategoryDTO;
import spring.framework.labs.exceptions.ResourceNotFoundException;
import spring.framework.labs.mappers.CartMapper;
import spring.framework.labs.repositories.CartRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;
    private final CartRepository cartRepository;

    public CartServiceImpl(CartMapper cartMapper, CartRepository cartRepository) {
        this.cartMapper = cartMapper;
        this.cartRepository = cartRepository;
    }

    @Override
    public Set<CartDTO> findAll() {
        return cartRepository.findAll()
                .stream()
                .map(cartMapper::cartToCartDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public CartDTO findById(Long aLong) {
        return cartRepository.findById(aLong)
                .map(cartMapper::cartToCartDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CartDTO save(CartDTO cartDTO) {
        return saveAndReturnDTO(cartMapper.cartDTOToCart(cartDTO));
    }

    private CartDTO saveAndReturnDTO(Cart cart) {
        Cart savedCart = cartRepository.save(cart);

        return cartMapper.cartToCartDTO(savedCart);
    }

    @Override
    public CartDTO update(CartDTO cartDTO, Long aLong) {
        return cartRepository.findById(aLong).map(cart -> {

            if (cartDTO.getBook_carts() != null) {
                cart.setBook_carts(cartDTO.getBook_carts());
            }

            if (cartDTO.getUser() != null) {
                cart.setUser(cartDTO.getUser());
            }

            return cartMapper.cartToCartDTO(cartRepository.save(cart));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void delete(CartDTO cartDTO) {
        cartRepository.delete(cartMapper.cartDTOToCart(cartDTO));
    }

    @Override
    public void deleteById(Long aLong) {
        cartRepository.deleteById(aLong);
    }

}
