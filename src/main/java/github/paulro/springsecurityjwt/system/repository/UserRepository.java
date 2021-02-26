package github.paulro.springsecurityjwt.system.repository;

import github.paulro.springsecurityjwt.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void deleteByUserName(String userName);

    boolean existsByUserName(String userName);
}
