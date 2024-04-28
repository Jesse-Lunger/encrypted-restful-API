package apitest.api;


import apitest.domain.TestUser;
import apitest.service.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("testUserController")
@RequestMapping("/api/users")
public class TestUserController {

    private final TestUserService testUserService;

    @Autowired
    public TestUserController(TestUserService testUserService) {
        this.testUserService = testUserService;
    }

    @GetMapping
    public ResponseEntity<List<TestUser>> getAllUsers() {
        List<TestUser> testUsers = testUserService.findAll();
        return new ResponseEntity<>(testUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestUser> getUserById(@PathVariable Long id) {
        TestUser testUser = testUserService.findById(id);
        if (testUser != null) {
            return new ResponseEntity<>(testUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody TestUser testUser) {
        testUserService.save(testUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody TestUser testUser) {
        testUser.setId(id); // Ensure the ID is set
        testUserService.update(testUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        testUserService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
