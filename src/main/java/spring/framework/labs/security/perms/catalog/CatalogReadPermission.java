package spring.framework.labs.security.perms.catalog;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('catalog.read')")
public @interface CatalogReadPermission {
}
