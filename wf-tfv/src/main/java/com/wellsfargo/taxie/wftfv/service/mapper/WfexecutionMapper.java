package com.wellsfargo.taxie.wftfv.service.mapper;

import com.wellsfargo.taxie.wftfv.domain.*;
import com.wellsfargo.taxie.wftfv.service.dto.WfexecutionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Wfexecution and its DTO WfexecutionDTO.
 */
@Mapper(componentModel = "spring", uses = {WfrequestMapper.class})
public interface WfexecutionMapper extends EntityMapper<WfexecutionDTO, Wfexecution> {

    @Mapping(source = "wfrequest.id", target = "wfrequestId")
    @Mapping(source = "wfrequest.wrkfl_execution_id", target = "wfrequestWrkfl_execution_id")
    WfexecutionDTO toDto(Wfexecution wfexecution);

    @Mapping(source = "wfrequestId", target = "wfrequest")
    Wfexecution toEntity(WfexecutionDTO wfexecutionDTO);

    default Wfexecution fromId(Long id) {
        if (id == null) {
            return null;
        }
        Wfexecution wfexecution = new Wfexecution();
        wfexecution.setId(id);
        return wfexecution;
    }
}
