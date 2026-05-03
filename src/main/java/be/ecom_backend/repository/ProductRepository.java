package be.ecom_backend.repository;

import be.ecom_backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("SELECT DISTINCT p.category FROM Product p WHERE p.category IS NOT NULL")
    List<String> findDistinctCategories();

    @Query("SELECT p FROM Product p WHERE " +
            "(:query = 'all' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
            "(:category = 'all' OR LOWER(p.category) LIKE LOWER(CONCAT('%', :category, '%'))) AND " +
            "(p.price >= :minPrice AND p.price <= :maxPrice) AND " +
            "(p.rating >= :minRating)")
    Page<Product> searchByFilter(@Param("query") String query,
                                 @Param("category") String category,
                                 @Param("minPrice") double minPrice,
                                 @Param("maxPrice") double maxPrice,
                                 @Param("minRating") double minRating,
                                 Pageable pageable);
}
