package com.attedance_sys.demo.services;

import com.attedance_sys.demo.Repositories.UserReository;
import com.attedance_sys.demo.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserReository userReository;

    public UserService(UserReository userReository) {
        this.userReository = userReository;
    }
    public List<User> findAll() {
        return userReository.findAll();
    }
    public User addUser(User user) {
       return userReository.save(user);
    }
    public User getUserById(Long id){
        return userReository.findById(id).get();
    }

    public User getUserBymail(String mail, String pasword){
        List<User> users=userReository.findAllByEmail(mail);
        User user=users.get(0);
        if(user.getPassword().trim().equals(pasword.trim())){
            return user;
        }
        else return null;
    }
}
