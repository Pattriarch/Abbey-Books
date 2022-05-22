package spring.framework.labs.security.perms.authorAPI;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('author.api.update')")
public @interface AuthorAPIUpdatePermission {
}