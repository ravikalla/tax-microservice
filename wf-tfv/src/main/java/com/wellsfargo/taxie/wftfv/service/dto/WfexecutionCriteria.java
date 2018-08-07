package com.wellsfargo.taxie.wftfv.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Wfexecution entity. This class is used in WfexecutionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /wfexecutions?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WfexecutionCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter bpm_instance_id;

    private IntegerFilter bpm_wrkfl_task_id;

    private IntegerFilter key_value;

    private IntegerFilter sla;

    private LongFilter wfrequestId;

    public WfexecutionCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getBpm_instance_id() {
        return bpm_instance_id;
    }

    public void setBpm_instance_id(IntegerFilter bpm_instance_id) {
        this.bpm_instance_id = bpm_instance_id;
    }

    public IntegerFilter getBpm_wrkfl_task_id() {
        return bpm_wrkfl_task_id;
    }

    public void setBpm_wrkfl_task_id(IntegerFilter bpm_wrkfl_task_id) {
        this.bpm_wrkfl_task_id = bpm_wrkfl_task_id;
    }

    public IntegerFilter getKey_value() {
        return key_value;
    }

    public void setKey_value(IntegerFilter key_value) {
        this.key_value = key_value;
    }

    public IntegerFilter getSla() {
        return sla;
    }

    public void setSla(IntegerFilter sla) {
        this.sla = sla;
    }

    public LongFilter getWfrequestId() {
        return wfrequestId;
    }

    public void setWfrequestId(LongFilter wfrequestId) {
        this.wfrequestId = wfrequestId;
    }

    @Override
    public String toString() {
        return "WfexecutionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (bpm_instance_id != null ? "bpm_instance_id=" + bpm_instance_id + ", " : "") +
                (bpm_wrkfl_task_id != null ? "bpm_wrkfl_task_id=" + bpm_wrkfl_task_id + ", " : "") +
                (key_value != null ? "key_value=" + key_value + ", " : "") +
                (sla != null ? "sla=" + sla + ", " : "") +
                (wfrequestId != null ? "wfrequestId=" + wfrequestId + ", " : "") +
            "}";
    }

}
