package com.wellsfargo.taxie.wftfv.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the Wfrequest entity. This class is used in WfrequestResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /wfrequests?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WfrequestCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter coe_rcpnt_id;

    private IntegerFilter wrkfl_execution_id;

    private IntegerFilter current_task_id;

    private IntegerFilter current_task_stat;

    private IntegerFilter request_stat;

    private LocalDateFilter start_dt;

    private LocalDateFilter end_dt;

    public WfrequestCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getCoe_rcpnt_id() {
        return coe_rcpnt_id;
    }

    public void setCoe_rcpnt_id(IntegerFilter coe_rcpnt_id) {
        this.coe_rcpnt_id = coe_rcpnt_id;
    }

    public IntegerFilter getWrkfl_execution_id() {
        return wrkfl_execution_id;
    }

    public void setWrkfl_execution_id(IntegerFilter wrkfl_execution_id) {
        this.wrkfl_execution_id = wrkfl_execution_id;
    }

    public IntegerFilter getCurrent_task_id() {
        return current_task_id;
    }

    public void setCurrent_task_id(IntegerFilter current_task_id) {
        this.current_task_id = current_task_id;
    }

    public IntegerFilter getCurrent_task_stat() {
        return current_task_stat;
    }

    public void setCurrent_task_stat(IntegerFilter current_task_stat) {
        this.current_task_stat = current_task_stat;
    }

    public IntegerFilter getRequest_stat() {
        return request_stat;
    }

    public void setRequest_stat(IntegerFilter request_stat) {
        this.request_stat = request_stat;
    }

    public LocalDateFilter getStart_dt() {
        return start_dt;
    }

    public void setStart_dt(LocalDateFilter start_dt) {
        this.start_dt = start_dt;
    }

    public LocalDateFilter getEnd_dt() {
        return end_dt;
    }

    public void setEnd_dt(LocalDateFilter end_dt) {
        this.end_dt = end_dt;
    }

    @Override
    public String toString() {
        return "WfrequestCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (coe_rcpnt_id != null ? "coe_rcpnt_id=" + coe_rcpnt_id + ", " : "") +
                (wrkfl_execution_id != null ? "wrkfl_execution_id=" + wrkfl_execution_id + ", " : "") +
                (current_task_id != null ? "current_task_id=" + current_task_id + ", " : "") +
                (current_task_stat != null ? "current_task_stat=" + current_task_stat + ", " : "") +
                (request_stat != null ? "request_stat=" + request_stat + ", " : "") +
                (start_dt != null ? "start_dt=" + start_dt + ", " : "") +
                (end_dt != null ? "end_dt=" + end_dt + ", " : "") +
            "}";
    }

}
