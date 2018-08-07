import { Moment } from 'moment';
import { IRecipient } from 'app/shared/model/recipienthelper/recipient.model';

export interface ITaxform {
    id?: number;
    coe_rcpnt_id?: number;
    sor_rcpnt_id?: string;
    lob_id?: number;
    sor_id?: number;
    sub_lob_id?: number;
    form_type?: number;
    form_effective_dt?: Moment;
    affidavit_dt?: Moment;
    recipient?: IRecipient;
}

export class Taxform implements ITaxform {
    constructor(
        public id?: number,
        public coe_rcpnt_id?: number,
        public sor_rcpnt_id?: string,
        public lob_id?: number,
        public sor_id?: number,
        public sub_lob_id?: number,
        public form_type?: number,
        public form_effective_dt?: Moment,
        public affidavit_dt?: Moment,
        public recipient?: IRecipient
    ) {}
}
