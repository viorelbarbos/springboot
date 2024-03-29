package kanban.example.kanban.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kanban.example.kanban.collections.User;
import kanban.example.kanban.dto.JwtAuthenticationResponse;
import kanban.example.kanban.dto.SignInRequest;
import kanban.example.kanban.dto.SignUpRequest;
import kanban.example.kanban.pojo.SignupResponse;
import kanban.example.kanban.services.AuthenticationService;
import kanban.example.kanban.utils.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<User>> signup(@RequestBody SignUpRequest request) {
        SignupResponse signupResponse = authenticationService.signup(request);

        if (!signupResponse.getSuccess()) {
            ApiResponse<User> response = ApiResponse.error(signupResponse.getMessage(), HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        ApiResponse<User> response = ApiResponse.success(signupResponse.getMessage(), HttpStatus.CREATED.value(),
                null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public JwtAuthenticationResponse signin(@RequestBody SignInRequest request) {
        return authenticationService.signin(request);
    }

}