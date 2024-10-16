package git.darul.tokonyadia.service;

import git.darul.tokonyadia.dto.request.CategoryRequest;
import git.darul.tokonyadia.dto.request.PagingAndShortingRequest;
import git.darul.tokonyadia.dto.response.CategoryResponse;
import git.darul.tokonyadia.entity.Category;
import org.springframework.data.domain.Page;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse updateCategory(CategoryRequest categoryRequest);
    void deleteCategory(String id);
    Category getOne(String id);
    Page<CategoryResponse> getAllCategories(PagingAndShortingRequest request);
    CategoryResponse getCategoryById(String id);
}
