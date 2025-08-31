package com.hs.auth.authenticate.oauth2.factory;

import com.hs.auth.authenticate.oauth2.client.KakaoOAuth2Client;
import com.hs.auth.authenticate.oauth2.common.OAuth2Client;
import com.hs.auth.common.OAuth2Provider;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2ClientFactory {
    
    private final Map<OAuth2Provider, OAuth2Client> clients = new HashMap<>();
    
    public OAuth2ClientFactory(KakaoOAuth2Client kakaoOAuth2Client) {
        clients.put(OAuth2Provider.KAKAO, kakaoOAuth2Client);
    }
    
    public OAuth2Client getClient(OAuth2Provider provider) {
        OAuth2Client client = clients.get(provider);
        if (client == null) {
            throw new IllegalArgumentException("지원하지 않는 OAuth2 Provider: " + provider);
        }
        return client;
    }
    
    public OAuth2Client getClient(String providerName) {
        OAuth2Provider provider = OAuth2Provider.from(providerName);
        return getClient(provider);
    }
}