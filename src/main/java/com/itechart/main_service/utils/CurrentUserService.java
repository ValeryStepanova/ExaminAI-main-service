package com.itechart.main_service.utils;

import com.itechart.profileserviceapi.dto.UserDto;
import com.itechart.profileserviceapi.enums.Role;
import com.sun.security.auth.UserPrincipal;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@UtilityClass
public class CurrentUserService {

    public static UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            List<String> roles = jwt.getClaimAsStringList("roles");
            Set<Role> currentRoles = roles.stream()
                    .filter(role -> role != null && role.startsWith("ROLE_"))
                    .map(Role::valueOf)
                    .collect(Collectors.toSet());
            return UserDto.builder()
                    .username(jwt.getClaim("preferred_username"))
                    .email(jwt.getClaim("email"))
                    .roles(currentRoles)
                    .build();
        }
        return null;
    }
}