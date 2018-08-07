import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWfexecution } from 'app/shared/model/wftfv/wfexecution.model';
import { WfexecutionService } from './wfexecution.service';

@Component({
    selector: 'jhi-wfexecution-delete-dialog',
    templateUrl: './wfexecution-delete-dialog.component.html'
})
export class WfexecutionDeleteDialogComponent {
    wfexecution: IWfexecution;

    constructor(
        private wfexecutionService: WfexecutionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.wfexecutionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'wfexecutionListModification',
                content: 'Deleted an wfexecution'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-wfexecution-delete-popup',
    template: ''
})
export class WfexecutionDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ wfexecution }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(WfexecutionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.wfexecution = wfexecution;
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
