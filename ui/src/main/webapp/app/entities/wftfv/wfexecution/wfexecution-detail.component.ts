import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWfexecution } from 'app/shared/model/wftfv/wfexecution.model';

@Component({
    selector: 'jhi-wfexecution-detail',
    templateUrl: './wfexecution-detail.component.html'
})
export class WfexecutionDetailComponent implements OnInit {
    wfexecution: IWfexecution;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ wfexecution }) => {
            this.wfexecution = wfexecution;
        });
    }

    previousState() {
        window.history.back();
    }
}
