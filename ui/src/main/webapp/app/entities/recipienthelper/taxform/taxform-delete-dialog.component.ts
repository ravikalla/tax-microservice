import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaxform } from 'app/shared/model/recipienthelper/taxform.model';
import { TaxformService } from './taxform.service';

@Component({
    selector: 'jhi-taxform-delete-dialog',
    templateUrl: './taxform-delete-dialog.component.html'
})
export class TaxformDeleteDialogComponent {
    taxform: ITaxform;

    constructor(private taxformService: TaxformService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.taxformService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'taxformListModification',
                content: 'Deleted an taxform'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-taxform-delete-popup',
    template: ''
})
export class TaxformDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ taxform }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TaxformDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.taxform = taxform;
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
