package com.example.ecommercenashtechbackend.controller.customer;

import com.example.ecommercenashtechbackend.common.RoleName;
import com.example.ecommercenashtechbackend.dto.request.RefreshTokenRequestDto;
import com.example.ecommercenashtechbackend.dto.request.UserLoginRequestDto;
import com.example.ecommercenashtechbackend.dto.request.UserRequestDto;
import com.example.ecommercenashtechbackend.dto.response.JwtResponse;
import com.example.ecommercenashtechbackend.dto.response.UserLoginResponseDto;
import com.example.ecommercenashtechbackend.dto.response.UserResponseDto;
import com.example.ecommercenashtechbackend.exception.ExceptionResponse;
import com.example.ecommercenashtechbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;

    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success, user registered",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Missing require field see message for more details",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            @ApiResponse(responseCode = "409", description = "Email is already in use",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Validated @RequestBody UserRequestDto userRequestDto) {
        userRequestDto.setRole(RoleName.ROLE_USER);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequestDto));
    }

    @Operation(summary = "Login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success, user logged in",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Missing require field see message for more details",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Invalid email or password",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
    })

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody UserLoginRequestDto userLoginDto) {
        UserLoginResponseDto userLoginResponseDto = userService.login(userLoginDto);
        return ResponseEntity.ok(userLoginResponseDto);
    }

    @Operation(summary = "Refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success, token refreshed",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Invalid refresh token",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
    })

    @PutMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Validated @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        JwtResponse jwtResponse = userService.refreshToken(refreshTokenRequestDto);
        return ResponseEntity.ok(jwtResponse);
    }
}
