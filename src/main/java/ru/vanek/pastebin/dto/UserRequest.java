package ru.vanek.pastebin.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
    @NotEmpty
    @Size(min = 3,max = 15)
    private String username;
//    (              #Start of group
//    (?=.*\d)       #must contains one digit from 0-9
//    (?=.*[a-z])    #must contains one lowercase characters
//    (?=.*[\W])     #must contains at least one special character
//    .              #match anything with previous condition checking
//    {8,20}         #ength at least 8 characters and maximum of 20
//            )      #End of group
    @Pattern(regexp = "^.*(?=.{8,})(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!#$%&?_ \"]).*$",message = "Пароль должен быть от 8 символов, содержать буквы нижнего или вехнего регистра, а также один из специальных символов: !#$%&?_ ")
    private String password;
    @NotEmpty
    private String confirmPassword;
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String email;
}
