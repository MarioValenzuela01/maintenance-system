package com.cornerstone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Ahora devuelve directamente el diseño de la página de inicio
        return "home";
    }
}