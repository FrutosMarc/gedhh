package com.hh.ged.config;

import com.hh.ged.entities.Role;
import com.hh.ged.entities.Utilisateur;
import com.hh.ged.entities.UtilisateurRole;
import com.hh.ged.repositories.RoleRepo;
import com.hh.ged.repositories.UtilisateurRepo;
import com.hh.ged.repositories.UtilisateurRoleRepo;
import com.hh.ged.service.jwt.RoleService;
import com.hh.ged.service.jwt.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by rvl on 26/07/2017.
 */
@Configuration
public class StartupConfig {

    private static final Logger LOG = LoggerFactory.getLogger(StartupConfig.class);


    @Autowired
    @Qualifier("roleService")
    RoleService roleService;

    @Autowired
    @Qualifier("utilisateurService")
    UtilisateurService utilisateurService;


    @PostConstruct
    public void init() {
        //créer les roles par défaut s'ils n'existent pas
        List<Role> roles = roleService.createRoles();

        //créer l'admin par défaut s'il n'existe pas
        utilisateurService.createSpecialUser(roles, "admin@gedhh.com", "superadmin", "gedhhadmin", "Herault Habitat", "Admin");
    }


}
