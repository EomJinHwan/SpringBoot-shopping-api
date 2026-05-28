package SpringBootShop.project.service;

import SpringBootShop.project.domain.Cart;
import SpringBootShop.project.domain.Product;
import SpringBootShop.project.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartService(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    // 상품 등록
    // userId를 파라미터로 받아 사용하여 User의존성 필요없음
    public Cart addCart(String userId, Long productId, Long quantity) {
        // findById에서 예외처리를 하고 Product로 반환하여 .orElseThrow 사용 안됌
        Product product = productService.findById(productId);

        // 이미 담긴 상품이면 수량 합산
        List<Cart> userCarts = cartRepository.findByUserId(userId);
        for (Cart cart : userCarts) {
            if (cart.getProductId().equals(productId)) {
                cart.setQuantity(cart.getQuantity() + quantity);
                return cartRepository.save(cart);
            }
        }

        Cart cart = new Cart(userId, productId, product.getProductName(), product.getPrice(), quantity);
        return cartRepository.save(cart);
    }

    // 장바구니 전체 보기
    public List<Cart> findAllCart(String userId) {
        return cartRepository.findByUserId(userId);
    }

    // 장바구니에서 상품 삭제 - deleteByUserIdAndProductId로 벽녕
    public void deleteCart(String userId, Long productId) {
        cartRepository.deleteByUserIdAndProductId(userId, productId);
    }

    // 장바구니 전체 삭제 - 구매 시 - deleteByUserId로 변경
    public void deleteAllCart(String userId) {
        cartRepository.deleteByUserId(userId);
    }
}
