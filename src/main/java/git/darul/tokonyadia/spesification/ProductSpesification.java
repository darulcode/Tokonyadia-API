package git.darul.tokonyadia.spesification;

import git.darul.tokonyadia.dto.request.SearchProductRequest;
import git.darul.tokonyadia.entity.Customer;
import git.darul.tokonyadia.entity.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductSpesification {

    public static Specification<Product> menuSpecifiation(SearchProductRequest request) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.hasText(request.getName())) {
                    Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%");
                    predicates.add(namePredicate);
                }
                if (request.getMinPrice() != null && request.getMaxPrice() != null ) {
                    Predicate minMaxPredicate = criteriaBuilder.between(root.get("price"), request.getMinPrice(), request.getMaxPrice());
                    predicates.add(minMaxPredicate);
                } else if (request.getMinPrice() != null ){
                    Predicate minPredicate = criteriaBuilder.between(root.get("price"),request.getMinPrice(), Long.MAX_VALUE);
                    predicates.add(minPredicate);
                } else if(request.getMaxPrice() != null ){
                    Predicate maxPredicate = criteriaBuilder.between(root.get("price"), 0L, Long.MAX_VALUE);
                    predicates.add(maxPredicate);
                }

                if (predicates.isEmpty()) {return criteriaBuilder.conjunction();}
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
