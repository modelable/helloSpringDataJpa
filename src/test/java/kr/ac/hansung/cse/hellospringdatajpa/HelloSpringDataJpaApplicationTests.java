package kr.ac.hansung.cse.hellospringdatajpa;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Product;
import kr.ac.hansung.cse.hellospringdatajpa.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// 통합 테스트: HelloSpringDataJpaApplication이 속한 패키지를 Scan한 뒤, 모든 Bean들을 Container에 저장
// @ExtendWith: junit 5에서 Spring과 관련된 기능 확장
@SpringBootTest // 통합 테스트(integration test) vs 단위 테스트(unit test)
@Transactional // 각 method에 대한 transaction 처리, 테스트 종료 후 Table Rollback
class HelloSpringDataJpaApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(HelloSpringDataJpaApplicationTests.class);

    @Test
    @DisplayName("Test1: findProductById")
    public void findProductById() {

        // Optional 객체: null 값을 처리할 때 발생할 수 있는 NullPointerException을 방지(Java 8)
        // 값이 있으면 해당 값을 포함하고, 없으면 비어 있습니다.
        Optional<Product> product = productRepository.findById(1L);

        // isPresent: isEmpty()와 비슷한 기능을 수행하는 Optional 클래스의 메소드
        assertTrue(product.isPresent(), "Product should be present");

        //lambda expression, parameters -> { statements; }, 익명 함수를 간결하게 작성할 수 있는 기능(Java 8)
        product.ifPresent(p -> {
            logger.info("Product found: {}", p);
        });
    }

    @Test
    @DisplayName("Test2: findAllProducts")
    public void findAllProducts() {

        //반환형: List<Product>
        List<Product> products = productRepository.findAll();
        assertNotNull(products);
        //lambda expression
        products.forEach(p -> logger.info("Product found: {}", p));
    }

    @Test
    @DisplayName("Test3: createProduct")
    public void createProduct() {

        Product product = new Product("OLED TV", "LG전자", "korea", 300.0);
        Product savedProduct = productRepository.save(product);

        Optional<Product> newProduct = productRepository.findById(savedProduct.getId());
        assertTrue(newProduct.isPresent(), "Product should be present");

        newProduct.ifPresent(p -> {
            logger.info("Product created: {}", p);
            assertEquals("OLED TV", p.getName());
        });
    }

    @Test
    @DisplayName("Test4: findByName")
    public void findByName() {

        Product product = productRepository.findByName("Galaxy S21");
        assertEquals("Galaxy S21", product.getName());
    }

    @Test
    @DisplayName("Test5: findByNameContainingWithPaging")
    public void findByNameContainingWithPaging() {

        // pageSize = 3, 첫번째 페이지
        Pageable paging = PageRequest.of(1, 3);
        List<Product> productList = productRepository.findByNameContaining("MacBook", paging);

        logger.info("====findByNameContainingWithPaging: MacBook=====");
        productList.forEach(product -> logger.info("--> {}", product));

        assertEquals(3, productList.size(), "Expected 3 products containing 'MacBook'");

    }

    @Test
    @DisplayName("Test6: findByNameContainingWithPagingAndSort")
    public void findByNameContainingWithPagingAndSort( ) {

        Pageable paging = PageRequest.of(0, 3, Sort.Direction.DESC, "id");

        List<Product> productList =
                productRepository.findByNameContaining("Galaxy", paging);

        logger.info("===findByNameContainingWithPagingAndSort: Galaxy====");
        productList.forEach(product -> logger.info("--> {}", product));

        assertEquals(3, productList.size(), "Expected 3 products containing 'Galaxy'");

    }

    @Test
    @DisplayName("Test7: searchByNameUsingQuery")
    public void searchByNameUsingQuery() {
        List<Product> productList= productRepository.searchByName("Air");

        logger.info("====searchByNameUsingQuery: Air====");
        productList.forEach(product -> logger.info("--> {}", product));

        assertEquals(6, productList.size(), "Expected 6 product containing 'Air'");
    }
}
