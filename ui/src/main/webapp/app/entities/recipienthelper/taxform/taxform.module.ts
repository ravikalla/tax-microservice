import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UiSharedModule } from 'app/shared';
import {
    TaxformComponent,
    TaxformDetailComponent,
    TaxformUpdateComponent,
    TaxformDeletePopupComponent,
    TaxformDeleteDialogComponent,
    taxformRoute,
    taxformPopupRoute
} from './';

const ENTITY_STATES = [...taxformRoute, ...taxformPopupRoute];

@NgModule({
    imports: [UiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TaxformComponent,
        TaxformDetailComponent,
        TaxformUpdateComponent,
        TaxformDeleteDialogComponent,
        TaxformDeletePopupComponent
    ],
    entryComponents: [TaxformComponent, TaxformUpdateComponent, TaxformDeleteDialogComponent, TaxformDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UiTaxformModule {}
