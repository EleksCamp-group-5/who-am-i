package com.eleks.academy.whoami.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50, message = "nickname length must be between {min} and {max}!")
    @NotBlank(message = "Please fill all the fields")
    private String nickname;

    //    @Size(min = 8, max = 32, message = "email length must be between {min} and {max}!")
    @NotNull
    @NotEmpty
    @NotBlank(message = "Please fill all the fields")
    @Pattern(regexp = "^\\w+[\\w-\\.]@\\w+((-\\w+)|(\\w))\\.[a-z]{2,3}$")
    @Pattern(regexp = "^[\\S]{8,32}$")
    private String email;

    //    @Size(min = 8, max = 50, message = "password length must be between {min} and {max}!")
    @NotNull
    @NotEmpty
    @NotBlank(message = "Please fill all the fields")
    @Pattern(regexp = "^[\\S]{8,50}$")
    private String password;

}
