import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITaxform } from 'app/shared/model/recipienthelper/taxform.model';
import { Principal } from 'app/core';
import { TaxformService } from './taxform.service';

@Component({
    selector: 'jhi-taxform',
    templateUrl: './taxform.component.html'
})
export class TaxformComponent implements OnInit, OnDestroy {
    taxforms: ITaxform[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private taxformService: TaxformService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.taxformService.query().subscribe(
            (res: HttpResponse<ITaxform[]>) => {
                this.taxforms = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTaxforms();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITaxform) {
        return item.id;
    }

    registerChangeInTaxforms() {
        this.eventSubscriber = this.eventManager.subscribe('taxformListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
