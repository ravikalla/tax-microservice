import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWfexecution } from 'app/shared/model/wftfv/wfexecution.model';

type EntityResponseType = HttpResponse<IWfexecution>;
type EntityArrayResponseType = HttpResponse<IWfexecution[]>;

@Injectable({ providedIn: 'root' })
export class WfexecutionService {
    private resourceUrl = SERVER_API_URL + 'wftfv/api/wfexecutions';

    constructor(private http: HttpClient) {}

    create(wfexecution: IWfexecution): Observable<EntityResponseType> {
        return this.http.post<IWfexecution>(this.resourceUrl, wfexecution, { observe: 'response' });
    }

    update(wfexecution: IWfexecution): Observable<EntityResponseType> {
        return this.http.put<IWfexecution>(this.resourceUrl, wfexecution, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IWfexecution>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IWfexecution[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
