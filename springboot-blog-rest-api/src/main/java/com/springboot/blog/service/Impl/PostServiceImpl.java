package com.springboot.blog.service.Impl;

import com.springboot.blog.model.Post;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    /*
    NOTE: Here I am using Constructor based Dependency Injection
     */
    private  PostRepository postRepository ;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public PostDTO createPost(PostDTO postDTO) {

        // convert DTO to Model
        Post post = new Post() ;
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post newPost = postRepository.save(post) ;

        // convert model to DTO
        PostDTO postResponse  = new PostDTO() ;
        postResponse.setId(newPost.getId());
        postResponse.setTitle(newPost.getTitle());
        postResponse.setDescription(newPost.getDescription());
        postResponse.setContent(newPost.getContent());

        return postResponse;
    }
}
