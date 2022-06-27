package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Component
public class GenerateAdmin {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public GenerateAdmin(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    private Role role1 = new Role(1L, "ROLE_ADMIN");
    private Role role2 = new Role(2L, "ROLE_USER");
    private Role role3 = new Role(3L, "ROLE_MANAGER");
    private Role role4 = new Role(4L, "ROLE_DIRECTOR");
    private Role role5 = new Role(5L, "ROLE_ACCOUNTANT");

    public Role getRole1() {
        return role1;
    }

    public Role getRole2() {
        return role2;
    }

    public Role getRole3() {
        return role3;
    }

    public Role getRole4() {
        return role4;
    }

    public Role getRole5() {
        return role5;
    }

    @PostConstruct
    public void setAdmin() {
        roleRepository.save(getRole1());
        roleRepository.save(getRole2());
        roleRepository.save(getRole3());
        roleRepository.save(getRole4());
        roleRepository.save(getRole5());

        User admin = new User();
        Collection<Role> roles = new ArrayList<>();
        admin.setUsername("1");
        User userFromDB = userRepository.findByUsername(admin.getUsername());
        if (userFromDB == null) {
            admin.setPassword(new BCryptPasswordEncoder().encode("1"));
            roles.add(getRole1());
            roles.add(getRole2());
            admin.setFirstName("admin");
            admin.setLastName("adminov");
            admin.setAge((byte) 20);
            admin.setEmail("admin@mail.ru");
            admin.setRoles(roles);
            userRepository.save(admin);
        }
    }
}
