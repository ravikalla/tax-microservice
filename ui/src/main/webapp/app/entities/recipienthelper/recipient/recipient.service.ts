import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecipient } from 'app/shared/model/recipienthelper/recipient.model';

type EntityResponseType = HttpResponse<IRecipient>;
type EntityArrayResponseType = HttpResponse<IRecipient[]>;

@Injectable({ providedIn: 'root' })
export class RecipientService {
    private resourceUrl = SERVER_API_URL + 'recipienthelper/api/recipients';

    constructor(private http: HttpClient) {}

    create(recipient: IRecipient): Observable<EntityResponseType> {
        return this.http.post<IRecipient>(this.resourceUrl, recipient, { observe: 'response' });
    }

    update(recipient: IRecipient): Observable<EntityResponseType> {
        return this.http.put<IRecipient>(this.resourceUrl, recipient, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRecipient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRecipient[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
