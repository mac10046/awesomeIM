import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITaxes } from 'app/shared/model/taxes.model';

type EntityResponseType = HttpResponse<ITaxes>;
type EntityArrayResponseType = HttpResponse<ITaxes[]>;

@Injectable({ providedIn: 'root' })
export class TaxesService {
  public resourceUrl = SERVER_API_URL + 'api/taxes';

  constructor(protected http: HttpClient) {}

  create(taxes: ITaxes): Observable<EntityResponseType> {
    return this.http.post<ITaxes>(this.resourceUrl, taxes, { observe: 'response' });
  }

  update(taxes: ITaxes): Observable<EntityResponseType> {
    return this.http.put<ITaxes>(this.resourceUrl, taxes, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITaxes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITaxes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
