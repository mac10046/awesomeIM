import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICoupons, Coupons } from 'app/shared/model/coupons.model';
import { CouponsService } from './coupons.service';
import { CouponsComponent } from './coupons.component';
import { CouponsDetailComponent } from './coupons-detail.component';
import { CouponsUpdateComponent } from './coupons-update.component';

@Injectable({ providedIn: 'root' })
export class CouponsResolve implements Resolve<ICoupons> {
  constructor(private service: CouponsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICoupons> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((coupons: HttpResponse<Coupons>) => {
          if (coupons.body) {
            return of(coupons.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Coupons());
  }
}

export const couponsRoute: Routes = [
  {
    path: '',
    component: CouponsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Coupons',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CouponsDetailComponent,
    resolve: {
      coupons: CouponsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Coupons',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CouponsUpdateComponent,
    resolve: {
      coupons: CouponsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Coupons',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CouponsUpdateComponent,
    resolve: {
      coupons: CouponsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Coupons',
    },
    canActivate: [UserRouteAccessService],
  },
];
