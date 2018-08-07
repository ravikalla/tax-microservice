package com.wellsfargo.taxie.recipienthelper.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Taxform.
 */
@Entity
@Table(name = "taxform")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Taxform implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "coe_rcpnt_id")
    private Integer coe_rcpnt_id;

    @Column(name = "sor_rcpnt_id")
    private String sor_rcpnt_id;

    @Column(name = "lob_id")
    private Integer lob_id;

    @Column(name = "sor_id")
    private Integer sor_id;

    @Column(name = "sub_lob_id")
    private Integer sub_lob_id;

    @Column(name = "form_type")
    private Integer form_type;

    @Column(name = "form_effective_dt")
    private LocalDate form_effective_dt;

    @Column(name = "affidavit_dt")
    private LocalDate affidavit_dt;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Recipient recipient;

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

    public Taxform coe_rcpnt_id(Integer coe_rcpnt_id) {
        this.coe_rcpnt_id = coe_rcpnt_id;
        return this;
    }

    public void setCoe_rcpnt_id(Integer coe_rcpnt_id) {
        this.coe_rcpnt_id = coe_rcpnt_id;
    }

    public String getSor_rcpnt_id() {
        return sor_rcpnt_id;
    }

    public Taxform sor_rcpnt_id(String sor_rcpnt_id) {
        this.sor_rcpnt_id = sor_rcpnt_id;
        return this;
    }

    public void setSor_rcpnt_id(String sor_rcpnt_id) {
        this.sor_rcpnt_id = sor_rcpnt_id;
    }

    public Integer getLob_id() {
        return lob_id;
    }

    public Taxform lob_id(Integer lob_id) {
        this.lob_id = lob_id;
        return this;
    }

    public void setLob_id(Integer lob_id) {
        this.lob_id = lob_id;
    }

    public Integer getSor_id() {
        return sor_id;
    }

    public Taxform sor_id(Integer sor_id) {
        this.sor_id = sor_id;
        return this;
    }

    public void setSor_id(Integer sor_id) {
        this.sor_id = sor_id;
    }

    public Integer getSub_lob_id() {
        return sub_lob_id;
    }

    public Taxform sub_lob_id(Integer sub_lob_id) {
        this.sub_lob_id = sub_lob_id;
        return this;
    }

    public void setSub_lob_id(Integer sub_lob_id) {
        this.sub_lob_id = sub_lob_id;
    }

    public Integer getForm_type() {
        return form_type;
    }

    public Taxform form_type(Integer form_type) {
        this.form_type = form_type;
        return this;
    }

    public void setForm_type(Integer form_type) {
        this.form_type = form_type;
    }

    public LocalDate getForm_effective_dt() {
        return form_effective_dt;
    }

    public Taxform form_effective_dt(LocalDate form_effective_dt) {
        this.form_effective_dt = form_effective_dt;
        return this;
    }

    public void setForm_effective_dt(LocalDate form_effective_dt) {
        this.form_effective_dt = form_effective_dt;
    }

    public LocalDate getAffidavit_dt() {
        return affidavit_dt;
    }

    public Taxform affidavit_dt(LocalDate affidavit_dt) {
        this.affidavit_dt = affidavit_dt;
        return this;
    }

    public void setAffidavit_dt(LocalDate affidavit_dt) {
        this.affidavit_dt = affidavit_dt;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public Taxform recipient(Recipient recipient) {
        this.recipient = recipient;
        return this;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
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
        Taxform taxform = (Taxform) o;
        if (taxform.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taxform.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Taxform{" +
            "id=" + getId() +
            ", coe_rcpnt_id=" + getCoe_rcpnt_id() +
            ", sor_rcpnt_id='" + getSor_rcpnt_id() + "'" +
            ", lob_id=" + getLob_id() +
            ", sor_id=" + getSor_id() +
            ", sub_lob_id=" + getSub_lob_id() +
            ", form_type=" + getForm_type() +
            ", form_effective_dt='" + getForm_effective_dt() + "'" +
            ", affidavit_dt='" + getAffidavit_dt() + "'" +
            "}";
    }
}
