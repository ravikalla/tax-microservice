import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IRecipient } from 'app/shared/model/recipienthelper/recipient.model';
import { RecipientService } from './recipient.service';

@Component({
    selector: 'jhi-recipient-update',
    templateUrl: './recipient-update.component.html'
})
export class RecipientUpdateComponent implements OnInit {
    private _recipient: IRecipient;
    isSaving: boolean;

    constructor(private recipientService: RecipientService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recipient }) => {
            this.recipient = recipient;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.recipient.id !== undefined) {
            this.subscribeToSaveResponse(this.recipientService.update(this.recipient));
        } else {
            this.subscribeToSaveResponse(this.recipientService.create(this.recipient));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRecipient>>) {
        result.subscribe((res: HttpResponse<IRecipient>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get recipient() {
        return this._recipient;
    }

    set recipient(recipient: IRecipient) {
        this._recipient = recipient;
    }
}
