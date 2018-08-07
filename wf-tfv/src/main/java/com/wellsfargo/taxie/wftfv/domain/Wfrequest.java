package com.wellsfargo.taxie.wftfv.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Wfrequest.
 */
@Entity
@Table(name = "wfrequest")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Wfrequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "coe_rcpnt_id", nullable = false)
    private Integer coe_rcpnt_id;

    @NotNull
    @Column(name = "wrkfl_execution_id", nullable = false)
    private Integer wrkfl_execution_id;

    @NotNull
    @Column(name = "current_task_id", nullable = false)
    private Integer current_task_id;

    @NotNull
    @Column(name = "current_task_stat", nullable = false)
    private Integer current_task_stat;

    @NotNull
    @Column(name = "request_stat", nullable = false)
    private Integer request_stat;

    @Column(name = "start_dt")
    private LocalDate start_dt;

    @Column(name = "end_dt")
    private LocalDate end_dt;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCoe_rcpnt_id() {
        return coe_rcpnt_id;
    }

    public Wfrequest coe_rcpnt_id(Integer coe_rcpnt_id) {
        this.coe_rcpnt_id = coe_rcpnt_id;
        return this;
    }

    public void setCoe_rcpnt_id(Integer coe_rcpnt_id) {
        this.coe_rcpnt_id = coe_rcpnt_id;
    }

    public Integer getWrkfl_execution_id() {
        return wrkfl_execution_id;
    }

    public Wfrequest wrkfl_execution_id(Integer wrkfl_execution_id) {
        this.wrkfl_execution_id = wrkfl_execution_id;
        return this;
    }

    public void setWrkfl_execution_id(Integer wrkfl_execution_id) {
        this.wrkfl_execution_id = wrkfl_execution_id;
    }

    public Integer getCurrent_task_id() {
        return current_task_id;
    }

    public Wfrequest current_task_id(Integer current_task_id) {
        this.current_task_id = current_task_id;
        return this;
    }

    public void setCurrent_task_id(Integer current_task_id) {
        this.current_task_id = current_task_id;
    }

    public Integer getCurrent_task_stat() {
        return current_task_stat;
    }

    public Wfrequest current_task_stat(Integer current_task_stat) {
        this.current_task_stat = current_task_stat;
        return this;
    }

    public void setCurrent_task_stat(Integer current_task_stat) {
        this.current_task_stat = current_task_stat;
    }

    public Integer getRequest_stat() {
        return request_stat;
    }

    public Wfrequest request_stat(Integer request_stat) {
        this.request_stat = request_stat;
        return this;
    }

    public void setRequest_stat(Integer request_stat) {
        this.request_stat = request_stat;
    }

    public LocalDate getStart_dt() {
        return start_dt;
    }

    public Wfrequest start_dt(LocalDate start_dt) {
        this.start_dt = start_dt;
        return this;
    }

    public void setStart_dt(LocalDate start_dt) {
        this.start_dt = start_dt;
    }

    public LocalDate getEnd_dt() {
        return end_dt;
    }

    public Wfrequest end_dt(LocalDate end_dt) {
        this.end_dt = end_dt;
        return this;
    }

    public void setEnd_dt(LocalDate end_dt) {
        this.end_dt = end_dt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Wfrequest wfrequest = (Wfrequest) o;
        if (wfrequest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wfrequest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Wfrequest{" +
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
