package vigoraes.hubspot_integration.utils;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import vigoraes.hubspot_integration.Dtos.TokensDto;

@Component
public class CacheUtils {

    @CachePut(value = "oauthCache", key = "'oauthCode'")
    public String cacheOAuthCode(String code) {
        return code;
    }

    @Cacheable(value = "oauthCache", key = "'oauthCode'")
    public String getCachedOAuthCode() {
        return null; // Retorna null se não houver código no cache
    }
    
    @CachePut(value = "authCache", key = "'tokens'")
    public TokensDto cacheTokens(TokensDto tokens) {
        return tokens;
    }

    @Cacheable(value = "authCache", key = "'tokens'")
    public TokensDto getCachedTokens() {
        return null; // Se não houver cache, retorna null
    }

    @CacheEvict(value = "authCache", allEntries = true)
    public void clearTokens() {
        System.out.println("Cache de tokens limpo.");
    }
}
