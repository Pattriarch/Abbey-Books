package spring.framework.labs.security.perms.userAPI;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('user.api.read')")
public @interface UserAPIReadPermission {
}