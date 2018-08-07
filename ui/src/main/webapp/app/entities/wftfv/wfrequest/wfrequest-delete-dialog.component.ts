import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWfrequest } from 'app/shared/model/wftfv/wfrequest.model';
import { WfrequestService } from './wfrequest.service';

@Component({
    selector: 'jhi-wfrequest-delete-dialog',
    templateUrl: './wfrequest-delete-dialog.component.html'
})
export class WfrequestDeleteDialogComponent {
    wfrequest: IWfrequest;

    constructor(private wfrequestService: WfrequestService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.wfrequestService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'wfrequestListModification',
                content: 'Deleted an wfrequest'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-wfrequest-delete-popup',
    template: ''
})
export class WfrequestDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ wfrequest }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(WfrequestDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.wfrequest = wfrequest;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
