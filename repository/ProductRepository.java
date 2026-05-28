package SpringBootShop.project.repository;

import SpringBootShop.project.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 이름으로 상품 찾기
    Optional<Product> findByProductName(String productName);

}
