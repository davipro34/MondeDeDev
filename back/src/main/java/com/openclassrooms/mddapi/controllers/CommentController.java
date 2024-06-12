package com.openclassrooms.mddapi.controllers;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/articles/{articleId}/comments")
public class CommentController {

    public CommentController() {
    }
}