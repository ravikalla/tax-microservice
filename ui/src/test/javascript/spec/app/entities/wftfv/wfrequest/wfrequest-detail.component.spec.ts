/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UiTestModule } from '../../../../test.module';
import { WfrequestDetailComponent } from 'app/entities/wftfv/wfrequest/wfrequest-detail.component';
import { Wfrequest } from 'app/shared/model/wftfv/wfrequest.model';

describe('Component Tests', () => {
    describe('Wfrequest Management Detail Component', () => {
        let comp: WfrequestDetailComponent;
        let fixture: ComponentFixture<WfrequestDetailComponent>;
        const route = ({ data: of({ wfrequest: new Wfrequest(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UiTestModule],
                declarations: [WfrequestDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(WfrequestDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WfrequestDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.wfrequest).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
