package com.hh.ged.service.jwt;


import com.hh.ged.config.StartupConfig;
import com.hh.ged.config.oauth2.JwtUser;
import com.hh.ged.entities.Role;
import com.hh.ged.entities.Utilisateur;
import com.hh.ged.entities.UtilisateurRole;
import com.hh.ged.repositories.RoleRepo;
import com.hh.ged.repositories.UtilisateurRepo;
import com.hh.ged.repositories.UtilisateurRoleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by rvl on 13/10/2017.
 */
@Service("roleService")
@Transactional
public class RoleService  {

    private static final Logger LOG = LoggerFactory.getLogger(RoleService.class);


    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_UTILISATEUR = "ROLE_UTILISATEUR";
    public static final String ROLE_DEVELOPPEUR = "ROLE_DEVELOPPEUR";


    @Autowired
    private RoleRepo roleRepo;

    public List<Role> createRoles() {

        List<Role> roleList = new ArrayList<>();

        // Création éventuelle du rôle Administrateur.
        Role roleAdmin = new Role(ROLE_ADMIN, "RA", true, null);
        roleAdmin = createRole(roleAdmin);

        // Création éventuelle du rôle Utilisateur.
        Role roleUtilisateur = new Role(ROLE_UTILISATEUR, "RU", true, null);
        roleUtilisateur = createRole(roleUtilisateur);

        // Création éventuelle du rôle Développeur.
        Role roleDeveloppeur = new Role(ROLE_DEVELOPPEUR, "RD", true, null);
        roleDeveloppeur = createRole(roleDeveloppeur);

        roleList.add(roleAdmin);
        roleList.add(roleUtilisateur);
        roleList.add(roleDeveloppeur);


        return roleList;
    }


    public Role createRole(Role role) {
        Role roleDb = roleRepo.findByLibelle(role.getLibelle());
        if (Objects.isNull(roleDb)) {
            role = roleRepo.save(role);
            LOG.info(String.format("%s créé avec succès", role.getLibelle()));
            return role;
        }
        return roleDb;
    }


}
