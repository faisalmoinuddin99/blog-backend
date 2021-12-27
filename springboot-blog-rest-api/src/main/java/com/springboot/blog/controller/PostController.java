package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List ;
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService ; // Injecting Interface so that it become loosely couple

    /*
    NOTE: If we inject Class instead of Interface it become tightly coupled
     */

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED) ;
    }

    // get all rest api
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
       return postService.getAllPosts(pageNo,pageSize) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id")long id){
        return ResponseEntity.ok(postService.getPostById(id)) ;
    }

    // update post by id rest api
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePostById(@RequestBody PostDTO postDTO, @PathVariable(name = "id") long id){
        return new ResponseEntity<>(postService.updatePost(postDTO,id), HttpStatus.OK) ;
    }

    // delete post rest api
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        postService.deletePostById(id);

        return new ResponseEntity<>("Post Entity deleted successfully", HttpStatus.OK) ;
    }

}
