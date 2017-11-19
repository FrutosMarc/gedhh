package com.hh.ged.service;


import com.hh.ged.dtos.CommuneDTO;
import com.hh.ged.dtos.Response;
import com.hh.ged.dtos.mappers.CommuneMapper;
import com.hh.ged.entities.Commune;
import com.hh.ged.entities.Role;
import com.hh.ged.entities.Utilisateur;
import com.hh.ged.entities.UtilisateurRole;
import com.hh.ged.exceptions.GedHhRestException;
import com.hh.ged.repositories.CommuneRepo;
import com.hh.ged.repositories.UtilisateurRepo;
import com.hh.ged.repositories.UtilisateurRoleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created by rvl on 13/10/2017.
 */
@Service("communeService")
@Transactional(rollbackFor = GedHhRestException.class)
public class CommuneService {


    @Autowired
    private CommuneRepo communeRepo;


    public Response<Commune> getAll() throws GedHhRestException {

        try {
            List<Commune> communeList = (List<Commune>) communeRepo.findAll();
            List<CommuneDTO> communeDTOList = CommuneMapper.INSTANCE.communesToCommuneDTOs(communeList);
            return new Response(communeDTOList,  communeDTOList.size());
        } catch (Exception e) {
            throw new GedHhRestException(new Response<>(true, e.getMessage()));
        }
    }


}
