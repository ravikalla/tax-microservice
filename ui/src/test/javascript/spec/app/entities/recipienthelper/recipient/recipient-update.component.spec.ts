/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UiTestModule } from '../../../../test.module';
import { RecipientUpdateComponent } from 'app/entities/recipienthelper/recipient/recipient-update.component';
import { RecipientService } from 'app/entities/recipienthelper/recipient/recipient.service';
import { Recipient } from 'app/shared/model/recipienthelper/recipient.model';

describe('Component Tests', () => {
    describe('Recipient Management Update Component', () => {
        let comp: RecipientUpdateComponent;
        let fixture: ComponentFixture<RecipientUpdateComponent>;
        let service: RecipientService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UiTestModule],
                declarations: [RecipientUpdateComponent]
            })
                .overrideTemplate(RecipientUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RecipientUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecipientService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Recipient(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.recipient = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Recipient();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.recipient = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
