package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collection;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAdminPage() {
        return "admin";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model, @AuthenticationPrincipal Role role) {
        model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("authorisedRoles", role);
        return "list";
    }

    @GetMapping("/users/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping("/users")
    public String create(@ModelAttribute("user") User user, @RequestParam(value = "roles") Collection<Role> roles) {
        userService.saveUser(user, roles);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("allRoles", userService.allRoles());
        return "edit";
    }

    @PostMapping("/users/{id}")
    public String update(@ModelAttribute("user") User user, @RequestParam(value = "roles") Collection<Role> roles) {
        userService.updateUser(user, roles);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
