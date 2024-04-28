package apitest.persistence.impl;


import apitest.domain.TestUser;
import apitest.persistence.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, TestUser> users = new HashMap<>();
    private long nextId = 1;

    @Override
    public List<TestUser> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public TestUser findById(Long id) {
        return users.get(id);
    }

    @Override
    public void save(TestUser testUser) {
        if (testUser.getId() == null) {
            testUser.setId(nextId++);
        }
        users.put(testUser.getId(), testUser);
    }

    @Override
    public void update(TestUser testUser) {
        if (users.containsKey(testUser.getId())) {
            users.put(testUser.getId(), testUser);
        }
    }

    @Override
    public void deleteById(Long id) {
        users.remove(id);
    }
}

