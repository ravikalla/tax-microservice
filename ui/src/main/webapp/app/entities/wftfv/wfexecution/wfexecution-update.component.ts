import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IWfexecution } from 'app/shared/model/wftfv/wfexecution.model';
import { WfexecutionService } from './wfexecution.service';
import { IWfrequest } from 'app/shared/model/wftfv/wfrequest.model';
import { WfrequestService } from 'app/entities/wftfv/wfrequest';

@Component({
    selector: 'jhi-wfexecution-update',
    templateUrl: './wfexecution-update.component.html'
})
export class WfexecutionUpdateComponent implements OnInit {
    private _wfexecution: IWfexecution;
    isSaving: boolean;

    wfrequests: IWfrequest[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private wfexecutionService: WfexecutionService,
        private wfrequestService: WfrequestService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ wfexecution }) => {
            this.wfexecution = wfexecution;
        });
        this.wfrequestService.query().subscribe(
            (res: HttpResponse<IWfrequest[]>) => {
                this.wfrequests = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.wfexecution.id !== undefined) {
            this.subscribeToSaveResponse(this.wfexecutionService.update(this.wfexecution));
        } else {
            this.subscribeToSaveResponse(this.wfexecutionService.create(this.wfexecution));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IWfexecution>>) {
        result.subscribe((res: HttpResponse<IWfexecution>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackWfrequestById(index: number, item: IWfrequest) {
        return item.id;
    }
    get wfexecution() {
        return this._wfexecution;
    }

    set wfexecution(wfexecution: IWfexecution) {
        this._wfexecution = wfexecution;
    }
}
