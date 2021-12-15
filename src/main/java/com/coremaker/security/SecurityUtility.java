package com.coremaker.security;

import com.coremaker.exception.AuthorizationHeaderMissingException;
import com.coremaker.service.CoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Component
public class SecurityUtility {

    private final CoreService coreService;

    public SecurityUtility(CoreService coreService) {
        this.coreService = coreService;
    }

    public boolean verifyToken() {
        HttpServletRequest request = getRequestFromContext();
        String token = getTokenFromHeader(request);
        return coreService.checkIfTokenExists(token);
    }

    public HttpServletRequest getRequestFromContext() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public String getTokenFromHeader(HttpServletRequest request) {
        String header = getAuthorizationHeader(request);
        return header.split(" ")[1].trim();
    }

    private String getAuthorizationHeader(HttpServletRequest request) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || header.isEmpty() || !header.startsWith("Bearer ")) {
            throw new AuthorizationHeaderMissingException();
        }
        return header;
    }
}
