import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrders, Orders } from 'app/shared/model/orders.model';
import { OrdersService } from './orders.service';
import { OrdersComponent } from './orders.component';
import { OrdersDetailComponent } from './orders-detail.component';
import { OrdersUpdateComponent } from './orders-update.component';

@Injectable({ providedIn: 'root' })
export class OrdersResolve implements Resolve<IOrders> {
  constructor(private service: OrdersService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrders> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((orders: HttpResponse<Orders>) => {
          if (orders.body) {
            return of(orders.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Orders());
  }
}

export const ordersRoute: Routes = [
  {
    path: '',
    component: OrdersComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Orders',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrdersDetailComponent,
    resolve: {
      orders: OrdersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Orders',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrdersUpdateComponent,
    resolve: {
      orders: OrdersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Orders',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrdersUpdateComponent,
    resolve: {
      orders: OrdersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Orders',
    },
    canActivate: [UserRouteAccessService],
  },
];
