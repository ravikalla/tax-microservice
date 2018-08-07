package com.wellsfargo.taxie.wftfv.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Wfexecution entity.
 */
public class WfexecutionDTO implements Serializable {

    private Long id;

    private Integer bpm_instance_id;

    private Integer bpm_wrkfl_task_id;

    private Integer key_value;

    private Integer sla;

    private Long wfrequestId;

    private String wfrequestWrkfl_execution_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBpm_instance_id() {
        return bpm_instance_id;
    }

    public void setBpm_instance_id(Integer bpm_instance_id) {
        this.bpm_instance_id = bpm_instance_id;
    }

    public Integer getBpm_wrkfl_task_id() {
        return bpm_wrkfl_task_id;
    }

    public void setBpm_wrkfl_task_id(Integer bpm_wrkfl_task_id) {
        this.bpm_wrkfl_task_id = bpm_wrkfl_task_id;
    }

    public Integer getKey_value() {
        return key_value;
    }

    public void setKey_value(Integer key_value) {
        this.key_value = key_value;
    }

    public Integer getSla() {
        return sla;
    }

    public void setSla(Integer sla) {
        this.sla = sla;
    }

    public Long getWfrequestId() {
        return wfrequestId;
    }

    public void setWfrequestId(Long wfrequestId) {
        this.wfrequestId = wfrequestId;
    }

    public String getWfrequestWrkfl_execution_id() {
        return wfrequestWrkfl_execution_id;
    }

    public void setWfrequestWrkfl_execution_id(String wfrequestWrkfl_execution_id) {
        this.wfrequestWrkfl_execution_id = wfrequestWrkfl_execution_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WfexecutionDTO wfexecutionDTO = (WfexecutionDTO) o;
        if (wfexecutionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wfexecutionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WfexecutionDTO{" +
            "id=" + getId() +
            ", bpm_instance_id=" + getBpm_instance_id() +
            ", bpm_wrkfl_task_id=" + getBpm_wrkfl_task_id() +
            ", key_value=" + getKey_value() +
            ", sla=" + getSla() +
            ", wfrequest=" + getWfrequestId() +
            ", wfrequest='" + getWfrequestWrkfl_execution_id() + "'" +
            "}";
    }
}
