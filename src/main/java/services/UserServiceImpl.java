package services;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import models.User;
import repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addRandomUser() {
        User s = new User();
        s.setName("Ololosh");
        userRepository.save(s);
    }

    @Override
    public List<User> getAll() {
        return Lists.newArrayList(userRepository.findAll());
    }

    @PostConstruct
    private void init(){
        addRandomUser();
        addRandomUser();
        addRandomUser();
        addRandomUser();
        addRandomUser();
    }

}
