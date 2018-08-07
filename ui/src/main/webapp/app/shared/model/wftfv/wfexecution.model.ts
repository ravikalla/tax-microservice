export interface IWfexecution {
    id?: number;
    bpm_instance_id?: number;
    bpm_wrkfl_task_id?: number;
    key_value?: number;
    sla?: number;
    wfrequestWrkfl_execution_id?: string;
    wfrequestId?: number;
}

export class Wfexecution implements IWfexecution {
    constructor(
        public id?: number,
        public bpm_instance_id?: number,
        public bpm_wrkfl_task_id?: number,
        public key_value?: number,
        public sla?: number,
        public wfrequestWrkfl_execution_id?: string,
        public wfrequestId?: number
    ) {}
}
