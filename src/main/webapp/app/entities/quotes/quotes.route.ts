import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQuotes, Quotes } from 'app/shared/model/quotes.model';
import { QuotesService } from './quotes.service';
import { QuotesComponent } from './quotes.component';
import { QuotesDetailComponent } from './quotes-detail.component';
import { QuotesUpdateComponent } from './quotes-update.component';

@Injectable({ providedIn: 'root' })
export class QuotesResolve implements Resolve<IQuotes> {
  constructor(private service: QuotesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuotes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((quotes: HttpResponse<Quotes>) => {
          if (quotes.body) {
            return of(quotes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Quotes());
  }
}

export const quotesRoute: Routes = [
  {
    path: '',
    component: QuotesComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Quotes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QuotesDetailComponent,
    resolve: {
      quotes: QuotesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Quotes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QuotesUpdateComponent,
    resolve: {
      quotes: QuotesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Quotes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QuotesUpdateComponent,
    resolve: {
      quotes: QuotesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Quotes',
    },
    canActivate: [UserRouteAccessService],
  },
];
