package SpringBootShop.project.service;

import SpringBootShop.project.controller.ProductForm;
import SpringBootShop.project.domain.Product;
import SpringBootShop.project.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductService {
    // 인터페이스 사용 의존성 주입
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 상품 등록
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // 전체 상품 조회
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // 조건 상품 조회 - id
    public Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("조건에 맞는 상품이 없습니다"));
    }

    // 조건 상품 조회 - 이름 - findByProductName으로 변경
    public Product findByName(String productName) {
        return productRepository.findByProductName(productName)
                .orElseThrow(() -> new IllegalArgumentException("조건에 맞는 상품이 없습니다"));
    }

    // 상품 삭제 - productId 기반
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    // 상품 업데이트
    public Product update(Long id, ProductForm form) {
        Product product = findById(id);
        // 상품 이름 수정
        if (form.getProductName() != null) {
            product.setProductName(form.getProductName());
        }
        // 상품 가격 수정
        if (form.getPrice() != null) {
            product.setPrice(form.getPrice());
        }
        // 상품 재고 수정
        if (form.getStock() != null) {
            product.setStock(form.getStock());
        }
        return product;
    }
}
