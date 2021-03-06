package spring.framework.labs.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spring.framework.labs.domain.security.User;
import spring.framework.labs.repositories.security.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserUnlockService {

    private final UserRepository userRepository;

    @Scheduled(fixedRate = 1500000)
    public void unlockAccounts(){
        log.debug("Происходит разблокировка аккаунтов");

        List<User> lockedUsers = userRepository
                .findAllByAccountNonLockedAndLastModifiedDateIsBefore(false,
                        Timestamp.valueOf(LocalDateTime.now().minusSeconds(30)));

        if(lockedUsers.size() > 0){
            log.debug("Заблокированные аккаунты найдены, происходит разблокировка");
            lockedUsers.forEach(user -> user.setAccountNonLocked(true));

            userRepository.saveAll(lockedUsers);
        }
    }

}