package com.arquitechthor.kopi.links.dto;

import jakarta.validation.constraints.NotBlank;

public record LinkRequest(
        @NotBlank(message = "URL is required")
        String url,

        @NotBlank(message = "Title is required")
        String title,

        String description,

        @NotBlank(message = "Category is required")
        String category,

        String tags
) {}
