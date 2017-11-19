package com.hh.ged.mvc;


import com.hh.ged.dtos.Response;
import com.hh.ged.entities.Commune;
import com.hh.ged.exceptions.GedHhRestException;
import com.hh.ged.service.CommuneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rvl on 03/07/2017.
 * Controleur pour la gestion produit.
 */

@RestController
@RequestMapping("/communes")
@Api(value = "communes", description = "COMMUNES")
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_UTILISATEUR')")
public class CommunesController {

    private static final Logger LOG = LoggerFactory.getLogger(CommunesController.class);

    @Autowired
    CommuneService communeService;

    @ApiOperation(value = "all", notes = "récupérer toutes les communes")
    @GetMapping(value = "/all")
    public Response<Commune> all() throws GedHhRestException {
        return communeService.getAll();
    }


}
