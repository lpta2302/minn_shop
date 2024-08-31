package dev.minn_shop.minn_shop.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDetailRecord> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailRecord> getUserById(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserBriefRecord>> getUsersDetails(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size) {
        return ResponseEntity.ok(userService.getUsersDetails(page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailRecord> updateUser(
            @Valid @RequestBody UserDetailRecord user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(
            @PathVariable("id") int id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

}
