import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrderDetails, OrderDetails } from 'app/shared/model/order-details.model';
import { OrderDetailsService } from './order-details.service';
import { OrderDetailsComponent } from './order-details.component';
import { OrderDetailsDetailComponent } from './order-details-detail.component';
import { OrderDetailsUpdateComponent } from './order-details-update.component';

@Injectable({ providedIn: 'root' })
export class OrderDetailsResolve implements Resolve<IOrderDetails> {
  constructor(private service: OrderDetailsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrderDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((orderDetails: HttpResponse<OrderDetails>) => {
          if (orderDetails.body) {
            return of(orderDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrderDetails());
  }
}

export const orderDetailsRoute: Routes = [
  {
    path: '',
    component: OrderDetailsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'OrderDetails',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrderDetailsDetailComponent,
    resolve: {
      orderDetails: OrderDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'OrderDetails',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrderDetailsUpdateComponent,
    resolve: {
      orderDetails: OrderDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'OrderDetails',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrderDetailsUpdateComponent,
    resolve: {
      orderDetails: OrderDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'OrderDetails',
    },
    canActivate: [UserRouteAccessService],
  },
];
