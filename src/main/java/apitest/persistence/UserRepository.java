package apitest.persistence;

import apitest.domain.TestUser;

import java.util.List;


public interface UserRepository {
    List<TestUser> findAll();
    TestUser findById(Long id);
    void save(TestUser testUser);
    void update(TestUser testUser);
    void deleteById(Long id);
}
