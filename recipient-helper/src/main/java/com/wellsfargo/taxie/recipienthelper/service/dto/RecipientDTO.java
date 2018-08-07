package com.wellsfargo.taxie.recipienthelper.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Recipient entity.
 */
public class RecipientDTO implements Serializable {

    private Long id;

    private String rec_src;

    @NotNull
    private Integer lob_id;

    @NotNull
    private Integer sor_id;

    @NotNull
    private Integer sub_lob_id;

    private String sor_rcpnt_id;

    @NotNull
    private Integer rcpnt_type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRec_src() {
        return rec_src;
    }

    public void setRec_src(String rec_src) {
        this.rec_src = rec_src;
    }

    public Integer getLob_id() {
        return lob_id;
    }

    public void setLob_id(Integer lob_id) {
        this.lob_id = lob_id;
    }

    public Integer getSor_id() {
        return sor_id;
    }

    public void setSor_id(Integer sor_id) {
        this.sor_id = sor_id;
    }

    public Integer getSub_lob_id() {
        return sub_lob_id;
    }

    public void setSub_lob_id(Integer sub_lob_id) {
        this.sub_lob_id = sub_lob_id;
    }

    public String getSor_rcpnt_id() {
        return sor_rcpnt_id;
    }

    public void setSor_rcpnt_id(String sor_rcpnt_id) {
        this.sor_rcpnt_id = sor_rcpnt_id;
    }

    public Integer getRcpnt_type() {
        return rcpnt_type;
    }

    public void setRcpnt_type(Integer rcpnt_type) {
        this.rcpnt_type = rcpnt_type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecipientDTO recipientDTO = (RecipientDTO) o;
        if (recipientDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipientDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecipientDTO{" +
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
