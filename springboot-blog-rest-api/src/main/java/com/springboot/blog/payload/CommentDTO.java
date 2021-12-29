package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Long id ;
    // name should not be null or empty
    @NotEmpty(message = "Name is required")
    @Size(min = 3, message = "add real name")
    private String name ;

    // email should not be null or empty
    // email field validation
    @NotEmpty(message = "Email is required")
    @Email
    private String email ;

    // comment body should not be null or empty
    // comment body must be minimum 10 characters
    @NotEmpty
    @Size(min = 10, message = "comment body must be minimum 10 characters")
    private String body ;


}
