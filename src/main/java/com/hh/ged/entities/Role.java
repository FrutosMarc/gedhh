package com.hh.ged.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rvl on 01/06/2017.
 */
@Entity
@Table(name = "roles")
public class Role implements Serializable, Comparable<Role> {

    private int id;
    private String libelle;
    private String code;
    private boolean actif;
    private List<UtilisateurRole> utilisateurRoleList = new ArrayList<>();

    public Role(String libelle, String code, boolean actif, List<UtilisateurRole> utilisateurRoleList) {
        this.libelle = libelle;
        this.code = code;
        this.actif = actif;
        this.utilisateurRoleList = utilisateurRoleList;
    }

    public Role(){

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


    @Column(name = "LIBELLE")
    public String getLibelle() {
        return libelle;
    }


    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }



    @Column(name = "ACTIF")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }



    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @OneToMany(mappedBy = "role")
    @Cascade(CascadeType.ALL)
    public List<UtilisateurRole> getUtilisateurRoleList() {
        return utilisateurRoleList;
    }

    public void setUtilisateurRoleList(List<UtilisateurRole> utilisateurRoleList) {
        this.utilisateurRoleList = utilisateurRoleList;
    }



    @Override
    public int compareTo(Role o) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (id != role.id) return false;
        if (actif != role.actif) return false;
        if (!libelle.equals(role.libelle)) return false;
        return code.equals(role.code);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + libelle.hashCode();
        result = 31 * result + code.hashCode();
        result = 31 * result + (actif ? 1 : 0);
        return result;
    }
}
