/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UiTestModule } from '../../../../test.module';
import { TaxformUpdateComponent } from 'app/entities/recipienthelper/taxform/taxform-update.component';
import { TaxformService } from 'app/entities/recipienthelper/taxform/taxform.service';
import { Taxform } from 'app/shared/model/recipienthelper/taxform.model';

describe('Component Tests', () => {
    describe('Taxform Management Update Component', () => {
        let comp: TaxformUpdateComponent;
        let fixture: ComponentFixture<TaxformUpdateComponent>;
        let service: TaxformService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UiTestModule],
                declarations: [TaxformUpdateComponent]
            })
                .overrideTemplate(TaxformUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TaxformUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaxformService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Taxform(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.taxform = entity;
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
                    const entity = new Taxform();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.taxform = entity;
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
