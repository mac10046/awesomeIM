import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICustomers, Customers } from 'app/shared/model/customers.model';
import { CustomersService } from './customers.service';
import { CustomersComponent } from './customers.component';
import { CustomersDetailComponent } from './customers-detail.component';
import { CustomersUpdateComponent } from './customers-update.component';

@Injectable({ providedIn: 'root' })
export class CustomersResolve implements Resolve<ICustomers> {
  constructor(private service: CustomersService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomers> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((customers: HttpResponse<Customers>) => {
          if (customers.body) {
            return of(customers.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Customers());
  }
}

export const customersRoute: Routes = [
  {
    path: '',
    component: CustomersComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Customers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomersDetailComponent,
    resolve: {
      customers: CustomersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Customers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomersUpdateComponent,
    resolve: {
      customers: CustomersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Customers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomersUpdateComponent,
    resolve: {
      customers: CustomersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Customers',
    },
    canActivate: [UserRouteAccessService],
  },
];
