package com.springboot.blog.payload;

import com.springboot.blog.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Long id ;
    private String name ;
    private String email ;
    private String body ;


}
