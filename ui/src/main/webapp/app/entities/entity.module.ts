import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { UiRecipientModule as RecipienthelperRecipientModule } from './recipienthelper/recipient/recipient.module';
import { UiTaxformModule as RecipienthelperTaxformModule } from './recipienthelper/taxform/taxform.module';
import { UiWfrequestModule as WftfvWfrequestModule } from './wftfv/wfrequest/wfrequest.module';
import { UiWfexecutionModule as WftfvWfexecutionModule } from './wftfv/wfexecution/wfexecution.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        RecipienthelperRecipientModule,
        RecipienthelperTaxformModule,
        WftfvWfrequestModule,
        WftfvWfexecutionModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UiEntityModule {}
