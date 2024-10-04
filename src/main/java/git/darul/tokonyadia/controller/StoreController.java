package git.darul.tokonyadia.controller;


import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.request.SearchCustomerRequest;
import git.darul.tokonyadia.dto.request.SearchStoreRequest;
import git.darul.tokonyadia.dto.request.StoreRequest;
import git.darul.tokonyadia.dto.response.StoreResponse;
import git.darul.tokonyadia.service.StoreService;
import git.darul.tokonyadia.util.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.STORE_API)
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public ResponseEntity<?> getAllStores(@RequestParam(name = "page",required = false, defaultValue = "1") Integer page,
                                          @RequestParam(name = "size",required = false, defaultValue = "10") Integer size,
                                          @RequestParam(name = "name", required = false) String name) {

        SearchStoreRequest storeRequest = SearchStoreRequest.builder()
                .name(name)
                .page(page)
                .size(size)
                .build();
        Page<StoreResponse> storeResult = storeService.getAll(storeRequest);
        return ResponseUtil.buildResponsePaging(HttpStatus.OK, "Succesfuller Get Data Customer", storeResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStoreById(@PathVariable String id) {
        StoreResponse store = storeService.getById(id);
        if (store == null) {
            return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST,
                    "Id not found",
                    store);
        }
        return ResponseUtil.buildResponse(HttpStatus.OK,
                 "Succesfully get data store",
                 store);
    }

    @PostMapping
    public ResponseEntity<?> createStore(@RequestBody StoreRequest request) {
        try {
            StoreResponse storeResponse = storeService.create(request);
            return ResponseUtil.buildResponse(HttpStatus.CREATED, "Successfully created a new store", storeResponse);
        } catch (Exception e) {
            return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStore(@PathVariable String id, @RequestBody StoreRequest request) {
        StoreResponse storeResult = storeService.update(id, request);
        if (storeResult == null) {
            return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST,
                    "Id not found",
                    null);
        }
        return ResponseUtil.buildResponse(HttpStatus.OK,
                "Succesfully update data store",
                storeResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStore(@PathVariable String id) {
        return  storeService.delete(id) ?
                ResponseUtil.buildResponse(
                        HttpStatus.OK,
                        "Succesfully Delete Store",
                        null
                ) : ResponseUtil.buildResponse(
                HttpStatus.BAD_REQUEST,
                "Id Store not found",
                null) ;
    }

}
