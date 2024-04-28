package api;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getEntityById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/")
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userService.saveEntity(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/")
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        userService.updateEntity(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.removeEntityById(id);
        return ResponseEntity.ok().build();
    }
}
