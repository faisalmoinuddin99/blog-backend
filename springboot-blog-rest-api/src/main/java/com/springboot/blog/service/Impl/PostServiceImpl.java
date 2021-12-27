package com.springboot.blog.service.Impl;

import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Post;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        Post post = mappedToModel(postDTO) ;
        Post newPost = postRepository.save(post) ;
        // convert model to DTO
        return mappedToDTO(newPost);
    }

    @Override
    public List<PostDTO> getAllPosts() {

        List<Post> posts = postRepository.findAll() ;
        return posts.stream()
                .map(post -> mappedToDTO(post))
                .collect(Collectors.toList()) ;
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Post", "id", id)
                ) ;
        return mappedToDTO(post) ;
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {
        // GET post by id from the database
        Post post = postRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Post", "id", id)
                ) ;

       post.setTitle(postDTO.getTitle());
       post.setDescription(postDTO.getDescription());
       post.setContent(postDTO.getContent());

       Post updatePost = postRepository.save(post) ;
       return mappedToDTO(updatePost) ;

    }

    @Override
    public void deletePostById(long id) {
        // fetching the post by its id
        Post post = postRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Post", "id", id)
                ) ;
        postRepository.delete(post);
    }

    // convert Model into DTO
    private  PostDTO mappedToDTO(Post post){
        PostDTO postDTO = new PostDTO() ;
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());

        return postDTO ;
    }
    // convert DTO to Model
    private Post mappedToModel(PostDTO postDTO) {
        Post post = new Post() ;
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        return post ;
    }
}
