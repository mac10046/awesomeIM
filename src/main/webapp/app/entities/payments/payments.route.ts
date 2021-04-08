import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPayments, Payments } from 'app/shared/model/payments.model';
import { PaymentsService } from './payments.service';
import { PaymentsComponent } from './payments.component';
import { PaymentsDetailComponent } from './payments-detail.component';
import { PaymentsUpdateComponent } from './payments-update.component';

@Injectable({ providedIn: 'root' })
export class PaymentsResolve implements Resolve<IPayments> {
  constructor(private service: PaymentsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPayments> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((payments: HttpResponse<Payments>) => {
          if (payments.body) {
            return of(payments.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Payments());
  }
}

export const paymentsRoute: Routes = [
  {
    path: '',
    component: PaymentsComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Payments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentsDetailComponent,
    resolve: {
      payments: PaymentsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Payments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentsUpdateComponent,
    resolve: {
      payments: PaymentsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Payments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentsUpdateComponent,
    resolve: {
      payments: PaymentsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Payments',
    },
    canActivate: [UserRouteAccessService],
  },
];
