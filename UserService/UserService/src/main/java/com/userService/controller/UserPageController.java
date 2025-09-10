package com.userService.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserPageController {

    @GetMapping("/login")
    public String loginPage() {
        return "index"; // login.html
    }

    @GetMapping("/register")
    public String registerPage() {
        return "registration"; // register.html
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard";
    }

    @GetMapping("/playlists")
    public String playlistsPage() {
        return "playlists";
    }

    @GetMapping("/songs")
    public String songsPage() {
        return "songs";
    }
}
