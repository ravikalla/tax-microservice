export interface IRecipient {
    id?: number;
    rec_src?: string;
    lob_id?: number;
    sor_id?: number;
    sub_lob_id?: number;
    sor_rcpnt_id?: string;
    rcpnt_type?: number;
}

export class Recipient implements IRecipient {
    constructor(
        public id?: number,
        public rec_src?: string,
        public lob_id?: number,
        public sor_id?: number,
        public sub_lob_id?: number,
        public sor_rcpnt_id?: string,
        public rcpnt_type?: number
    ) {}
}
