package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;


@Controller
//@RestController
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping(value = "/users")
    @GetMapping(value = "")
    public String listUsersAdmin(Model model) {
        model.addAttribute("users", userService.listUsers());

        //21-05 new USER
        model.addAttribute("user", new User());

        //21-05 edit USER
//        model.addAttribute("user2", userService.getUserById(id));

        //28-05 ROLES
        model.addAttribute("roles",userService.getRoles());

        return "usersadmin";
    }


//    //22-05 test
//    @GetMapping("/users-update/{id}/edit")
//    public String edit2(@PathVariable("id") int id, Model model) {
//        model.addAttribute("user", userService.getUserById(id));
//        return "edit";
//    }


    @GetMapping("/users/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "show";
    }


    @PostMapping("/users")
    public String createUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    //20-05 rework+++ UPDATE GET
    @GetMapping("/users-update/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    //20-05 rework+++ UPDATE GET-EDIT button
    @GetMapping("/users-update/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    //20-05 rework+++ UPDATE GET-DELETE button
    @GetMapping("/users-delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
        //return null;
    }


}