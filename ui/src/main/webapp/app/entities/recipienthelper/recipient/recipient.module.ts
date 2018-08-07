import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UiSharedModule } from 'app/shared';
import {
    RecipientComponent,
    RecipientDetailComponent,
    RecipientUpdateComponent,
    RecipientDeletePopupComponent,
    RecipientDeleteDialogComponent,
    recipientRoute,
    recipientPopupRoute
} from './';

const ENTITY_STATES = [...recipientRoute, ...recipientPopupRoute];

@NgModule({
    imports: [UiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RecipientComponent,
        RecipientDetailComponent,
        RecipientUpdateComponent,
        RecipientDeleteDialogComponent,
        RecipientDeletePopupComponent
    ],
    entryComponents: [RecipientComponent, RecipientUpdateComponent, RecipientDeleteDialogComponent, RecipientDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UiRecipientModule {}
