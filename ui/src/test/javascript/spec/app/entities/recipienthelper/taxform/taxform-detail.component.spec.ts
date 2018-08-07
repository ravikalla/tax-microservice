/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UiTestModule } from '../../../../test.module';
import { TaxformDetailComponent } from 'app/entities/recipienthelper/taxform/taxform-detail.component';
import { Taxform } from 'app/shared/model/recipienthelper/taxform.model';

describe('Component Tests', () => {
    describe('Taxform Management Detail Component', () => {
        let comp: TaxformDetailComponent;
        let fixture: ComponentFixture<TaxformDetailComponent>;
        const route = ({ data: of({ taxform: new Taxform(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UiTestModule],
                declarations: [TaxformDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TaxformDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TaxformDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.taxform).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
