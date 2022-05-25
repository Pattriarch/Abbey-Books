package spring.framework.labs.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import spring.framework.labs.domain.Cart;
import spring.framework.labs.domain.security.Authority;
import spring.framework.labs.domain.security.Role;
import spring.framework.labs.domain.security.User;
import spring.framework.labs.repositories.security.AuthorityRepository;
import spring.framework.labs.repositories.security.RoleRepository;
import spring.framework.labs.repositories.security.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultBreweryLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findAll().size() == 0) {
            loadSecurityData();
        }
    }

    private void loadSecurityData() {

        // author API auths
        Authority createAuthorAPI = authorityRepository.save(Authority.builder().permission("author.api.create").build());
        Authority readAuthorAPI = authorityRepository.save(Authority.builder().permission("author.api.read").build());
        Authority updateAuthorAPI = authorityRepository.save(Authority.builder().permission("author.api.update").build());
        Authority deleteAuthorAPI = authorityRepository.save(Authority.builder().permission("author.api.delete").build());

        // book API auths
        Authority createBookAPI = authorityRepository.save(Authority.builder().permission("book.api.create").build());
        Authority readBookAPI = authorityRepository.save(Authority.builder().permission("book.api.read").build());
        Authority updateBookAPI = authorityRepository.save(Authority.builder().permission("book.api.update").build());
        Authority deleteBookAPI = authorityRepository.save(Authority.builder().permission("book.api.delete").build());

        // category API auths
        Authority createCategoryAPI = authorityRepository.save(Authority.builder().permission("category.api.create").build());
        Authority readCategoryAPI = authorityRepository.save(Authority.builder().permission("category.api.read").build());
        Authority updateCategoryAPI = authorityRepository.save(Authority.builder().permission("category.api.update").build());
        Authority deleteCategoryAPI = authorityRepository.save(Authority.builder().permission("category.api.delete").build());

        // publisher API auths
        Authority createPublisherAPI = authorityRepository.save(Authority.builder().permission("publisher.api.create").build());
        Authority readPublisherAPI = authorityRepository.save(Authority.builder().permission("publisher.api.read").build());
        Authority updatePublisherAPI = authorityRepository.save(Authority.builder().permission("publisher.api.update").build());
        Authority deletePublisherAPI = authorityRepository.save(Authority.builder().permission("publisher.api.delete").build());

        // user API auths
        Authority createUserAPI = authorityRepository.save(Authority.builder().permission("user.api.create").build());
        Authority readUserAPI = authorityRepository.save(Authority.builder().permission("user.api.read").build());
        Authority updateUserAPI = authorityRepository.save(Authority.builder().permission("user.api.update").build());
        Authority deleteUserAPI = authorityRepository.save(Authority.builder().permission("user.api.delete").build());

        // cart auths
        Authority readCart = authorityRepository.save(Authority.builder().permission("cart.read").build());
        Authority updateCart = authorityRepository.save(Authority.builder().permission("cart.update").build());

        // book auths
        Authority createBook = authorityRepository.save(Authority.builder().permission("book.create").build());
        Authority readBook  = authorityRepository.save(Authority.builder().permission("book.read").build());
        Authority updateBook  = authorityRepository.save(Authority.builder().permission("book.update").build());
        Authority deleteBook  = authorityRepository.save(Authority.builder().permission("book.delete").build());

        log.debug("Cозданы authorities для roles");

        // roles
        Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
        Role moderatorRole = roleRepository.save(Role.builder().name("MODERATOR").build());
        Role userRole = roleRepository.save(Role.builder().name("USER").build());

        log.debug("Cозданы roles");

        // set authorities for role
        adminRole.setAuthorities(new HashSet<>(Set.of(
                createBook, readBook, updateBook, deleteBook,
                readCart, updateCart,
                createAuthorAPI, readAuthorAPI, updateAuthorAPI, deleteAuthorAPI,
                createBookAPI, readBookAPI, updateBookAPI, deleteBookAPI,
                createCategoryAPI, readCategoryAPI, updateCategoryAPI, deleteCategoryAPI,
                createPublisherAPI, readPublisherAPI, updatePublisherAPI, deletePublisherAPI,
                createUserAPI, readUserAPI, updateUserAPI, deleteUserAPI)));

        moderatorRole.setAuthorities(new HashSet<>(Set.of(
                readBook, updateBook,
                readCart, updateCart,
                readAuthorAPI,
                readBookAPI,
                readCategoryAPI,
                readPublisherAPI)));

        userRole.setAuthorities(new HashSet<>(Set.of(
                readBook,
                readCart, updateCart,
                readAuthorAPI,
                readBookAPI,
                readCategoryAPI,
                readPublisherAPI)));

        log.debug("Установлены authorities для roles");

        // save roles
        roleRepository.saveAll(Arrays.asList(adminRole, moderatorRole, userRole));

        log.debug("Сохранены роли");

        // create users
        userRepository.save(User.builder()
                .name("Комаров Михаил Романович")
                .username("admin")
                .balance(100000000L)
                .password(passwordEncoder.encode("Parol123987123"))
                .role(adminRole)
                .cart(Cart.builder().book_carts(new HashSet<>()).build())
                .build());

        userRepository.save(User.builder()
                .name("Ширяев Юрий Созонович")
                .username("moderator")
                .balance(20000L)
                .password(passwordEncoder.encode("Parol123987"))
                .role(moderatorRole)
                .cart(Cart.builder().book_carts(new HashSet<>()).build())
                .build());

        userRepository.save(User.builder()
                .name("Андреев Гордей Валерьевич")
                .username("user1")
                .balance(5000L)
                .password(passwordEncoder.encode("password"))
                .role(userRole)
                .cart(Cart.builder().book_carts(new HashSet<>()).build())
                .build());

        userRepository.save(User.builder()
                .name("Артемьев Овидий Святославович")
                .username("user2")
                .balance(5000L)
                .password(passwordEncoder.encode("password"))
                .role(userRole)
                .cart(Cart.builder().book_carts(new HashSet<>()).build())
                .build());

        userRepository.save(User.builder()
                .name("Никитин Арсений Юлианович")
                .username("user3")
                .balance(5000L)
                .password(passwordEncoder.encode("password"))
                .role(userRole)
                .cart(Cart.builder().book_carts(new HashSet<>()).build())
                .build());

        userRepository.save(User.builder()
                .name("Афанасьев Дмитрий Геннадьевич")
                .username("user4")
                .balance(5000L)
                .password(passwordEncoder.encode("password"))
                .role(userRole)
                .cart(Cart.builder().book_carts(new HashSet<>()).build())
                .build());

        userRepository.save(User.builder()
                .name("Беляков Иван Аркадьевич")
                .username("user5")
                .balance(5000L)
                .password(passwordEncoder.encode("password"))
                .role(userRole)
                .cart(Cart.builder().book_carts(new HashSet<>()).build())
                .build());

        log.debug("Загружены " + userRepository.count() + " пользователей");
    }

}