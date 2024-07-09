package com.nhom7.qltd.controller;


import com.nhom7.qltd.model.Comment;
import com.nhom7.qltd.model.Contact;
import com.nhom7.qltd.model.TinTuc;
import com.nhom7.qltd.model.User;
import com.nhom7.qltd.service.CommentService;
import com.nhom7.qltd.service.TinTucCategoryService;
import com.nhom7.qltd.service.TinTucService;
import com.nhom7.qltd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tintuc")
public class TintucController {
    @Autowired
private final TinTucService tintucService;
    @Autowired
private  final TinTucCategoryService tinTucCategoryService;
@Autowired
private final CommentService commentService;
@Autowired
private final UserService userService ;
    public TintucController(TinTucService tintucService, TinTucCategoryService tinTucCategoryService, CommentService commentService, UserService userService) {
        this.tintucService = tintucService;
        this.tinTucCategoryService = tinTucCategoryService;
        this.commentService = commentService;
        this.userService = userService;
    }
    @GetMapping("")
    public String tintuc(Model model) {
        model.addAttribute("categories", tinTucCategoryService.getAllTinTucCategories());
        model.addAttribute("news", tintucService.getActiveNews());
        return "tintuc/index";
    }
   @GetMapping("/")
    public String getPostsByCategory(@RequestParam("categoryId") Integer categoryId, Model model) {
        List<TinTuc> posts = tintucService.getPostsByCategory(categoryId);
       model.addAttribute("categories", tinTucCategoryService.getAllTinTucCategories());
        model.addAttribute("news", tintucService.getPostsByCategory(categoryId));
        return "tintuc/index2";
    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        TinTuc post = tintucService.getPostById(id)
           .orElseThrow(() -> new IllegalArgumentException("Invalid Post Id:" + id));
        model.addAttribute("post", post);
        model.addAttribute("comments", commentService.getAllCommentByNewsId(id));
        model.addAttribute("news", tintucService.getActiveNews());
        model.addAttribute("categories", tinTucCategoryService.getAllTinTucCategories());
        return "tintuc/detail";
    }
    @PostMapping("/comment/{id}")
    public String comment(@PathVariable Integer id, @RequestParam String MessageContent,   Model model) {
        Comment comment = new Comment();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + username));
        TinTuc post = tintucService.getPostById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Post Id:" + id));
        comment.setNews(post);
        comment.setUser(user);
        comment.setContent(MessageContent);
        comment.setTimeUpload(java.time.LocalDateTime.now());
        commentService.addComment(comment);
        return "redirect:/tintuc/detail/" + id;
    }
}
