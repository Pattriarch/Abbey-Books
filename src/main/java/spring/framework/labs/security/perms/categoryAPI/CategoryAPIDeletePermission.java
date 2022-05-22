package spring.framework.labs.security.perms.categoryAPI;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('category.api.delete')")
public @interface CategoryAPIDeletePermission {
}