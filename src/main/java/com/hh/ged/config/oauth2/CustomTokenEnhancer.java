package com.hh.ged.config.oauth2;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by rvl on 07/11/2017.
 */
@Service
public class CustomTokenEnhancer extends JwtAccessTokenConverter implements TokenEnhancer {


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        JwtUser user = (JwtUser) authentication.getPrincipal();
        final Map<String, Object> additionalInfos = new LinkedHashMap<>();


        additionalInfos.put("prenom",user.getFirstname());
        additionalInfos.put("nom",user.getLastname());

        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(additionalInfos);

        //renvoyer le token customisé
        return customAccessToken;
    }
}
