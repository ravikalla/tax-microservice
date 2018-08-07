import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UiSharedModule } from 'app/shared';
import {
    WfrequestComponent,
    WfrequestDetailComponent,
    WfrequestUpdateComponent,
    WfrequestDeletePopupComponent,
    WfrequestDeleteDialogComponent,
    wfrequestRoute,
    wfrequestPopupRoute
} from './';

const ENTITY_STATES = [...wfrequestRoute, ...wfrequestPopupRoute];

@NgModule({
    imports: [UiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WfrequestComponent,
        WfrequestDetailComponent,
        WfrequestUpdateComponent,
        WfrequestDeleteDialogComponent,
        WfrequestDeletePopupComponent
    ],
    entryComponents: [WfrequestComponent, WfrequestUpdateComponent, WfrequestDeleteDialogComponent, WfrequestDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UiWfrequestModule {}
