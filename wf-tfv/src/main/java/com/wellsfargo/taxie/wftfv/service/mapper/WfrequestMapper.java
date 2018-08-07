package com.wellsfargo.taxie.wftfv.service.mapper;

import com.wellsfargo.taxie.wftfv.domain.*;
import com.wellsfargo.taxie.wftfv.service.dto.WfrequestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Wfrequest and its DTO WfrequestDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WfrequestMapper extends EntityMapper<WfrequestDTO, Wfrequest> {



    default Wfrequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        Wfrequest wfrequest = new Wfrequest();
        wfrequest.setId(id);
        return wfrequest;
    }
}
