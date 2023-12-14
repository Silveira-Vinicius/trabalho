package com.example.trab.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;

@Controller
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequest {
    private String first_name;
    private String last_name;
    private String nationality;
}
