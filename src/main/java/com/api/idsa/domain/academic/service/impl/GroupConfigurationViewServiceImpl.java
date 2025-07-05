package com.api.idsa.domain.academic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.response.GroupConfigurationViewResponse;
import com.api.idsa.domain.academic.mapper.IGroupConfigurationViewMapper;
import com.api.idsa.domain.academic.model.GroupConfigurationEntity;
import com.api.idsa.domain.academic.repository.IGroupConfigurationRepository;
import com.api.idsa.domain.academic.service.IGroupConfigurationViewService;
import com.api.idsa.domain.personnel.repository.IUserRepository;
import org.springframework.util.StringUtils;

@Service
public class GroupConfigurationViewServiceImpl implements IGroupConfigurationViewService {

    @Autowired
    IGroupConfigurationRepository groupConfigurationRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IGroupConfigurationViewMapper groupConfigurationViewMapper;

    @Override
    public Page<GroupConfigurationViewResponse> getAllGroupConfigurationView(Pageable pageable, String search) {
        Page<GroupConfigurationEntity> groupConfigurationPage;
        if (StringUtils.hasText(search)) {
            String searchTerm = search.trim();
            groupConfigurationPage = isAdmin() ?
                    groupConfigurationRepository.findGroupConfigurationsBySearchTerm(searchTerm, pageable) :
                    groupConfigurationRepository.findGroupConfigurationsByTutorEmailAndSearchTerm(getCurrentEmail(), searchTerm, pageable);
        } else {
            groupConfigurationPage = isAdmin() ?
                    groupConfigurationRepository.findAll(pageable) :
                    groupConfigurationRepository.findByTutorPersonUserEmail(getCurrentEmail(), pageable);
        }
        return groupConfigurationPage.map(groupConfigurationViewMapper::toResponse);
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    }

    private String getCurrentEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        if (!userRepository.existsByEmail(email)) {
            throw new ResourceNotFoundException("User", "email", email);
        }
        return email;
    }
}
