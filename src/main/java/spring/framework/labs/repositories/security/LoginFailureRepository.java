package spring.framework.labs.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.framework.labs.domain.security.LoginFailure;
import spring.framework.labs.domain.security.User;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface LoginFailureRepository extends JpaRepository<LoginFailure, Integer> {

    List<LoginFailure> findAllByUserAndCreatedDateIsAfter(User user, Timestamp timestamp);
}