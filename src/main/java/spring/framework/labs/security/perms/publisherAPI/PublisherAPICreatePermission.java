package spring.framework.labs.security.perms.publisherAPI;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('publisher.api.create')")
public @interface PublisherAPICreatePermission {
}