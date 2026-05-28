package SpringBootShop.project.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;    // PK

    // CartRepository가 Map으로 userId 관리 →  Cart가 직접 보유
    private String userId;

    // Product 객체 통째로 → 필요한 값만 꺼내서 보관
    private Long productId;
    private String productName;
    private Long price;

    // 동일
    private Long quantity;

    // JPA 기본 생성자
    protected Cart() {
    }

    public Cart(String userId, Long productId, String productName, Long price, Long quantity) {
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Long getPrice() {
        return price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}

