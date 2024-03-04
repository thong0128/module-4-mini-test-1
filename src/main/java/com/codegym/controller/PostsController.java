package com.codegym.controller;

import com.codegym.model.Posts;
import com.codegym.model.PostsForm;
import com.codegym.service.IPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;
import java.io.IOException;

@Controller
@EnableWebMvc
@RequestMapping("posts")
@PropertySource("classpath:upload_file.properties")
public class PostsController {
    @Autowired
    private IPostsService postsService;
    @Value("${upload}")
    private String upload;
    @GetMapping("")
    public String home(Model model){
        model.addAttribute("posts", postsService.findAll());
        return "index";
    }
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("post", new PostsForm());
        return "create";
    }
    @PostMapping("/save")
    public String save(PostsForm postsForm) throws IOException {
        MultipartFile multipartFile = postsForm.getImg();
        String fileName = multipartFile.getOriginalFilename();
        FileCopyUtils.copy(multipartFile.getBytes(), new File(upload+fileName));

        Posts posts = new Posts();
        posts.setName(postsForm.getName());
        posts.setTitle(postsForm.getTitle());
        posts.setDescription(postsForm.getDescription());
        posts.setImg(fileName);
        postsService.save(posts);
        return "redirect:/posts";
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Model model){
        model.addAttribute("posts", postsService.findById(id));
        return "delete";
    }
    @PostMapping("/delete")
    public String remove(Posts posts){
        postsService.remove(posts.getId());
        return "redirect:/posts";
    }
    @GetMapping("/{id}/view")
    public String detail(@PathVariable int id, Model model){
        model.addAttribute("post", postsService.findById(id));
        return "view";
    }
}
