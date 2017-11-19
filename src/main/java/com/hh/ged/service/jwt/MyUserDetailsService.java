package com.hh.ged.service.jwt;


import com.hh.ged.config.oauth2.JwtUser;
import com.hh.ged.entities.Role;
import com.hh.ged.entities.Utilisateur;
import com.hh.ged.repositories.UtilisateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by rvl on 13/10/2017.
 */
@Service("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Utilisateur user = userRepository.findByLogin(login);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(String.format("No user found with login '%s'.", login));
        } else {

            List<GrantedAuthority> authorities = mapToGrantedAuthorities(user);
            JwtUser jwtUser = new JwtUser(user.getId(), user.getLogin(), user.getPrenom(), user.getNom(), user.getEmail(),
                user.getPassword(), authorities, user.isActif());

            return jwtUser;
        }
    }

    private List<GrantedAuthority> mapToGrantedAuthorities(Utilisateur utilisateur) {
        List<Role> roles = utilisateur.getUtilisateurRoleList().stream().map(ur -> ur.getRole()).collect(Collectors.toList());
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getLibelle()))
            .collect(Collectors.toList());
    }

    public JwtUser getJwtUserFromAccessToken(OAuth2Authentication authentication) {
        JwtUser jwtUser = new JwtUser();

        jwtUser.setUsername((String) authentication.getPrincipal());

        //OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        //Map<String, Object> decodedDetails = (Map<String, Object>) details.getDecodedDetails();

        return jwtUser;
    }

}
