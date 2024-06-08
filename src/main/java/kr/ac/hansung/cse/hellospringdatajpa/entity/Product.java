package kr.ac.hansung.cse.hellospringdatajpa.entity;

import jakarta.persistence.*; //java.persistence의 최신 버전
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity // Entity Class, 테이블 자동 생성
@Table(name = "product") //database table name
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    private String madeIn;
    private double price;

    public Product(String name, String brand, String madeIn, double price) {
        this.name = name;
        this.brand = brand;
        this.madeIn = madeIn;
        this.price = price;
    }
}
