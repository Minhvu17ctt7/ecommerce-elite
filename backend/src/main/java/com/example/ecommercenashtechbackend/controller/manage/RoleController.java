package com.example.ecommercenashtechbackend.controller.manage;

import com.example.ecommercenashtechbackend.dto.response.ResponseDto;
import com.example.ecommercenashtechbackend.entity.Role;
import com.example.ecommercenashtechbackend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<Role>>> getAllRoles() {
        List<Role> roleList = roleService.getAllRoles();
        ResponseDto<List<Role>> responseDto = new ResponseDto<>(200, roleList, "Get all roles successfully");
        return ResponseEntity.ok(responseDto);
    }
}
