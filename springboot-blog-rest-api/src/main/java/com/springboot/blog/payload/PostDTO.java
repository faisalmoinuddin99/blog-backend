package com.springboot.blog.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDTO {

    private Long id ;

    // title should not be null or empty
    // title should have at least 2 character
    @NotEmpty
    @Size(min = 2, message = "title should have at least 2 character")
    private String title ;

    // post description should be null or empty
    // post description should have at least 10 character
    @NotEmpty
    @Size(min=10, message = "description should have at least 10 character")
    private String description ;

    // post content should not be null or empty
    @NotEmpty
    private String content ;
    private Set<CommentDTO> comments ;

}
