/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UiTestModule } from '../../../../test.module';
import { WfexecutionUpdateComponent } from 'app/entities/wftfv/wfexecution/wfexecution-update.component';
import { WfexecutionService } from 'app/entities/wftfv/wfexecution/wfexecution.service';
import { Wfexecution } from 'app/shared/model/wftfv/wfexecution.model';

describe('Component Tests', () => {
    describe('Wfexecution Management Update Component', () => {
        let comp: WfexecutionUpdateComponent;
        let fixture: ComponentFixture<WfexecutionUpdateComponent>;
        let service: WfexecutionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UiTestModule],
                declarations: [WfexecutionUpdateComponent]
            })
                .overrideTemplate(WfexecutionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WfexecutionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WfexecutionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Wfexecution(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.wfexecution = entity;
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
                    const entity = new Wfexecution();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.wfexecution = entity;
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
