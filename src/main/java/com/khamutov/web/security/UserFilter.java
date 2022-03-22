package com.khamutov.web.security;

import com.khamutov.entities.UserRole;

public class UserFilter extends TokenFilter{
    @Override
    boolean isAccessGranted(UserRole userRole) {
        return userRole.getAuthority() == 1;
    }
}
