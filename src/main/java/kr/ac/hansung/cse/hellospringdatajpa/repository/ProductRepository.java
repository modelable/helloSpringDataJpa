package kr.ac.hansung.cse.hellospringdatajpa.repository;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByName(String name);
    // List<Product>의 내용이 많을 수 있으므로 Pageable 객체로 페이지를 명시
    List<Product> findByNameContaining(String searchKeyword, Pageable paging);

    // where the name attribute contains a specific substring
    // JPQL을 사용한 사용자 정의 쿼리를 명시, %는 0개 이상 문자와 일치(와일드카드)
    @Query("select p from Product p where p.name like %?1%")
    List<Product> searchByName(String name);
}
