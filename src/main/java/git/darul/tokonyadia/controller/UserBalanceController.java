package git.darul.tokonyadia.controller;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.request.UserBalanceRequest;
import git.darul.tokonyadia.dto.response.UserBalanceResponse;
import git.darul.tokonyadia.service.UserBalanceService;
import git.darul.tokonyadia.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(Constant.USER_BALANCE_API)
@RestController
@RequiredArgsConstructor
public class UserBalanceController {

    private final UserBalanceService userBalanceService;

    @PostMapping
    public ResponseEntity<?> updateBalance(@RequestBody UserBalanceRequest amount) {
        userBalanceService.updateBalance(amount);
        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_UPDATE_BALANCE, null);
    }

    @GetMapping
    public ResponseEntity<?> getBalance() {
        UserBalanceResponse balance = userBalanceService.getBalance();
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully get balance", balance);
    }
}
