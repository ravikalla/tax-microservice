/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UiTestModule } from '../../../../test.module';
import { TaxformComponent } from 'app/entities/recipienthelper/taxform/taxform.component';
import { TaxformService } from 'app/entities/recipienthelper/taxform/taxform.service';
import { Taxform } from 'app/shared/model/recipienthelper/taxform.model';

describe('Component Tests', () => {
    describe('Taxform Management Component', () => {
        let comp: TaxformComponent;
        let fixture: ComponentFixture<TaxformComponent>;
        let service: TaxformService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UiTestModule],
                declarations: [TaxformComponent],
                providers: []
            })
                .overrideTemplate(TaxformComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TaxformComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaxformService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Taxform(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.taxforms[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
