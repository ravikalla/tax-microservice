import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWfrequest } from 'app/shared/model/wftfv/wfrequest.model';

type EntityResponseType = HttpResponse<IWfrequest>;
type EntityArrayResponseType = HttpResponse<IWfrequest[]>;

@Injectable({ providedIn: 'root' })
export class WfrequestService {
    private resourceUrl = SERVER_API_URL + 'wftfv/api/wfrequests';

    constructor(private http: HttpClient) {}

    create(wfrequest: IWfrequest): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(wfrequest);
        return this.http
            .post<IWfrequest>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(wfrequest: IWfrequest): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(wfrequest);
        return this.http
            .put<IWfrequest>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IWfrequest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IWfrequest[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(wfrequest: IWfrequest): IWfrequest {
        const copy: IWfrequest = Object.assign({}, wfrequest, {
            start_dt: wfrequest.start_dt != null && wfrequest.start_dt.isValid() ? wfrequest.start_dt.format(DATE_FORMAT) : null,
            end_dt: wfrequest.end_dt != null && wfrequest.end_dt.isValid() ? wfrequest.end_dt.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.start_dt = res.body.start_dt != null ? moment(res.body.start_dt) : null;
        res.body.end_dt = res.body.end_dt != null ? moment(res.body.end_dt) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((wfrequest: IWfrequest) => {
            wfrequest.start_dt = wfrequest.start_dt != null ? moment(wfrequest.start_dt) : null;
            wfrequest.end_dt = wfrequest.end_dt != null ? moment(wfrequest.end_dt) : null;
        });
        return res;
    }
}
