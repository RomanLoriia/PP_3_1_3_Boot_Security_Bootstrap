package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import javax.transaction.Transactional;

import java.util.ArrayList;

import java.util.List;



@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private List<Role> roles;

    public void listRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

    }

    @Transactional

    public void saveUser(User user, long[] listRoles) {
        List<Role> rolesList = new ArrayList<>();
        for (int i = 0; i < listRoles.length; i++) {
            rolesList.add(roleRepository.findById(listRoles[i]));
        }


        user.setRoles(rolesList);
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(User user, long[] role_id) {
        List<Role> rolesList = new ArrayList<>();
        for (int i = 0; i < role_id.length; i++) {
            rolesList.add(roleRepository.findById(role_id[i]));
        }

        user.setRoles(rolesList);
        userRepository.save(user);
    }



    public List<User> findAll() {
        return userRepository.findAll();
    }


    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }


    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public void deleteById(User user) {
        userRepository.delete(user);
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown user" + " " + username);
        }
        return user;
    }
}






