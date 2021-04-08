import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDocDetails } from 'app/shared/model/doc-details.model';

type EntityResponseType = HttpResponse<IDocDetails>;
type EntityArrayResponseType = HttpResponse<IDocDetails[]>;

@Injectable({ providedIn: 'root' })
export class DocDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/doc-details';

  constructor(protected http: HttpClient) {}

  create(docDetails: IDocDetails): Observable<EntityResponseType> {
    return this.http.post<IDocDetails>(this.resourceUrl, docDetails, { observe: 'response' });
  }

  update(docDetails: IDocDetails): Observable<EntityResponseType> {
    return this.http.put<IDocDetails>(this.resourceUrl, docDetails, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDocDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDocDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
