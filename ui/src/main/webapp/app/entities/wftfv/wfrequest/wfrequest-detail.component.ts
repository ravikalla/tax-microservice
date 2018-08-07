import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWfrequest } from 'app/shared/model/wftfv/wfrequest.model';

@Component({
    selector: 'jhi-wfrequest-detail',
    templateUrl: './wfrequest-detail.component.html'
})
export class WfrequestDetailComponent implements OnInit {
    wfrequest: IWfrequest;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ wfrequest }) => {
            this.wfrequest = wfrequest;
        });
    }

    previousState() {
        window.history.back();
    }
}
