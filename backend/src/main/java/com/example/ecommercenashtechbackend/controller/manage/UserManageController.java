package com.example.ecommercenashtechbackend.controller.manage;

import com.example.ecommercenashtechbackend.dto.request.UserRequestDto;
import com.example.ecommercenashtechbackend.dto.request.UserStatusRequestDto;
import com.example.ecommercenashtechbackend.dto.request.UserUpdateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.ResponseDto;
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

    @GetMapping("/all")
    public ResponseEntity<ResponseDto> getAllUser(@RequestParam boolean deleted) {
        List<UserResponseDto> userResponseDtoList = userService.getAllUsers(deleted);
        ResponseDto<List<UserResponseDto>> responseDto =  new ResponseDto<>(200, userResponseDtoList, "Get list users successfully");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getListUserPaginationFirstPage() {
        ResponseDto<List<User>> responseDto = getListUserPagination(1, 4, "email", "asc", null, false).getBody();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{pageNumber}")
    public ResponseEntity<ResponseDto> getListUserPagination(@PathVariable("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortField") String sortField,
                                                             @RequestParam("sortName") String sortName, @RequestParam("keyword") String keywork,
                                                             @RequestParam(value = "deleted", required = false) Boolean deleted) {
        deleted = deleted == null ? false : deleted;
        List<User> users = userService.getListUser(pageNumber, pageSize, sortField, sortName, keywork, deleted);
        ResponseDto<List<User>> responseDto = new ResponseDto<>(200, users, "Get list users successfully");
        return ResponseEntity.ok(responseDto);
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
    @PostMapping
    public ResponseEntity<ResponseDto> createUser(@Validated @RequestBody UserRequestDto userRequestCreateDto) {
        UserResponseDto userResponseDto = userService.createUser(userRequestCreateDto);
        ResponseDto<UserResponseDto> responseDto = new ResponseDto<>(201, userResponseDto, "Create user successfully");
        return ResponseEntity.ok(responseDto);
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
    @PutMapping
    public ResponseEntity<ResponseDto> updateUser(@Validated @RequestBody UserUpdateRequestDto userRequestUpdateDto) {
        UserResponseDto userResponseDto = userService.updateUser(userRequestUpdateDto);
        ResponseDto<UserResponseDto> responseDto = new ResponseDto<>(200, userResponseDto, "Update user successfully");
        return ResponseEntity.ok(responseDto);
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
    @PutMapping("/update-block-user")
    public ResponseEntity<ResponseDto> updateBlockUser(@Validated @RequestBody UserStatusRequestDto userStatusRequestDto) {
        UserResponseDto userUpdated = userService.updateBlockUser(userStatusRequestDto);
        ResponseDto<UserResponseDto> responseDto = new ResponseDto<>(200, userUpdated, "Update user successfully");
        return ResponseEntity.ok(responseDto);
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
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        ResponseDto<UserResponseDto> responseDto = new ResponseDto<>(200, null, "Deleted user");
        return ResponseEntity.ok(responseDto);
    }
}


