package com.arquitechthor.kopi.links.service;

import com.arquitechthor.kopi.links.dto.LinkRequest;
import com.arquitechthor.kopi.links.dto.LinkResponse;
import com.arquitechthor.kopi.links.entity.Link;
import com.arquitechthor.kopi.links.repository.LinkRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    public List<LinkResponse> getAllLinks(UUID userId) {
        return linkRepository.findAllByUserIdOrderByCategoryAsc(userId)
                .stream()
                .map(LinkResponse::from)
                .toList();
    }

    public Map<String, List<LinkResponse>> getLinksGroupedByCategory(UUID userId) {
        return linkRepository.findAllByUserIdOrderByCategoryAsc(userId)
                .stream()
                .map(LinkResponse::from)
                .collect(Collectors.groupingBy(LinkResponse::category));
    }

    public LinkResponse getLinkById(Long id, UUID userId) {
        return linkRepository.findByIdAndUserId(id, userId)
                .map(LinkResponse::from)
                .orElseThrow(() -> new EntityNotFoundException("Link not found"));
    }

    @Transactional
    public LinkResponse createLink(LinkRequest request, UUID userId) {
        Link link = Link.builder()
                .userId(userId)
                .url(request.url())
                .title(request.title())
                .description(request.description())
                .category(request.category())
                .tags(request.tags())
                .build();
        return LinkResponse.from(linkRepository.save(link));
    }

    @Transactional
    public LinkResponse updateLink(Long id, LinkRequest request, UUID userId) {
        Link link = linkRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Link not found"));

        link.setUrl(request.url());
        link.setTitle(request.title());
        link.setDescription(request.description());
        link.setCategory(request.category());
        link.setTags(request.tags());

        return LinkResponse.from(linkRepository.save(link));
    }

    @Transactional
    public void deleteLink(Long id, UUID userId) {
        if (!linkRepository.findByIdAndUserId(id, userId).isPresent()) {
            throw new EntityNotFoundException("Link not found");
        }
        linkRepository.deleteByIdAndUserId(id, userId);
    }
}
