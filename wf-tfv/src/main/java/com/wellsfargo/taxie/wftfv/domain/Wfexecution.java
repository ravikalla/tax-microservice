package com.wellsfargo.taxie.wftfv.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Wfexecution.
 */
@Entity
@Table(name = "wfexecution")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Wfexecution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "bpm_instance_id")
    private Integer bpm_instance_id;

    @Column(name = "bpm_wrkfl_task_id")
    private Integer bpm_wrkfl_task_id;

    @Column(name = "key_value")
    private Integer key_value;

    @Column(name = "sla")
    private Integer sla;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Wfrequest wfrequest;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBpm_instance_id() {
        return bpm_instance_id;
    }

    public Wfexecution bpm_instance_id(Integer bpm_instance_id) {
        this.bpm_instance_id = bpm_instance_id;
        return this;
    }

    public void setBpm_instance_id(Integer bpm_instance_id) {
        this.bpm_instance_id = bpm_instance_id;
    }

    public Integer getBpm_wrkfl_task_id() {
        return bpm_wrkfl_task_id;
    }

    public Wfexecution bpm_wrkfl_task_id(Integer bpm_wrkfl_task_id) {
        this.bpm_wrkfl_task_id = bpm_wrkfl_task_id;
        return this;
    }

    public void setBpm_wrkfl_task_id(Integer bpm_wrkfl_task_id) {
        this.bpm_wrkfl_task_id = bpm_wrkfl_task_id;
    }

    public Integer getKey_value() {
        return key_value;
    }

    public Wfexecution key_value(Integer key_value) {
        this.key_value = key_value;
        return this;
    }

    public void setKey_value(Integer key_value) {
        this.key_value = key_value;
    }

    public Integer getSla() {
        return sla;
    }

    public Wfexecution sla(Integer sla) {
        this.sla = sla;
        return this;
    }

    public void setSla(Integer sla) {
        this.sla = sla;
    }

    public Wfrequest getWfrequest() {
        return wfrequest;
    }

    public Wfexecution wfrequest(Wfrequest wfrequest) {
        this.wfrequest = wfrequest;
        return this;
    }

    public void setWfrequest(Wfrequest wfrequest) {
        this.wfrequest = wfrequest;
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
        Wfexecution wfexecution = (Wfexecution) o;
        if (wfexecution.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wfexecution.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Wfexecution{" +
            "id=" + getId() +
            ", bpm_instance_id=" + getBpm_instance_id() +
            ", bpm_wrkfl_task_id=" + getBpm_wrkfl_task_id() +
            ", key_value=" + getKey_value() +
            ", sla=" + getSla() +
            "}";
    }
}
