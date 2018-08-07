import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRecipient } from 'app/shared/model/recipienthelper/recipient.model';
import { Principal } from 'app/core';
import { RecipientService } from './recipient.service';

@Component({
    selector: 'jhi-recipient',
    templateUrl: './recipient.component.html'
})
export class RecipientComponent implements OnInit, OnDestroy {
    recipients: IRecipient[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private recipientService: RecipientService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.recipientService.query().subscribe(
            (res: HttpResponse<IRecipient[]>) => {
                this.recipients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRecipients();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRecipient) {
        return item.id;
    }

    registerChangeInRecipients() {
        this.eventSubscriber = this.eventManager.subscribe('recipientListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
