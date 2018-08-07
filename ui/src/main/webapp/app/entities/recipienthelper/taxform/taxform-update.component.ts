import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITaxform } from 'app/shared/model/recipienthelper/taxform.model';
import { TaxformService } from './taxform.service';
import { IRecipient } from 'app/shared/model/recipienthelper/recipient.model';
import { RecipientService } from 'app/entities/recipienthelper/recipient';

@Component({
    selector: 'jhi-taxform-update',
    templateUrl: './taxform-update.component.html'
})
export class TaxformUpdateComponent implements OnInit {
    private _taxform: ITaxform;
    isSaving: boolean;

    recipients: IRecipient[];
    form_effective_dtDp: any;
    affidavit_dtDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private taxformService: TaxformService,
        private recipientService: RecipientService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ taxform }) => {
            this.taxform = taxform;
        });
        this.recipientService.query().subscribe(
            (res: HttpResponse<IRecipient[]>) => {
                this.recipients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.taxform.id !== undefined) {
            this.subscribeToSaveResponse(this.taxformService.update(this.taxform));
        } else {
            this.subscribeToSaveResponse(this.taxformService.create(this.taxform));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITaxform>>) {
        result.subscribe((res: HttpResponse<ITaxform>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRecipientById(index: number, item: IRecipient) {
        return item.id;
    }
    get taxform() {
        return this._taxform;
    }

    set taxform(taxform: ITaxform) {
        this._taxform = taxform;
    }
}
