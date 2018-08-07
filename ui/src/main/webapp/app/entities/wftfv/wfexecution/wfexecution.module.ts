import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UiSharedModule } from 'app/shared';
import {
    WfexecutionComponent,
    WfexecutionDetailComponent,
    WfexecutionUpdateComponent,
    WfexecutionDeletePopupComponent,
    WfexecutionDeleteDialogComponent,
    wfexecutionRoute,
    wfexecutionPopupRoute
} from './';

const ENTITY_STATES = [...wfexecutionRoute, ...wfexecutionPopupRoute];

@NgModule({
    imports: [UiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WfexecutionComponent,
        WfexecutionDetailComponent,
        WfexecutionUpdateComponent,
        WfexecutionDeleteDialogComponent,
        WfexecutionDeletePopupComponent
    ],
    entryComponents: [WfexecutionComponent, WfexecutionUpdateComponent, WfexecutionDeleteDialogComponent, WfexecutionDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UiWfexecutionModule {}
