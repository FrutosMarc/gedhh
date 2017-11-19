package com.hh.ged.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by rvl on 01/06/2017.
 */
@Entity
@Table(name = "communes")
public class Commune implements Serializable, Comparable<Commune> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private int id;

    @Column(name = "NOM")
    private String nom;

    @Column(name = "CODE_DEPARTEMENT")
    private String codeDepartement;

    @Column(name = "CODE_POSTAL")
    private String codePostal;

    public Integer getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodeDepartement() {
        return codeDepartement;
    }

    public void setCodeDepartement(String codeDepartement) {
        this.codeDepartement = codeDepartement;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Commune commune = (Commune) o;

        if (id != commune.id) return false;
        if (codePostal != commune.codePostal) return false;
        if (!nom.equals(commune.nom)) return false;
        return codeDepartement.equals(commune.codeDepartement);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + nom.hashCode();
        result = 31 * result + codeDepartement.hashCode();
        return result;
    }

    @Override
    public int compareTo(Commune o) {
        return 0;
    }
}
