package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List ;
@RestController
@RequestMapping("/api/v1")
public class CommentController {

   @Autowired
    CommentService commentService ;

   @PostMapping("/posts/{postId}/comments")
   public ResponseEntity<CommentDTO> createComment(@PathVariable(name =  "postId") long postId, @Valid  @RequestBody CommentDTO commentDTO) {

       return new ResponseEntity<>(commentService.createComment(postId,commentDTO), HttpStatus.CREATED) ;

   }

   @GetMapping("/posts/{postId}/comments")
   public  List<CommentDTO> getCommentsByPostId(@PathVariable(name =  "postId") long postId){
       return commentService.getCommentsByPostId(postId) ;
   }

   @GetMapping("/posts/{postId}/comments/{id}")
   public  ResponseEntity<CommentDTO> getCommentById(
           @PathVariable(name = "postId") long postId,
           @PathVariable(name = "id") long commentId
   ){
       CommentDTO commentDTO = commentService.getCommentById(postId,commentId) ;
       return new ResponseEntity<>(commentDTO, HttpStatus.OK) ;
   }

   @PutMapping("/posts/{postId}/comments/{id}")
   public ResponseEntity<CommentDTO> updateComment(
           @PathVariable(name = "postId") long postId ,
           @PathVariable(name = "id") long commentId ,
           @Valid @RequestBody CommentDTO commentDTO
   ){
       CommentDTO updateComment = commentService.updateComment(postId,commentId,commentDTO);
       return new ResponseEntity<>(updateComment,HttpStatus.OK) ;
   }

   @DeleteMapping("/posts/{postId}/comments/{id}")
   public ResponseEntity<String> deleteComment(
           @PathVariable(name = "postId") long postId ,
           @PathVariable(name = "id") long commentId
   ){
       commentService.deleteComment(postId,commentId);
       return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK) ;
   }
}
