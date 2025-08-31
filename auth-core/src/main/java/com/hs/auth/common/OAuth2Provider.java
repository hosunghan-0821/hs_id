package com.hs.auth.common;

public enum OAuth2Provider {
    KAKAO("kakao"),
    GOOGLE("google"), 
    NAVER("naver");
    
    private final String value;
    
    OAuth2Provider(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static OAuth2Provider from(String value) {
        for (OAuth2Provider provider : OAuth2Provider.values()) {
            if (provider.value.equalsIgnoreCase(value)) {
                return provider;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 OAuth2 Provider: " + value);
    }
}