package spring.framework.labs.services.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.labs.domain.Book;
import spring.framework.labs.domain.Cart;
import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.domain.security.User;
import spring.framework.labs.exceptions.NotEnoughMoneyException;
import spring.framework.labs.exceptions.ResourceNotFoundException;
import spring.framework.labs.exceptions.UserAlreadyExistException;
import spring.framework.labs.mappers.BookMapper;
import spring.framework.labs.mappers.CartMapper;
import spring.framework.labs.mappers.UserMapper;
import spring.framework.labs.repositories.security.RoleRepository;
import spring.framework.labs.repositories.security.UserRepository;
import spring.framework.labs.services.BookService;
import spring.framework.labs.services.CartService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final BookMapper bookMapper;
    private final CartMapper cartMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookService bookService;
    private final CartService cartService;

    @Override
    public Set<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public UserDTO findById(Long aLong) {
        return userRepository.findById(Math.toIntExact(aLong))
                .map(userMapper::userToUserDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        if (findByUsername(userDTO.getUsername()) != null) {
            throw new UserAlreadyExistException("Пользователь с name = " + userDTO.getUsername() + " уже существует");
        }
        return saveAndReturnDTO(userMapper.userDTOToUser(userDTO));
    }

    private UserDTO saveAndReturnDTO(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRoles().isEmpty()) {
            user.setRoles(new HashSet<>());
            user.getRoles().add(roleRepository.findByName("USER").get());
        }

        if (user.getCart() == null) {
            user.setCart(Cart.builder().user(user).book_carts(new HashSet<>()).build());
        }

        User savedUser = userRepository.save(user);

        return userMapper.userToUserDTO(savedUser);
    }


    @Override
    public UserDTO update(UserDTO userDTO, Long aLong) {
        return userRepository.findById(Math.toIntExact(aLong)).map(user -> {

            if (userDTO.getName() != null) {
                user.setName(userDTO.getName());
            }

            if (userDTO.getUsername() != null) {
                user.setUsername(userDTO.getUsername());
            }

            if (userDTO.getBalance() != null) {
                user.setBalance(userDTO.getBalance());
            }

            if (userDTO.getRoles() != null) {
                user.setRoles(userDTO.getRoles());
            } else {
                user.setRoles(new HashSet<>());
                user.getRoles().add(roleRepository.findByName("USER").get());
            }

            if (userDTO.getBooks() != null) {
                user.setBooks(userDTO.getBooks());
            } else {
                user.setBooks(new HashSet<>());
            }

            if (userDTO.getCart() != null) {
                user.setCart(userDTO.getCart());
            }

            if (userDTO.getAccountNonExpired() != null) {
                user.setAccountNonExpired(userDTO.getAccountNonExpired());
            }

            if (userDTO.getAccountNonLocked() != null) {
                user.setAccountNonLocked(userDTO.getAccountNonLocked());
            }

            if (userDTO.getCredentialsNonExpired() != null) {
                user.setCredentialsNonExpired(userDTO.getCredentialsNonExpired());
            }

            if (userDTO.getEnabled() != null) {
                user.setEnabled(userDTO.getEnabled());
            }
            if (userDTO.getUseGoogle2fa() != null) {
                user.setUseGoogle2fa(userDTO.getUseGoogle2fa());
            }

            if (userDTO.getGoogle2faSecret() != null) {
                user.setGoogle2faSecret(userDTO.getGoogle2faSecret());
            }

            if (userDTO.getGoogle2faRequired() != null) {
                user.setGoogle2faRequired(userDTO.getGoogle2faRequired());
            }

            if (userDTO.getCreatedDate() != null) {
                user.setCreatedDate(userDTO.getCreatedDate());
            }

            if (userDTO.getLastModifiedDate() != null) {
                user.setLastModifiedDate(userDTO.getLastModifiedDate());
            }

            return userMapper.userToUserDTO(userRepository.save(user));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void delete(UserDTO userDTO) {
        userRepository.delete(userMapper.userDTOToUser(userDTO));
    }

    @Override
    public void deleteById(Long aLong) {
        userRepository.deleteById(Math.toIntExact(aLong));
    }

    @Override
    public UserDTO findByUsername(String name) {
        return userMapper.userToUserDTO(userRepository.findByUsername(name).orElse(null));
    }

    @Override
    public UserDTO payBooks(UserDTO userDTO) throws NotEnoughMoneyException {
        Long sum = userDTO
                .getCart()
                .getBook_carts()
                .stream()
                .map(Book::getPrice)
                .mapToLong(Long::longValue)
                .sum();

        if (userDTO.getBalance() >= sum) {
            userDTO.getCart()
                    .getBook_carts()
                    .forEach(book -> userDTO.getBooks().add(book));
            userDTO.getCart().setBook_carts(new HashSet<>());
            userDTO.setBalance(userDTO.getBalance() - sum);
            cartService.update(cartMapper.cartToCartDTO(userDTO.getCart()), userDTO.getCart().getId());
            return update(userDTO, Long.valueOf(userDTO.getId()));
        } else {
            throw new NotEnoughMoneyException("Не хватает " + (sum - userDTO.getBalance()) + " для оплаты");
        }
    }

    @Override
    public void addToCart(UserDTO userDTO, Long aLong) {
        userDTO
                .getCart()
                .getBook_carts()
                .add(bookMapper.bookDTOToBook(bookService.findById(aLong)));

        cartService.update(cartMapper.cartToCartDTO(userDTO.getCart()), userDTO.getCart().getId());
    }

    @Override
    public void changePassword(String currentPassword, String newPassword, Long userId) {

        User user = userRepository.findById(Math.toIntExact(userId)).get();

        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }
    }

    @Override
    public UserDTO deleteBookFromCart(UserDTO userDTO, Long bookId) {
        User user = userRepository.findById(userDTO.getId()).get();
        user.getCart().getBook_carts().removeIf(book -> book.getId().equals(bookId));
        return update(userMapper.userToUserDTO(user), Long.valueOf(user.getId()));
    }

    @Override
    public UserDTO deleteAllBooksFromCart(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).get();
        user.getCart().setBook_carts(new HashSet<>());
        return update(userMapper.userToUserDTO(user), Long.valueOf(user.getId()));
    }

    @Override
    public UserDTO addBalance(Long userId, Long value) {
        User user = userRepository.findById(Math.toIntExact(userId)).get();
        user.setBalance(user.getBalance() + value);
        return update(userMapper.userToUserDTO(user), Long.valueOf(user.getId()));
    }

}
