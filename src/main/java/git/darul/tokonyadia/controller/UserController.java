package git.darul.tokonyadia.controller;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.request.UserRequest;
import git.darul.tokonyadia.dto.request.UserSearchRequest;
import git.darul.tokonyadia.dto.response.UserResponse;
import git.darul.tokonyadia.service.UserService;
import git.darul.tokonyadia.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.USER_API)
public class UserController {
    private final UserService userService;

    @Operation(summary = "Register Customer")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        UserResponse user = userService.createUser(request);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, Constant.SUCCESS_REGISTERED_MESSAGE, user);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                         @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                         @RequestParam(name = "name", required = false) String name) {

        UserSearchRequest request = UserSearchRequest.builder()
                .page(page)
                .size(size)
                .name(name)
                .build();

        Page<UserResponse> allUser = userService.getAllUser(request);
        return ResponseUtil.buildResponsePage(HttpStatus.OK, "Success fetch all users", allUser);
    }

    @Operation(summary = "update Customer")
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserRequest request) {
        UserResponse user = userService.updateUser(request);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully updated user", user);
    }


}
