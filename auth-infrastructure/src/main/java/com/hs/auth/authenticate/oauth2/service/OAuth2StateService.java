package com.hs.auth.authenticate.oauth2.service;

import com.hs.auth.common.OAuth2Provider;
import org.springframework.stereotype.Service;

@Service
public class OAuth2StateService {
    
    public OAuth2StateInfo parseState(String state) {
        if (state == null || state.trim().isEmpty()) {
            throw new IllegalArgumentException("OAuth2 State 파라미터가 없습니다.");
        }
        
        String[] parts = state.split("_", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("OAuth2 State 형식이 올바르지 않습니다. 형식: {provider}_{serviceName}");
        }
        
        try {
            OAuth2Provider provider = OAuth2Provider.from(parts[0]);
            String serviceName = parts[1];
            
            if (serviceName.trim().isEmpty()) {
                throw new IllegalArgumentException("서비스 이름이 비어있습니다.");
            }
            
            return new OAuth2StateInfo(provider, serviceName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("지원하지 않는 OAuth2 Provider 또는 잘못된 State 형식: " + state, e);
        }
    }
    
    public String generateState(OAuth2Provider provider, String serviceName) {
        if (serviceName == null || serviceName.trim().isEmpty()) {
            throw new IllegalArgumentException("서비스 이름은 필수입니다.");
        }
        return provider.getValue() + "_" + serviceName;
    }
    
    public static class OAuth2StateInfo {
        private final OAuth2Provider provider;
        private final String serviceName;
        
        public OAuth2StateInfo(OAuth2Provider provider, String serviceName) {
            this.provider = provider;
            this.serviceName = serviceName;
        }
        
        public OAuth2Provider getProvider() {
            return provider;
        }
        
        public String getServiceName() {
            return serviceName;
        }
    }
}