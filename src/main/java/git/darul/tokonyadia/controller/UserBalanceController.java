package git.darul.tokonyadia.controller;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.request.UserBalanceRequest;
import git.darul.tokonyadia.dto.response.UserBalanceResponse;
import git.darul.tokonyadia.service.UserBalanceService;
import git.darul.tokonyadia.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping(Constant.USER_BALANCE_API)
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class UserBalanceController {

    private final UserBalanceService userBalanceService;

    @Operation(summary = "Update Balance")
    @PreAuthorize("hasRole('SELLER')")
    @PostMapping
    public ResponseEntity<?> updateBalance(@RequestBody UserBalanceRequest request) {
        userBalanceService.updateBalance(request);
        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_UPDATE_BALANCE, null);
    }

    @Operation(summary = "Get Balance")
    @GetMapping
    public ResponseEntity<?> getBalance() {
        UserBalanceResponse balance = userBalanceService.getBalance();
        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_GET_BALANCE_MESSAGE, balance);
    }
}
