package com.hh.ged.service.jwt;


import com.hh.ged.entities.Role;
import com.hh.ged.entities.Utilisateur;
import com.hh.ged.entities.UtilisateurRole;
import com.hh.ged.repositories.RoleRepo;
import com.hh.ged.repositories.UtilisateurRepo;
import com.hh.ged.repositories.UtilisateurRoleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by rvl on 13/10/2017.
 */
@Service("utilisateurService")
@Transactional
public class UtilisateurService {

    private static final Logger LOG = LoggerFactory.getLogger(UtilisateurService.class);



    @Autowired
    private UtilisateurRepo utilisateurRepo;

    @Autowired
    private UtilisateurRoleRepo utilisateurRoleRepo;


    /**
     * Créer le superadmin, s'il n'existe pas
     *
     * @param roles
     */
    public void createSpecialUser(List<Role> roles, String email, String login, String passwd, String lastname, String firstname) {

        Utilisateur superAdmin = new Utilisateur();
        superAdmin.setCode("");
        superAdmin.setActif(true);
        superAdmin.setEmail(email);
        superAdmin.setLogin(login);
        superAdmin.setPassword(BCrypt.hashpw(passwd, BCrypt.gensalt()));
        superAdmin.setNom(lastname);
        superAdmin.setPrenom(firstname);

        Utilisateur userDb = utilisateurRepo.findByLogin(superAdmin.getLogin());
        if (Objects.isNull(userDb)) {

            superAdmin = utilisateurRepo.save(superAdmin);

            //on rattache les roles
            for (Role role : roles) {

                    UtilisateurRole ur = new UtilisateurRole(superAdmin, role);
                    utilisateurRoleRepo.save(ur);
                    LOG.info(String.format("role %s ajouté au %s", role.getLibelle(), superAdmin.getLogin()));
            }

            LOG.info(String.format("%s créé avec succès", superAdmin.getLogin()));
        }

    }
}
