import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITaxform } from 'app/shared/model/recipienthelper/taxform.model';

type EntityResponseType = HttpResponse<ITaxform>;
type EntityArrayResponseType = HttpResponse<ITaxform[]>;

@Injectable({ providedIn: 'root' })
export class TaxformService {
    private resourceUrl = SERVER_API_URL + 'recipienthelper/api/taxforms';

    constructor(private http: HttpClient) {}

    create(taxform: ITaxform): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(taxform);
        return this.http
            .post<ITaxform>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(taxform: ITaxform): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(taxform);
        return this.http
            .put<ITaxform>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITaxform>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITaxform[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(taxform: ITaxform): ITaxform {
        const copy: ITaxform = Object.assign({}, taxform, {
            form_effective_dt:
                taxform.form_effective_dt != null && taxform.form_effective_dt.isValid()
                    ? taxform.form_effective_dt.format(DATE_FORMAT)
                    : null,
            affidavit_dt: taxform.affidavit_dt != null && taxform.affidavit_dt.isValid() ? taxform.affidavit_dt.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.form_effective_dt = res.body.form_effective_dt != null ? moment(res.body.form_effective_dt) : null;
        res.body.affidavit_dt = res.body.affidavit_dt != null ? moment(res.body.affidavit_dt) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((taxform: ITaxform) => {
            taxform.form_effective_dt = taxform.form_effective_dt != null ? moment(taxform.form_effective_dt) : null;
            taxform.affidavit_dt = taxform.affidavit_dt != null ? moment(taxform.affidavit_dt) : null;
        });
        return res;
    }
}
