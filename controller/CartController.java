package SpringBootShop.project.controller;

import SpringBootShop.project.domain.Cart;
import SpringBootShop.project.dto.cart.CartForm;
import SpringBootShop.project.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * 장바구니 담기
     */
    @PostMapping("/api/cart/items")
    @ResponseBody
    public Cart addCart(Authentication authentication, @RequestBody CartForm form) {
        String userId = authentication.getName();

        return cartService.addCart(userId, form.getProductId(), form.getQuantity());
    }

    /**
     * 장바구니 전체 조회
     */
    @GetMapping("/api/cart")
    @ResponseBody
    public List<Cart> findAllCart(Authentication authentication) {
        String userId = authentication.getName();

        return cartService.findAllCart(userId);
    }

    /**
     * 장바구니 단건 삭제
     */
    @DeleteMapping("/api/cart/items/{productId}")
    @ResponseBody
    public void deleteCart(Authentication authentication, @PathVariable Long productId) {
        String userId = authentication.getName();

        cartService.deleteCart(userId, productId);
    }

    /**
     * 장바구니 전체 삭제
     */
    @DeleteMapping("/api/cart")
    @ResponseBody
    public void deleteAllCart(Authentication authentication) {
        String userId = authentication.getName();

        cartService.deleteAllCart(userId);
    }
}
