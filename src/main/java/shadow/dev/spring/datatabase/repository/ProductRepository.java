package shadow.dev.spring.datatabase.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import shadow.dev.spring.datatabase.entity.Product;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
    List<Product> findAllByPrice(double price, Pageable pageable);
}
