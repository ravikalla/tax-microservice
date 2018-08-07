/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UiTestModule } from '../../../../test.module';
import { TaxformDeleteDialogComponent } from 'app/entities/recipienthelper/taxform/taxform-delete-dialog.component';
import { TaxformService } from 'app/entities/recipienthelper/taxform/taxform.service';

describe('Component Tests', () => {
    describe('Taxform Management Delete Component', () => {
        let comp: TaxformDeleteDialogComponent;
        let fixture: ComponentFixture<TaxformDeleteDialogComponent>;
        let service: TaxformService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UiTestModule],
                declarations: [TaxformDeleteDialogComponent]
            })
                .overrideTemplate(TaxformDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TaxformDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaxformService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
