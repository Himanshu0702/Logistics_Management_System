package com.delivery.logistics.common.security;

import com.delivery.logistics.common.enums.Role;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * 🔴 IMPORTANT:
     * Skip JWT filter for public auth endpoints
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        System.out.println("=== shouldNotFilter CALLED ===");
        System.out.println("URI: " + request.getRequestURI());
        System.out.println("METHOD: " + request.getMethod());

        String uri = request.getRequestURI();
        String method = request.getMethod();

        boolean skip =
                (uri.equals("/customers") && method.equals("POST"))
                        || (uri.equals("/auth/login") && method.equals("POST"))
                        || (uri.equals("/agents/login") && method.equals("POST"))
                        || uri.startsWith("/v3/api-docs")
                        || uri.startsWith("/swagger-ui");

        System.out.println("SKIP JWT FILTER = " + skip);

        return skip;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        /**
         * ✅ NO Authorization header → just continue
         * This allows:
         * - Validation errors (400)
         * - Missing fields
         * - Controller exceptions
         */
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            // 🔐 Validate token
            if (!jwtUtil.validateJwtToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // 🧠 Extract identity & role
            UUID userId = jwtUtil.extractIdFromJwtToken(token);
            Role role = jwtUtil.extractRoleFromJwtToken(token);

            // 🎭 Convert role → authority
            SimpleGrantedAuthority authority =
                    new SimpleGrantedAuthority("ROLE_" + role.name());

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            List.of(authority)
                    );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            // ✅ Set security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Continue request
            filterChain.doFilter(request, response);

        } catch (JwtException | IllegalArgumentException ex ) {
            /**
             * ❌ Token present but invalid / malformed / expired
             * → 401 ONLY here
             */
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
