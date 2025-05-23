package me.hwangjoonsoung.pefint.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.customException.InvalidUserFormException;
import me.hwangjoonsoung.pefint.domain.User;
import me.hwangjoonsoung.pefint.dto.UserForm;
import me.hwangjoonsoung.pefint.dto.UserInfo;
import me.hwangjoonsoung.pefint.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/alluser")
    public String getAllUser(Model model){
        List<User> allUser = userService.getAllUser();
        System.out.println("allUser = " + allUser);
        model.addAttribute("users", allUser);
        return "/user/users";
    }

    @GetMapping("/new")
    public String newUser(){
        return "/user/new";
    }

    @PostMapping("/new")
    public String newUser(@Valid UserForm userForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new InvalidUserFormException("유효하지 않은 값입니다");
        }
        userService.join(userForm);
        return "redirect:/user/new";
    }

    @GetMapping("/login")
    public String userLogin(){
        return "/user/login";
    }

    @GetMapping("/i/{id}")
    public String findUserById(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        System.out.println(user);
        model.addAttribute("user", user);
        return "/user/info";
    }

    @GetMapping("/success")
    public String loginSuccess(HttpServletRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            System.out.println(auth.getName() +": "+auth.isAuthenticated() );
        }
        return "/user/LoginSuccess";
    }

    @GetMapping("/profile")
    public String userProfile(@AuthenticationPrincipal UserInfo userInfo, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            String name = authentication.getName();
            System.out.println(name);
        }
        return "/user/profile";
    }

    @GetMapping("/profile2")
    public String userProfile2(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        System.out.println(username);
        return "/user/profile";
    }

}
