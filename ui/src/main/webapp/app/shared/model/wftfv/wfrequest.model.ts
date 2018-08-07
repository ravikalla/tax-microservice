import { Moment } from 'moment';

export interface IWfrequest {
    id?: number;
    coe_rcpnt_id?: number;
    wrkfl_execution_id?: number;
    current_task_id?: number;
    current_task_stat?: number;
    request_stat?: number;
    start_dt?: Moment;
    end_dt?: Moment;
}

export class Wfrequest implements IWfrequest {
    constructor(
        public id?: number,
        public coe_rcpnt_id?: number,
        public wrkfl_execution_id?: number,
        public current_task_id?: number,
        public current_task_stat?: number,
        public request_stat?: number,
        public start_dt?: Moment,
        public end_dt?: Moment
    ) {}
}
