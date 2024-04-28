package apitest.service;

import apitest.persistence.UserRepository;
import apitest.domain.TestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestUserService implements UserRepository{

    private final UserRepository userRepository;

    @Autowired
    public TestUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<TestUser> findAll() {
        return userRepository.findAll();
    }

    public TestUser findById(Long id) {
        return userRepository.findById(id);
    }

    public void save(TestUser testUser) {
        userRepository.save(testUser);
    }

    public void update(TestUser testUser) {
        userRepository.update(testUser);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}

