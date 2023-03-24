package net.codejava.blog.controllers;

import net.codejava.blog.models.Post;
import net.codejava.blog.repo.PostRepository;
import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private PostRepository postRepository;
    @GetMapping("/blog")
    public String blogMain(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";
    }
    @PostMapping("/blog/add")
    public String blogPostAdd(
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String full_text){
        Post post = new Post(title,anons,full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogPostDetails(@PathVariable(value = "id") long id, Model model){
        if(postRepository.existsById(id)) {
            Optional<Post> post = postRepository.findById(id);
            ArrayList<Post> res = new ArrayStack<>();
            post.ifPresent(res::add);
            model.addAttribute("post", res);

            //increase postview counter
            Post postView = postRepository.findById(id).orElseThrow();
            postView.setView(postView.getView() + 1);
            postRepository.save(postView);

            return "blog-details";
        }
        else
            return "redirect:/blog";
    }

    @GetMapping("/blog/{id}/delete")
    public String blogPostDelete(@PathVariable(value="id") long id){
        if(postRepository.existsById(id)){
            postRepository.deleteById(id);
            return "redirect:/blog";
        }
        else
            return "redirect:/blog";
    }


    @GetMapping("/blog/{id}/edit")
    public String blogPostEdit(@PathVariable(value = "id") long id, Model model){
        if(postRepository.existsById(id)){
            Optional<Post> post = postRepository.findById(id);
            ArrayList<Post> res = new ArrayStack<>();
            post.ifPresent(res::add);
            model.addAttribute("post", res);
            return "blog-edit";
        }
        else
            return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(
            @PathVariable(value="id") long id,
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String full_text){

        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);

        return "redirect:/blog/" + id;
    }

    @GetMapping("/blog/{id}/copy")
    public String blogPostCopy(@PathVariable(value="id") long id){
        Post post = postRepository.findById(id).orElseThrow();
        Post newPost = postRepository.save(new Post(post.getTitle(), post.getAnons(),post.getFull_text()));
        return "redirect:/blog/" + newPost.getId() +"/edit";
    }

}
