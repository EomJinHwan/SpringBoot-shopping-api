package SpringBootShop.project.repository;

import SpringBootShop.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // id(유저 아이디)로 조회 - JPA가 자동으로 쿼리 생성
    Optional<User> findByUserId(String id);
}
