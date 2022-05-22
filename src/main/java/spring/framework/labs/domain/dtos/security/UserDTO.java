package spring.framework.labs.domain.dtos.security;

import lombok.Data;
import spring.framework.labs.domain.Book;
import spring.framework.labs.domain.Cart;
import spring.framework.labs.domain.security.Role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {
    private Integer id;

    @NotEmpty(message = "Поле с именем не может быть пустым")
    @Size(min = 8,message = "ФИО должно содержать не менее 8 символов")
    private String name;

    @NotEmpty(message = "Поле с именем пользователя не может быть пустым")
    @Size(min = 6,message = "Имя пользователя должно содержать не менее 6 символов")
    private String username;

    @NotEmpty(message = "Поле с паролем не может быть пустым")
    @Size(min = 6,message = "Пароль должен содержать не менее 6 символов")
    private String password;

    private Set<Role> roles = new HashSet<>();

    private Set<Book> books = new HashSet<>();

    private Cart cart;

    private Long balance = 0L;

    private Boolean accountNonExpired = true;

    private Boolean accountNonLocked = true;

    private Boolean credentialsNonExpired = true;

    private Boolean enabled = true;

    private Boolean useGoogle2fa = false;

    private String google2faSecret;

    private Boolean google2faRequired = true;

    private Timestamp createdDate;

    private Timestamp lastModifiedDate;
}
