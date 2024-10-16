package git.darul.tokonyadia.controller;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.request.CategoryRequest;
import git.darul.tokonyadia.dto.request.PagingAndShortingRequest;
import git.darul.tokonyadia.dto.response.CategoryResponse;
import git.darul.tokonyadia.dto.response.PagingResponse;
import git.darul.tokonyadia.service.CategoryService;
import git.darul.tokonyadia.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.CATEGORY_API)
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest request) {
        CategoryResponse category = categoryService.createCategory(request);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Successfully created category", category);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody CategoryRequest request) {
        CategoryResponse category = categoryService.updateCategory(request);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully updated category", category);
    }

    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully deleted category", null);
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                              @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        PagingAndShortingRequest request = PagingAndShortingRequest.builder()
                .page(page)
                .size(size)
                .build();

        Page<CategoryResponse> allCategories = categoryService.getAllCategories(request);
        return ResponseUtil.buildResponsePage(HttpStatus.OK, "Successfully fetch all category", allCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable String id) {
        CategoryResponse category = categoryService.getCategoryById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully fetch category", category);
    }
}
