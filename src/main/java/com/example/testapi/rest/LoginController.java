package com.example.testapi.rest;

import com.example.testapi.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/login")
@RequiredArgsConstructor
public class LoginController {
    private final UserDetailsService userDetailsService;

    @PostMapping(value = "", consumes = {"application/json;charset=UTF-8"})
    @PreAuthorize("permitAll()")
    public ResponseEntity<Integer> addArticle(@RequestBody User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        if (userDetails != null) {
            return ResponseEntity.ok(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().contains("ROLE_ADMIN") ? 100 : 10);
        }
        return ResponseEntity.ok(0);
    }
}
