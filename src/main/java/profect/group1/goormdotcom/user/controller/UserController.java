package profect.group1.goormdotcom.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import profect.group1.goormdotcom.user.service.UserService;
import profect.group1.goormdotcom.user.controller.dto.request.RegisterRequestDto;
import profect.group1.goormdotcom.user.controller.dto.response.MeResponseDto;
import profect.group1.goormdotcom.user.controller.dto.request.ListRequestDto;
import profect.group1.goormdotcom.user.controller.dto.response.ListResponseDto;
import profect.group1.goormdotcom.user.controller.dto.response.RegisterResponseDto;
import profect.group1.goormdotcom.user.controller.dto.request.EditRequestDto;
import profect.group1.goormdotcom.user.controller.dto.request.LoginRequestDto;
import profect.group1.goormdotcom.user.controller.dto.response.LoginResponseDto;
import profect.group1.goormdotcom.user.service.dto.CreateUserDto;
import profect.group1.goormdotcom.user.domain.User;
import profect.group1.goormdotcom.apiPayload.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import profect.group1.goormdotcom.apiPayload.code.status.ErrorStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;
import profect.group1.goormdotcom.user.controller.auth.LoginUser;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserController implements UserApiDocs {
    
    private final UserService service;

    @PostMapping("/register")
    public ApiResponse<RegisterResponseDto> register(@RequestBody RegisterRequestDto body) {
        CreateUserDto userDto = CreateUserDto.builder()
            .name(body.getName())
            .email(body.getEmail())
            .password(body.getPassword())
            .role(body.getRole())
            .brandId(body.getBrandId())
            .build();

        try {
            User user = service.register(userDto);
            return ApiResponse.onSuccess(RegisterResponseDto.of(user));
        } catch (Exception e) {
            String code = ErrorStatus._INTERNAL_SERVER_ERROR.getCode();
            String message = ErrorStatus._INTERNAL_SERVER_ERROR.getMessage();
            System.out.println("e.getMessage()::: " + e.getMessage());

            switch (e.getMessage()) {
                case "Invalid password":
                    code = ErrorStatus.AUTH_INVALID_PASSWORD.getCode();
                    message = ErrorStatus.AUTH_INVALID_PASSWORD.getMessage();
                    break;
                case "Email already exists":
                    code = ErrorStatus.AUTH_EMAIL_ALREADY_EXISTS.getCode();
                    message = ErrorStatus.AUTH_EMAIL_ALREADY_EXISTS.getMessage();
                    break;
            }
            return ApiResponse.onFailure(String.valueOf(code), message, null);
        }
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponseDto> login(@RequestBody LoginRequestDto body) {
        try {
            String token = service.login(body.getEmail(), body.getPassword());
            return ApiResponse.onSuccess(LoginResponseDto.of(token));
        } catch (Exception e) {
            String code = ErrorStatus._INTERNAL_SERVER_ERROR.getCode();
            String message = ErrorStatus._INTERNAL_SERVER_ERROR.getMessage();

            switch (e.getMessage()) {
                case "Invalid credentials":
                    code = ErrorStatus.AUTH_NOT_EXISTS.getCode();
                    message = ErrorStatus.AUTH_NOT_EXISTS.getMessage();
                    break;
            }
            return ApiResponse.onFailure(String.valueOf(code), message, null);
        }
    }

    @PostMapping("/leave")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ApiResponse<Object> leave(@LoginUser UUID userId) {
        service.deleteById(userId);
        return ApiResponse.onSuccess(null);
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ApiResponse<Object> edit(@LoginUser UUID userId, @RequestBody EditRequestDto body) {
        try {
        service.edit(userId, body.getName(), body.getEmail());
            return ApiResponse.onSuccess(null);
        } catch (Exception e) {
            String code = ErrorStatus._INTERNAL_SERVER_ERROR.getCode();
            String message = ErrorStatus._INTERNAL_SERVER_ERROR.getMessage();
            switch (e.getMessage()) {
                case "Email already exists":
                    code = ErrorStatus.AUTH_EMAIL_ALREADY_EXISTS.getCode();
                    message = ErrorStatus.AUTH_EMAIL_ALREADY_EXISTS.getMessage();
                    break;
            }
            return ApiResponse.onFailure(String.valueOf(code), message, null);
        }
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('MASTER')")
    public ApiResponse<Object> delete(@PathVariable UUID userId) {
        service.deleteById(userId);
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/me")
    public ApiResponse<MeResponseDto> me(@LoginUser UUID userId) {
        User user = service.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        return ApiResponse.onSuccess(MeResponseDto.of(user));
    }

    @GetMapping
    @PreAuthorize("hasRole('MASTER')")
    public ApiResponse<ListResponseDto> users(ListRequestDto body) {
        List<User> users = service.findAllBy(body);
        return ApiResponse.onSuccess(ListResponseDto.of(users));
    }
}
