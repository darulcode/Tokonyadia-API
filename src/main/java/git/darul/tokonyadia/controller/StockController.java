package git.darul.tokonyadia.controller;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.request.StockRequest;
import git.darul.tokonyadia.dto.response.StockResponse;
import git.darul.tokonyadia.service.StockService;
import git.darul.tokonyadia.util.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(Constant.STOCK_API)
public class StockController {

    private final StockService stockService;


    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<?> getStock(@RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                      @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                      @RequestParam(name = "productId", required = false) String productId,
                                      @RequestParam(name = "storeId", required = false) String storeId){
        StockRequest request = StockRequest.builder()
                .productId(productId)
                .storeId(storeId)
                .size(size)
                .page(page)
                .build();
        Page<StockResponse> allStock = stockService.getAllStock(request);
        return ResponseUtil.buildResponsePaging(HttpStatus.OK, "Succesfully get data Stock", allStock);
    }

    @PostMapping
    public ResponseEntity<?> addStock(@RequestBody StockRequest request) {
        StockResponse stock = stockService.createStock(request);
        if (stock == null) {
            return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "Product or store not found", stock);
        }
        return ResponseUtil.buildResponse(HttpStatus.OK, "Succesfully Created Stock", stock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStock(@PathVariable(name = "id") String id,@RequestBody StockRequest request) {
        StockResponse stockResponse = stockService.updateStock(id, request.getStock());
        if (stockResponse == null) {
            return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "Product or store not found", stockResponse);
        }
        return ResponseUtil.buildResponse(HttpStatus.OK, "Succesfully Updated Stock", stockResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") String id) {
        StockResponse stockResponse = stockService.getById(id);
        if (stockResponse == null) {
            return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "Product or store not found", stockResponse);
        }
        return ResponseUtil.buildResponse(HttpStatus.OK, "Succesfully Get Stock", stockResponse);
    }
}
