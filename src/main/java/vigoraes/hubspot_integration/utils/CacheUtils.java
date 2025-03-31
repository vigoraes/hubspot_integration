package vigoraes.hubspot_integration.utils;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

public class CacheUtils {

    @CachePut(value = "oauthCache", key = "'oauthCode'")
    public static String cacheOAuthCode(String code) {
        return code;
    }

    @Cacheable(value = "oauthCache", key = "'oauthCode'")
    public static String getCachedOAuthCode() {
        return null; // Retorna null se não houver código no cache
    }
    
    @CachePut(value = "authCache", key = "'accessToken'")
    public static String cacheAccessToken(String accessToken) {
        return accessToken;
    }

    @Cacheable(value = "authCache", key = "'accessToken'")
    public static String getCachedAccessToken() {
        return null; // Se não houver cache, retorna null
    }

    @CachePut(value = "authCache", key = "'refreshToken'")
    public static String cacheRefreshToken(String refreshToken) {
        return refreshToken;
    }

    @Cacheable(value = "authCache", key = "'refreshToken'")
    public static String getCachedRefreshToken() {
        return null;
    }

    @CacheEvict(value = "authCache", allEntries = true)
    public void clearTokens() {
        System.out.println("Cache de tokens limpo.");
    }
}
