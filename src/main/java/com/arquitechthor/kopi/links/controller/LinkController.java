package com.arquitechthor.kopi.links.controller;

import com.arquitechthor.kopi.links.dto.LinkRequest;
import com.arquitechthor.kopi.links.dto.LinkResponse;
import com.arquitechthor.kopi.links.security.KopiUserPrincipal;
import com.arquitechthor.kopi.links.service.LinkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/links")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @GetMapping
    public ResponseEntity<List<LinkResponse>> getAll(@AuthenticationPrincipal KopiUserPrincipal principal) {
        return ResponseEntity.ok(linkService.getAllLinks(principal.userId()));
    }

    @GetMapping("/grouped")
    public ResponseEntity<Map<String, List<LinkResponse>>> getGrouped(@AuthenticationPrincipal KopiUserPrincipal principal) {
        return ResponseEntity.ok(linkService.getLinksGroupedByCategory(principal.userId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LinkResponse> getById(@PathVariable Long id,
                                                @AuthenticationPrincipal KopiUserPrincipal principal) {
        return ResponseEntity.ok(linkService.getLinkById(id, principal.userId()));
    }

    @PostMapping
    public ResponseEntity<LinkResponse> create(@Valid @RequestBody LinkRequest request,
                                               @AuthenticationPrincipal KopiUserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(linkService.createLink(request, principal.userId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LinkResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody LinkRequest request,
                                               @AuthenticationPrincipal KopiUserPrincipal principal) {
        return ResponseEntity.ok(linkService.updateLink(id, request, principal.userId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal KopiUserPrincipal principal) {
        linkService.deleteLink(id, principal.userId());
        return ResponseEntity.noContent().build();
    }
}
