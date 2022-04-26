package com.example.ecommercenashtechbackend.controller.manage;

import com.example.ecommercenashtechbackend.dto.request.UserRequestDto;
import com.example.ecommercenashtechbackend.dto.request.UserStatusRequestDto;
import com.example.ecommercenashtechbackend.dto.request.UserUpdateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.UserResponseDto;
import com.example.ecommercenashtechbackend.entity.User;
import com.example.ecommercenashtechbackend.exception.ExceptionResponse;
import com.example.ecommercenashtechbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserManageController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getListUserFirstPage() {
        List<User> users = getListUserPagination(1, 4, "email", "asc", null, false).getBody();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{pageNumber}")
    public ResponseEntity<List<User>> getListUserPagination(@PathVariable("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortField") String sortField,
                                                            @RequestParam("sortName") String sortName, @RequestParam("keyword") String keywork,
                                                            @RequestParam(value = "deleted", required = false) Boolean deleted) {
        deleted = deleted == null ? false : deleted;
        List<User> users = userService.getListUser(pageNumber, pageSize, sortField, sortName, keywork, deleted);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Create new user by admin")
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
    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> createUser(@Validated @RequestBody UserRequestDto userRequestCreateDto) {
        UserResponseDto userResponseDto = userService.createUser(userRequestCreateDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @Operation(summary = "Update user by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success, user registered",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Missing require field see message for more details",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Not found user with this id",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
    })
    @PutMapping("/update")
    public ResponseEntity<UserResponseDto> updateUser(@Validated @RequestBody UserUpdateRequestDto userRequestUpdateDto) {
        UserResponseDto userResponseDto = userService.updateUser(userRequestUpdateDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @Operation(summary = "Update status user by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success, user registered",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Missing require field see message for more details",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Not found user with this id",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
    })
    @PutMapping("/update-status")
    public ResponseEntity<UserResponseDto> updateBlockUser(@Validated @RequestBody UserStatusRequestDto userStatusRequestDto) {
        UserResponseDto userUpdated = userService.updateBlockUser(userStatusRequestDto);
        return ResponseEntity.ok(userUpdated);
    }

    @Operation(summary = "Delete user by adin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success, user registered",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Not found user with this id",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User with id " + id + " deleted");
    }
}


