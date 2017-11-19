package com.hh.ged.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "utilisateurs")
public class Utilisateur {

    private static final Logger LOG = LoggerFactory.getLogger(Utilisateur.class);

    /**
     * l'<b>id</b> de l'utilisateur.
     */
    private Integer id;

    /**
     * Le prénom de l'utilisateur.
     */
    private String nom = null;

    /**
     * Le nom de l'utilisateur.
     */
    private String prenom = null;

    /**
     * Le login de l'utilisateur.
     */
    private String login = null;

    /**
     * Le mot de passe de l'utilisateur.
     */
    private String password = null;

    /**
     * L'email de l'utilisateur.
     */
    private String email = null;

    /**
     * Le code de l'{@link Utilisateur}.
     */
    private String code = null;

    /**
     * Booléen indiquant si l'utilisateur est actif.
     */
    private boolean actif;

    private List<UtilisateurRole> utilisateurRoleList = new ArrayList<>();

    public Utilisateur() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    @Column(name = "nom", nullable = true)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    @Column(name = "prenom", nullable = true)
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    @Column(name = "login", nullable = false)
    public String getLogin() {
        return login;
    }


    public void setLogin(String login) {
        this.login = login;

    }



    @Column(name = "password", nullable = false)
    @JsonIgnore
    public String getPassword() {
        // LOG.debug("Utilisateur#getPassword = " + password);
        return password;
    }


    public void setPassword(String password) {
        // LOG.debug("Utilisateur#setPassword = " + password);
        this.password = password;
    }


    @Deprecated
    @Transient
    public boolean isEnabled() {
        return false;
    }

    @Deprecated
    public void setEnabled(boolean enabled) {

    }

    @Column(name = "email", nullable = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "actif")
    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }


    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @OneToMany(mappedBy = "utilisateur")
    public List<UtilisateurRole> getUtilisateurRoleList() {
        return utilisateurRoleList;
    }

    public void setUtilisateurRoleList(List<UtilisateurRole> utilisateurRoleList) {
        this.utilisateurRoleList = utilisateurRoleList;
    }


}
