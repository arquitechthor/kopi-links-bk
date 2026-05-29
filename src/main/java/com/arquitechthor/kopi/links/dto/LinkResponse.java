package com.arquitechthor.kopi.links.dto;

import com.arquitechthor.kopi.links.entity.Link;

public record LinkResponse(
        Long id,
        String url,
        String title,
        String description,
        String category,
        String tags
) {
    public static LinkResponse from(Link link) {
        return new LinkResponse(
                link.getId(),
                link.getUrl(),
                link.getTitle(),
                link.getDescription(),
                link.getCategory(),
                link.getTags()
        );
    }
}
