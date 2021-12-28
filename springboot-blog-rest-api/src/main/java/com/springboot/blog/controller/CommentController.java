package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

   @Autowired
    CommentService commentService ;

   @PostMapping("/posts/{postId}/comments")
   public ResponseEntity<CommentDTO> createComment(@PathVariable(name =  "postId") long postId, @RequestBody CommentDTO commentDTO) {

       return new ResponseEntity<>(commentService.createComment(postId,commentDTO), HttpStatus.CREATED) ;

   }
}
