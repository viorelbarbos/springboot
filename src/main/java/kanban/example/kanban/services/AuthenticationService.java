package kanban.example.kanban.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import kanban.example.kanban.collections.User;
import kanban.example.kanban.dto.JwtAuthenticationResponse;
import kanban.example.kanban.dto.SignInRequest;
import kanban.example.kanban.dto.SignUpRequest;
import kanban.example.kanban.models.Role;
import kanban.example.kanban.pojo.SignupResponse;
import kanban.example.kanban.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public SignupResponse signup(SignUpRequest request) {
        var user = User
                .builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .createdAt((new Date()).toString())
                .updatedAt((new Date()).toString())
                .build();

        user = userService.createUser(user);

        if (user == null)
            return new SignupResponse("Email already exists", false);

        return new SignupResponse("User created successfully", true);
    }

    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("role", user.getRole());
        extraClaims.put("userId", user.getId());
        extraClaims.put("userName", user.getUserName());

        var jwt = jwtService.generateToken(user, extraClaims);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    public String getUserIdFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        return jwtService.getUserIdFromToken(token);
    }
}
