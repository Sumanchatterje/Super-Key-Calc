package com.example.super_keys.controller;

import com.example.super_keys.model.SuperKeyRequest;
import com.example.super_keys.service.SuperKeysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/superkeys")
@CrossOrigin(origins = "*") // Allows frontend to access this API
public class SuperKeysController {

    @Autowired
    private SuperKeysService superKeysService;

    @PostMapping
    public List<Set<String>> getSuperKeys(@RequestBody SuperKeyRequest request) {
        return superKeysService.findAllSuperkeys(request);
    }
}
