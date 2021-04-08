import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITaxes, Taxes } from 'app/shared/model/taxes.model';
import { TaxesService } from './taxes.service';
import { TaxesComponent } from './taxes.component';
import { TaxesDetailComponent } from './taxes-detail.component';
import { TaxesUpdateComponent } from './taxes-update.component';

@Injectable({ providedIn: 'root' })
export class TaxesResolve implements Resolve<ITaxes> {
  constructor(private service: TaxesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaxes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((taxes: HttpResponse<Taxes>) => {
          if (taxes.body) {
            return of(taxes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Taxes());
  }
}

export const taxesRoute: Routes = [
  {
    path: '',
    component: TaxesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Taxes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaxesDetailComponent,
    resolve: {
      taxes: TaxesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Taxes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaxesUpdateComponent,
    resolve: {
      taxes: TaxesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Taxes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaxesUpdateComponent,
    resolve: {
      taxes: TaxesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Taxes',
    },
    canActivate: [UserRouteAccessService],
  },
];
