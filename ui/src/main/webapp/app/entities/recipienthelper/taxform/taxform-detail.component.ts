import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaxform } from 'app/shared/model/recipienthelper/taxform.model';

@Component({
    selector: 'jhi-taxform-detail',
    templateUrl: './taxform-detail.component.html'
})
export class TaxformDetailComponent implements OnInit {
    taxform: ITaxform;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ taxform }) => {
            this.taxform = taxform;
        });
    }

    previousState() {
        window.history.back();
    }
}
