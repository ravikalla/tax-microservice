/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UiTestModule } from '../../../../test.module';
import { WfexecutionDetailComponent } from 'app/entities/wftfv/wfexecution/wfexecution-detail.component';
import { Wfexecution } from 'app/shared/model/wftfv/wfexecution.model';

describe('Component Tests', () => {
    describe('Wfexecution Management Detail Component', () => {
        let comp: WfexecutionDetailComponent;
        let fixture: ComponentFixture<WfexecutionDetailComponent>;
        const route = ({ data: of({ wfexecution: new Wfexecution(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UiTestModule],
                declarations: [WfexecutionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(WfexecutionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WfexecutionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.wfexecution).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
