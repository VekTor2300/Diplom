package com.example.individualpr.Controllers;

import com.example.individualpr.Models.Employee;
import com.example.individualpr.Models.Post;
import com.example.individualpr.Models.User;
import com.example.individualpr.Repos.EmployeeRepos;
import com.example.individualpr.Repos.PostRepos;
import com.example.individualpr.Repos.UserRepos;
import com.example.individualpr.RoleChek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/adminPost")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class PostController {

    @Autowired
    private PostRepos postRepos;

    @GetMapping
    public String postList(Model model){
        Iterable<Post> posts = postRepos.findAll();
        model.addAttribute("postest",posts);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        return "Post/main";
    }

    @GetMapping("/add")
    public String postAdd(Post post, Model model){
        Post postTitle = postRepos.findByTitlepost(post.getTitlepost());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        Iterable<Post> posts = postRepos.findAll();
        model.addAttribute("postest",posts);
        return "Post/add";
    }

    @PostMapping("/add")
    public String postAdd(@ModelAttribute("post")@Valid Post post,
                              BindingResult bindingResult, Model model){
        Post postTitle = postRepos.findByTitlepost(post.getTitlepost());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        if(bindingResult.hasErrors() || postTitle != null){
            model.addAttribute("message", "Такая должность уже существует");
            return "Post/add";
        }
        postRepos.save(post);
        return "redirect:/adminPost";
    }

    @GetMapping("/edit/{post}")
    public String postEdit(Post post, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        model.addAttribute("posts", postRepos.findAll());
        return "Post/edit";
    }

    @PostMapping("/edit/{post}")
    public String postPostEdit(
            @ModelAttribute("post") @Valid Post post,
            BindingResult bindingResult,
            Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        if (bindingResult.hasErrors()) {
            return "Post/edit";
        }
        postRepos.save(post);
        return "redirect:../";
    }

    @GetMapping("/del/{post}")
    public String postDel(
            Post post, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        postRepos.delete(post);
        return "redirect:../";
    }
}
