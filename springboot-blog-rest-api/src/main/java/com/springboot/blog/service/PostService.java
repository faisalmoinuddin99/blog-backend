package com.springboot.blog.service;

import com.springboot.blog.payload.PostDTO;

public interface PostService {
    PostDTO createPost(PostDTO postDTO) ;
}
