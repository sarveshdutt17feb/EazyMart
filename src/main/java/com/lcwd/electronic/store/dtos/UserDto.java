package com.lcwd.electronic.store.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;
    @Size(min=3,max=20,message = "invalid name !!")
    private String name;

    @Email(message = "invalid user email !!")
    @NotBlank(message = "email is required !!")
    private String email;
    @NotBlank(message = "password is required !!")
    private String password;
    @Size(min=4,max=6,message = "invalid gender !!")
    private String gender;
    @NotBlank(message = "write something about yourself !!")
    private String about;
    //@Pattern
    //Custome validator


    private String imageName;

}
