package com.wellsfargo.taxie.recipienthelper.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Recipient.
 */
@Entity
@Table(name = "recipient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Recipient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "rec_src")
    private String rec_src;

    @NotNull
    @Column(name = "lob_id", nullable = false)
    private Integer lob_id;

    @NotNull
    @Column(name = "sor_id", nullable = false)
    private Integer sor_id;

    @NotNull
    @Column(name = "sub_lob_id", nullable = false)
    private Integer sub_lob_id;

    @Column(name = "sor_rcpnt_id")
    private String sor_rcpnt_id;

    @NotNull
    @Column(name = "rcpnt_type", nullable = false)
    private Integer rcpnt_type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRec_src() {
        return rec_src;
    }

    public Recipient rec_src(String rec_src) {
        this.rec_src = rec_src;
        return this;
    }

    public void setRec_src(String rec_src) {
        this.rec_src = rec_src;
    }

    public Integer getLob_id() {
        return lob_id;
    }

    public Recipient lob_id(Integer lob_id) {
        this.lob_id = lob_id;
        return this;
    }

    public void setLob_id(Integer lob_id) {
        this.lob_id = lob_id;
    }

    public Integer getSor_id() {
        return sor_id;
    }

    public Recipient sor_id(Integer sor_id) {
        this.sor_id = sor_id;
        return this;
    }

    public void setSor_id(Integer sor_id) {
        this.sor_id = sor_id;
    }

    public Integer getSub_lob_id() {
        return sub_lob_id;
    }

    public Recipient sub_lob_id(Integer sub_lob_id) {
        this.sub_lob_id = sub_lob_id;
        return this;
    }

    public void setSub_lob_id(Integer sub_lob_id) {
        this.sub_lob_id = sub_lob_id;
    }

    public String getSor_rcpnt_id() {
        return sor_rcpnt_id;
    }

    public Recipient sor_rcpnt_id(String sor_rcpnt_id) {
        this.sor_rcpnt_id = sor_rcpnt_id;
        return this;
    }

    public void setSor_rcpnt_id(String sor_rcpnt_id) {
        this.sor_rcpnt_id = sor_rcpnt_id;
    }

    public Integer getRcpnt_type() {
        return rcpnt_type;
    }

    public Recipient rcpnt_type(Integer rcpnt_type) {
        this.rcpnt_type = rcpnt_type;
        return this;
    }

    public void setRcpnt_type(Integer rcpnt_type) {
        this.rcpnt_type = rcpnt_type;
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
        Recipient recipient = (Recipient) o;
        if (recipient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Recipient{" +
            "id=" + getId() +
            ", rec_src='" + getRec_src() + "'" +
            ", lob_id=" + getLob_id() +
            ", sor_id=" + getSor_id() +
            ", sub_lob_id=" + getSub_lob_id() +
            ", sor_rcpnt_id='" + getSor_rcpnt_id() + "'" +
            ", rcpnt_type=" + getRcpnt_type() +
            "}";
    }
}
