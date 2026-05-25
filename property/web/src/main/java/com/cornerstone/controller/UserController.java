package com.cornerstone.controller;

import com.cornerstone.entity.AppUserEntity;
import com.cornerstone.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(AppUserRepository appUserRepository,
                          PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", appUserRepository.findAll());
        return "users/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("user", new AppUserEntity());
        return "users/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("user") AppUserEntity user,
                         @RequestParam("plainPassword") String plainPassword) {

        user.setPassword(passwordEncoder.encode(plainPassword));
        user.setEnabled(true);

        appUserRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        AppUserEntity user = appUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);
        return "users/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,
                       @ModelAttribute("user") AppUserEntity formUser,
                       @RequestParam(name = "plainPassword", required = false) String plainPassword) {

        AppUserEntity user = appUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(formUser.getUsername());
        user.setFullName(formUser.getFullName());
        user.setRole(formUser.getRole());
        user.setEnabled(formUser.getEnabled());
        user.setEmail(formUser.getEmail());

        if (plainPassword != null && !plainPassword.isBlank()) {
            user.setPassword(passwordEncoder.encode(plainPassword));
        }

        appUserRepository.save(user);

        return "redirect:/users";
    }

    @PostMapping("/disable/{id}")
    public String disable(@PathVariable("id") Long id) {
        AppUserEntity user = appUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEnabled(false);
        appUserRepository.save(user);

        return "redirect:/users";
    }
}