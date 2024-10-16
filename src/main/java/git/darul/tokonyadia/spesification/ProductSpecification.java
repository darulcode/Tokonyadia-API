package git.darul.tokonyadia.spesification;

import git.darul.tokonyadia.constant.ProductStatus;
import git.darul.tokonyadia.dto.request.ProductSearchRequest;
import git.darul.tokonyadia.entity.Product;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProductSpecification {

    public static Specification<Product> getSpecification(ProductSearchRequest request, Boolean active) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            log.info("Memasuki spesification");
            if (StringUtils.hasText(request.getQuery())) {
                Predicate queryPredicate =  criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + request.getQuery() + "%");
                predicates.add(queryPredicate);
            }
            // Tambahkan predicate untuk status ACTIVE
            if (active) {
                predicates.add(criteriaBuilder.equal(root.get("productStatus"), ProductStatus.AVAILABLE));
            }

            if (request.getMinPrice() != null && request.getMaxPrice() != null) {
                Predicate minMaxPredicate = criteriaBuilder.between(root.get("price"), request.getMinPrice(), request.getMaxPrice());
                predicates.add(minMaxPredicate);
            } else if (request.getMinPrice() != null) {
                Predicate minPredicate = criteriaBuilder.between(root.get("price"), request.getMinPrice(), Long.MAX_VALUE);
                predicates.add(minPredicate);
            } else if (request.getMaxPrice() != null) {
                Predicate maxPredicate = criteriaBuilder.between(root.get("price"), 0L, request.getMaxPrice());
                predicates.add(maxPredicate);
            }

            if (predicates.isEmpty()) return criteriaBuilder.conjunction();

            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }
}
