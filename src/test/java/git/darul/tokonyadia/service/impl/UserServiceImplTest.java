package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.constant.UserType;
import git.darul.tokonyadia.dto.request.UserRequest;
import git.darul.tokonyadia.dto.response.UserResponse;
import git.darul.tokonyadia.entity.User;
import git.darul.tokonyadia.entity.UserAccount;
import git.darul.tokonyadia.repository.UserRepository;
import git.darul.tokonyadia.service.UserAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserAccountService userAccountService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnUserResponseWhenCreateUser() {
        UserRequest request = new UserRequest("abdul", "abdul@gmail.com", "085268487440", "abdul", "abdul");
        UserAccount userAccount = new UserAccount();
        userAccount.setId("1");
        userAccount.setUserType(UserType.ROLE_BUYER);

        when(userAccountService.create(request)).thenReturn(userAccount);

        User user = new User();
        user.setUserAccount(userAccount);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);

        UserResponse response = userService.createUser(request);

        assertNotNull(response);
        assertEquals("abdul", response.getName());
        assertEquals("abdul@gmail.com", response.getEmail());
        assertEquals(UserType.ROLE_BUYER.getDescription(), response.getUserType());  // Memastikan deskripsi userType benar
        verify(userRepository, times(1)).saveAndFlush(any(User.class));
    }
}
