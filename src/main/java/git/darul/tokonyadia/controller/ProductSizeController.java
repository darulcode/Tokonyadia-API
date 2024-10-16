package git.darul.tokonyadia.controller;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.request.ProductSizeRequest;
import git.darul.tokonyadia.service.ProductSizeService;
import git.darul.tokonyadia.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.PRODUCT_SIZE_API)
public class ProductSizeController {

    private final ProductSizeService productSizeService;

    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductSize(@PathVariable(name = "id") String id) {
        log.info("Deleting product size {}", id);
        productSizeService.deleteProductSize(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully delete size", null);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductSize(@PathVariable(name = "id") String id, @RequestBody ProductSizeRequest request) {
        request.setId(id);
        log.info("Updating product size {}", id);
        productSizeService.updateByid(request);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully update size", null);
    }

}
