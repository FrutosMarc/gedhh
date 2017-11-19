package com.hh.ged.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by rvl on 01/06/2017.
 */
@Entity
@Table(name = "utilisateurs_roles")
public class UtilisateurRole implements Serializable, Comparable<UtilisateurRole> {

    private int id;
    private int idUtilisateur;
    private int idRole;

    private Utilisateur utilisateur;
    private Role role;

    /**
     * Constructeur.
     */
    public UtilisateurRole() {
    }

    /**
     * Constructeur.
     *
     * @param utilisateur l'{@link Utilisateur} concerné.
     * @param role        le {@link Role} de l'{@link Utilisateur} concerné.
     */
    public UtilisateurRole(Utilisateur utilisateur, Role role) {
        this.utilisateur = utilisateur;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "ID_UTILISATEUR", insertable = false, updatable = false)
    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Column(name = "ID_ROLE", insertable = false, updatable = false)
    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    @ManyToOne
    @JoinColumn(name = "ID_UTILISATEUR", nullable = false)
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @ManyToOne
    @JoinColumn(name = "ID_ROLE", nullable = false)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    @Override
    public int compareTo(UtilisateurRole o) {
        return 0;
    }
}
