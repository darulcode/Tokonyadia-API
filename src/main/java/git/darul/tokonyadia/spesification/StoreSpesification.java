package git.darul.tokonyadia.spesification;

import git.darul.tokonyadia.dto.request.SearchStoreRequest;
import git.darul.tokonyadia.entity.Store;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class StoreSpesification {
    public static Specification<Store> spesificationStore(SearchStoreRequest request) {
        return new Specification<Store>() {
            @Override
            public Predicate toPredicate(Root<Store> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (request.getName() == null) return criteriaBuilder.conjunction();
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%");
            }
        };
    }
}
