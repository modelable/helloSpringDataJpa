package kr.ac.hansung.cse.hellospringdatajpa.controller;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Product;
import kr.ac.hansung.cse.hellospringdatajpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/")
    public String viewHomePage(Model model) {

        List<Product> listProducts = service.listAll();
        model.addAttribute("listProducts", listProducts);

        return "index";
    }

    @GetMapping("/new")
    public String showNewProductPage(Model model) {

        Product product = new Product();
        model.addAttribute("product", product);

        return "new_product";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductPage(@PathVariable(name = "id") int id, Model model) {

        Product product = service.get(id);
        model.addAttribute("product", product);

        return "edit_product";
    }

    // Data binding: @ModelAttribute vs @RequestBody
    // 1. @ModelAttribute는 Form data(예: name=Laptop&brand=Samsung&madeIn=Korea&price=1000.00)를 Product 객체에 매핑
    // 2. @RequestBody는 HTTP 요청 body에 포함된 JSON 데이터(예: {"name": "Laptop", "brand": "Samsung",
    // "madeIn": "Korea", "price": 1000.00})를 Product 객체에 매핑
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {

        service.save(product);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") int id) {

        service.delete(id);
        return "redirect:/";
    }
}
