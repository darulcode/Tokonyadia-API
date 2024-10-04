package git.darul.tokonyadia.spesification;

import git.darul.tokonyadia.dto.request.SearchCustomerRequest;
import git.darul.tokonyadia.entity.Customer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpesification {

    public static Specification<Customer> spesificationCustomer(SearchCustomerRequest request){
        return new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (request.getName() == null) return criteriaBuilder.conjunction();
                return  criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%");
            }
        };
    }
}
