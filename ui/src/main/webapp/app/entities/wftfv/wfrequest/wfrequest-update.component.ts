import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IWfrequest } from 'app/shared/model/wftfv/wfrequest.model';
import { WfrequestService } from './wfrequest.service';

@Component({
    selector: 'jhi-wfrequest-update',
    templateUrl: './wfrequest-update.component.html'
})
export class WfrequestUpdateComponent implements OnInit {
    private _wfrequest: IWfrequest;
    isSaving: boolean;
    start_dtDp: any;
    end_dtDp: any;

    constructor(private wfrequestService: WfrequestService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ wfrequest }) => {
            this.wfrequest = wfrequest;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.wfrequest.id !== undefined) {
            this.subscribeToSaveResponse(this.wfrequestService.update(this.wfrequest));
        } else {
            this.subscribeToSaveResponse(this.wfrequestService.create(this.wfrequest));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IWfrequest>>) {
        result.subscribe((res: HttpResponse<IWfrequest>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get wfrequest() {
        return this._wfrequest;
    }

    set wfrequest(wfrequest: IWfrequest) {
        this._wfrequest = wfrequest;
    }
}
