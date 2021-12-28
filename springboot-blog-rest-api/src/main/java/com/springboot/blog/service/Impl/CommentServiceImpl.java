package com.springboot.blog.service.Impl;

import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Comment;
import com.springboot.blog.model.Post;
import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository ;

    @Autowired
    PostRepository postRepository ;

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {

        Comment comment = mapToModel(commentDTO) ;

        // Retrieve post mode by id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", postId)
        );
        // set post to comment model
        comment.setPost(post);

        // save comment to DB
       Comment newComment =  commentRepository.save(comment) ;


        return mapToDTO(newComment);
    }

    private CommentDTO mapToDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO() ;
        commentDTO.setId(comment.getId());
        commentDTO.setName(comment.getName());
        commentDTO.setEmail(comment.getEmail());
        commentDTO.setBody(comment.getBody());

        return commentDTO ;
    }
    private Comment mapToModel(CommentDTO commentDTO){
        Comment comment = new Comment() ;
        comment.setId(commentDTO.getId());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());

        return comment ;
    }
}
