package SpringBootShop.project.controller;

import SpringBootShop.project.domain.Product;
import SpringBootShop.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {
    // 의존성 받기
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 상품 등록
     */
    @PostMapping("/products")
    @ResponseBody
    public Product create(@RequestBody ProductForm form) {
        Product product = new Product(form.getProductName(), form.getPrice(), form.getStock());
        return productService.addProduct(product);
    }

    /**
     * 전체 사품 조회
     */
    @GetMapping("/products")
    @ResponseBody
    public List<Product> findAll() {
        return productService.findAll();
    }

    /**
     * id로 상품 조회
     */
    @GetMapping("/products/{id}")
    @ResponseBody
    public Product findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    /**
     * 이름으로 상품 조회
     */
    @GetMapping("/products/search")
    @ResponseBody
    public Product findByName(@RequestParam String name) {
        return productService.findByName(name);
    }

    /**
     * 상품 삭제
     */
    @DeleteMapping("/products/{id}")
    @ResponseBody
    public String deleteById(@PathVariable Long id) {
        productService.deleteById(id);
        return "삭제 완료";
    }

    /**
     * 상품 정보 수정
     */
    @PutMapping("/products/{id}")
    @ResponseBody
    public String update(@PathVariable Long id, @RequestBody ProductForm form) {
        productService.update(id, form);
        return "상품 수정 완료";
    }
}
