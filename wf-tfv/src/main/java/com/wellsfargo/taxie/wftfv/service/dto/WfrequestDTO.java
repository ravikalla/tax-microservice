package com.wellsfargo.taxie.wftfv.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Wfrequest entity.
 */
public class WfrequestDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer coe_rcpnt_id;

    @NotNull
    private Integer wrkfl_execution_id;

    @NotNull
    private Integer current_task_id;

    @NotNull
    private Integer current_task_stat;

    @NotNull
    private Integer request_stat;

    private LocalDate start_dt;

    private LocalDate end_dt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCoe_rcpnt_id() {
        return coe_rcpnt_id;
    }

    public void setCoe_rcpnt_id(Integer coe_rcpnt_id) {
        this.coe_rcpnt_id = coe_rcpnt_id;
    }

    public Integer getWrkfl_execution_id() {
        return wrkfl_execution_id;
    }

    public void setWrkfl_execution_id(Integer wrkfl_execution_id) {
        this.wrkfl_execution_id = wrkfl_execution_id;
    }

    public Integer getCurrent_task_id() {
        return current_task_id;
    }

    public void setCurrent_task_id(Integer current_task_id) {
        this.current_task_id = current_task_id;
    }

    public Integer getCurrent_task_stat() {
        return current_task_stat;
    }

    public void setCurrent_task_stat(Integer current_task_stat) {
        this.current_task_stat = current_task_stat;
    }

    public Integer getRequest_stat() {
        return request_stat;
    }

    public void setRequest_stat(Integer request_stat) {
        this.request_stat = request_stat;
    }

    public LocalDate getStart_dt() {
        return start_dt;
    }

    public void setStart_dt(LocalDate start_dt) {
        this.start_dt = start_dt;
    }

    public LocalDate getEnd_dt() {
        return end_dt;
    }

    public void setEnd_dt(LocalDate end_dt) {
        this.end_dt = end_dt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WfrequestDTO wfrequestDTO = (WfrequestDTO) o;
        if (wfrequestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wfrequestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WfrequestDTO{" +
            "id=" + getId() +
            ", coe_rcpnt_id=" + getCoe_rcpnt_id() +
            ", wrkfl_execution_id=" + getWrkfl_execution_id() +
            ", current_task_id=" + getCurrent_task_id() +
            ", current_task_stat=" + getCurrent_task_stat() +
            ", request_stat=" + getRequest_stat() +
            ", start_dt='" + getStart_dt() + "'" +
            ", end_dt='" + getEnd_dt() + "'" +
            "}";
    }
}
