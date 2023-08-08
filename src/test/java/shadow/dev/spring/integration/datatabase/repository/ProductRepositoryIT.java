package shadow.dev.spring.integration.datatabase.repository;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import shadow.dev.spring.datatabase.entity.Product;
import shadow.dev.spring.datatabase.repository.ProductRepository;
import shadow.dev.spring.integration.IntegrationTestBase;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductRepositoryIT extends IntegrationTestBase {
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    @Transactional("productTransactionManager")
    public void setUp() {
        productRepository.save(Product.from(1001, "Book", 21));
        productRepository.save(Product.from(1002, "Coffee", 10));
        productRepository.save(Product.from(1003, "Jeans", 30));
        productRepository.save(Product.from(1004, "Shirt", 32));
        productRepository.save(Product.from(1005, "Bacon", 10));
    }

    @Test
    void whenRequestingFirstPageOfSizeTwo_ThenReturnFirstPage() {

        var pageRequest = PageRequest.of(0, 2);

        var result = productRepository.findAll(pageRequest);

        assertThat(result.getContent(), hasSize(2));
        assertThat(result.getTotalPages(), equalTo(3));
        assertThat(result.getTotalElements(), equalTo(5L));
        assertThat(result.nextOrLastPageable(), equalTo(PageRequest.of(1,2)));
;
        assertTrue(result.stream()
                .map(Product::getId)
                .peek(d-> System.out.println("----"+d))
                .allMatch(id -> Arrays.asList(1001L, 1002L).contains(id)));
    }

    @Test
    void whenRequestingLastPage_ThenReturnLastPageWithRemData() {
        var pageRequest = PageRequest.of(2, 2);
        var result = productRepository.findAll(pageRequest);
        assertThat(result.getContent(), hasSize(1));
        assertTrue(result.stream().map(Product::getId)
                .peek(d-> System.out.println("----"+d))
                .allMatch(id -> Arrays.asList(1005L).contains(id)));
    }

    @Test
    void whenRequestingSecondPageOfSizeTwo_ThenReturnSecondPage() {
        var pageRequest = PageRequest.of(1, 2);
        var result = productRepository.findAll(pageRequest);
        assertThat(result.getContent(), hasSize(2));
        assertTrue(result.stream().map(Product::getId).allMatch(id -> Arrays.asList(1003L, 1004L).contains(id)));
    }

    @Test
    void whenSortingByNameAscAndPaging_ThenReturnSortedPagedResult() {
        var pageRequest = PageRequest.of(0, 3, Sort.by("name"));
        var result = productRepository.findAll(pageRequest);
        assertThat(result.getContent(), hasSize(3));
        assertThat(result.getContent()
                .stream().map(Product::getId)
                .collect(Collectors.toList()),
                        equalTo(Arrays.asList(1005L, 1001L, 1002L)));
    }

    @Test
    void whenSortingByPriceDescAndPaging_ThenReturnSortedPagedResult() {
        var pageRequest = PageRequest.of(0, 3, Sort.by("price").descending());

        var result = productRepository.findAll(pageRequest);

        assertThat(result.getContent(), hasSize(3));
        assertThat(result.getContent()
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList()), equalTo(Arrays.asList(1004L, 1003L, 1001L)));
    }

    @Test
    void whenSortingByPriceDescAndNameAscAndPaging_thenReturnSortedPagedResult() {
        var pageRequest = PageRequest.of(0, 5, Sort.by("price").descending()
                .and(Sort.by("name")));

        var result = productRepository.findAll(pageRequest);

        assertThat(result.getContent(), hasSize(5));
        assertThat(result.getTotalPages(), equalTo(1));
        assertThat(result.getContent()
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList()), equalTo(Arrays.asList(1004, 1003, 1001, 1005, 1002)));
    }

    @Test
    void whenRequestingFirstPageOfSizeTwoUsingCustomMethod_ThenReturnFirtsPage() {
        var pageRequest = PageRequest.of(0, 2);

        var result = productRepository.findAllByPrice(10, pageRequest);

        assertThat(result, hasSize(2));
        assertTrue(result.stream()
                .map(Product::getId)
                .allMatch(id -> Arrays.asList(1002L, 1005L)
                        .contains(id)));
    }
}
