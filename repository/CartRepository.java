package SpringBootShop.project.repository;

import SpringBootShop.project.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // 해당 유저의 장바구니 전체 조회
    List<Cart> findByUserId(String userId);

    // 해당 유저의 특정 상품 삭제
    void deleteByUserIdAndProductId(String userId, Long productId);

    // 해당 유저 장바구니 전체 삭제
    void deleteByUserId(String userId);
}
