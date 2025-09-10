package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @GetMapping("/login")
    public String loginPage() {
        return "index"; // index.html
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard";
    }

    @GetMapping("/songs")
    public String songsPage() {
        return "songs";
    }

    @GetMapping("/albums")
    public String albumsPage() {
        return "albums";
    }

    @GetMapping("/notifications")
    public String notificationsPage() {
        return "notifications";
    }
}
