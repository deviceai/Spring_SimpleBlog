package net.codejava.blog.controllers;

import net.codejava.blog.models.Post;
import net.codejava.blog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {
    @Autowired
    PostRepository postRepository;
    @GetMapping("/api")
    public List<Post> api (){
        List<Post> posts = (List<Post>) postRepository.findAll();
        return posts;
    }
}
