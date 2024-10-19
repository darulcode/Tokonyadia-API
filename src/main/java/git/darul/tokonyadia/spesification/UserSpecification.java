package git.darul.tokonyadia.spesification;

import git.darul.tokonyadia.dto.request.UserRequest;
import git.darul.tokonyadia.dto.request.UserSearchRequest;
import git.darul.tokonyadia.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> specificationUser(UserSearchRequest request) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (request.getName() == null) return criteriaBuilder.conjunction();
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%");
            }
        };

    }
}
