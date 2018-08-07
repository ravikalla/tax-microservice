/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UiTestModule } from '../../../../test.module';
import { WfrequestUpdateComponent } from 'app/entities/wftfv/wfrequest/wfrequest-update.component';
import { WfrequestService } from 'app/entities/wftfv/wfrequest/wfrequest.service';
import { Wfrequest } from 'app/shared/model/wftfv/wfrequest.model';

describe('Component Tests', () => {
    describe('Wfrequest Management Update Component', () => {
        let comp: WfrequestUpdateComponent;
        let fixture: ComponentFixture<WfrequestUpdateComponent>;
        let service: WfrequestService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UiTestModule],
                declarations: [WfrequestUpdateComponent]
            })
                .overrideTemplate(WfrequestUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WfrequestUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WfrequestService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Wfrequest(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.wfrequest = entity;
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
                    const entity = new Wfrequest();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.wfrequest = entity;
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
