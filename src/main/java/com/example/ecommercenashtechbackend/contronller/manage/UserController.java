package com.example.ecommercenashtechbackend.contronller.manage;

import com.example.ecommercenashtechbackend.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class UserController {

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = new ArrayList<>();
        return ResponseEntity.ok(users);
    }



}
