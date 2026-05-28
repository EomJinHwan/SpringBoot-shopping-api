package SpringBootShop.project.controller;

import SpringBootShop.project.domain.Cart;
import SpringBootShop.project.service.CartService;
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
    @PostMapping("/cart/{userId}")
    @ResponseBody
    public Cart addCart(@PathVariable String userId, @RequestBody CartForm form) {
        return cartService.addCart(userId, form.getProductId(), form.getQuantity());
    }

    /**
     * 장바구니 전체 조회
     */
    @GetMapping("/cart/{userId}")
    @ResponseBody
    public List<Cart> findAllCart(@PathVariable String userId) {
        return cartService.findAllCart(userId);
    }

    /**
     * 장바구니 단건 삭제
     */
    @DeleteMapping("/cart/{userId}/{productId}")
    @ResponseBody
    public void deleteCart(@PathVariable String userId, @PathVariable Long productId) {
        cartService.deleteCart(userId, productId);
    }

    /**
     * 장바구니 전체 삭제
     */
    @DeleteMapping("/cart/{userId}")
    @ResponseBody
    public void deleteAllCart(@PathVariable String userId) {
        cartService.deleteAllCart(userId);
    }
}
