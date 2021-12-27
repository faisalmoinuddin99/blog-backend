package com.springboot.blog.service.Impl;

import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Post;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public PostResponse getAllPosts(int pageNo, int pageSize) {

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo,pageSize) ;

        Page<Post> posts = postRepository.findAll(pageable) ;

        // get content for page object
        List<Post> listOfPosts = posts.getContent() ;

        List<PostDTO> content = listOfPosts.stream()
                .map(post -> mappedToDTO(post))
                .collect(Collectors.toList()) ;
        PostResponse postResponse = new PostResponse() ;
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse ;
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
