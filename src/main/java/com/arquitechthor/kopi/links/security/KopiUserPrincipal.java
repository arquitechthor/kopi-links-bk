package com.arquitechthor.kopi.links.security;

import java.util.UUID;

public record KopiUserPrincipal(UUID userId, String email) {}
