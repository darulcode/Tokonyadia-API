package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.constant.CategoryStatus;
import git.darul.tokonyadia.constant.UserType;
import git.darul.tokonyadia.dto.request.CategoryRequest;
import git.darul.tokonyadia.dto.request.PagingAndShortingRequest;
import git.darul.tokonyadia.dto.response.CategoryResponse;
import git.darul.tokonyadia.entity.Category;
import git.darul.tokonyadia.entity.UserAccount;
import git.darul.tokonyadia.repository.CategoryRepository;
import git.darul.tokonyadia.service.CategoryService;
import git.darul.tokonyadia.util.AuthenticationContextUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        UserAccount userAccount = AuthenticationContextUtil.getCurrentUser();

        if (userAccount.getUserType().equals(UserType.ROLE_BUYER))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");

        Category category = Category.builder().name(request.getName()).status(CategoryStatus.ACTIVE).build();
        Category categoryByName = categoryRepository.findByName(request.getName());
        if (categoryByName != null) throw new ResponseStatusException(HttpStatus.CONFLICT, "Category name already exists");
        categoryRepository.save(category);
        return CategoryResponse.builder().id(category.getId()).statusCategory(category.getStatus().getDescription()).name(category.getName()).build();
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest request) {
        UserAccount userAccount = AuthenticationContextUtil.getCurrentUser();

        if (userAccount.getUserType().equals(UserType.ROLE_BUYER))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");

        Category category = getOne(request.getId());
        category.setName(request.getName());
        categoryRepository.save(category);
        return CategoryResponse.builder().id(category.getId()).statusCategory(category.getStatus().getDescription()).name(category.getName()).build();

    }

    @Override
    public void deleteCategory(String id) {
        UserAccount userAccount = AuthenticationContextUtil.getCurrentUser();

        if (userAccount.getUserType().equals(UserType.ROLE_BUYER))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");

        Category category = getOne(id);
        category.setStatus(CategoryStatus.INACTIVE);
        categoryRepository.save(category);
    }

    @Override
    public Category getOne(String id) {
        Optional<Category> category = categoryRepository.findByIdAndStatus(id, CategoryStatus.ACTIVE);
        return category.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
    }

    @Override
    public Page<CategoryResponse> getAllCategories(PagingAndShortingRequest request) {

        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        Page<Category> categories;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //TODO: membaguskan nested percabangan
        if ( authentication.getPrincipal() == "anonymousUser") {
            categories = categoryRepository.findAllByStatus(pageable, CategoryStatus.ACTIVE);
        } else {
            UserAccount userAccount = (UserAccount) authentication.getPrincipal();
            if (userAccount.getUserType().equals(UserType.ROLE_BUYER)) {
                log.info("User role :{}", userAccount.getUserType());
                categories = categoryRepository.findAllByStatus(pageable, CategoryStatus.ACTIVE);
            } else {
                log.info("User role :{}", userAccount.getUserType());
                System.out.println("Masuk ke findAll");
                categories = categoryRepository.findAll(pageable);
            }
        }
        return categories.map(new Function<Category, CategoryResponse>() {
            @Override
            public CategoryResponse apply(Category category) {
                return CategoryResponse.builder().id(category.getId()).name(category.getName()).statusCategory(category.getStatus().getDescription()).build();
            }
        });
    }

    @Override
    public CategoryResponse getCategoryById(String id) {
        Category category = getOne(id);
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .statusCategory(category.getStatus().name())
                .build();
    }
}
