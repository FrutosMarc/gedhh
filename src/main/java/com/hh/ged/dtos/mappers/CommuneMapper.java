package com.hh.ged.dtos.mappers;

import com.hh.ged.dtos.CommuneDTO;
import com.hh.ged.entities.Commune;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by rvl on 10/07/2017.
 */
@Mapper(componentModel="spring",uses={})
public interface CommuneMapper {

    CommuneMapper INSTANCE = Mappers.getMapper( CommuneMapper.class );

    CommuneDTO communeToCommuneDTO(Commune commune);

    List<CommuneDTO> communesToCommuneDTOs(List<Commune> communes);

    Commune communeDTOToCommune(CommuneDTO communeDTO);

    List<Commune> communeDTOsToCommunes(List<CommuneDTO> communeDTOs);

    default Commune communeFromId(Integer id) {
        if (id == null) {
            return null;
        }
        Commune commune = new Commune();
        commune.setId(id);
        return commune;
    }
}
