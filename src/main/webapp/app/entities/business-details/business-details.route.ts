import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBusinessDetails, BusinessDetails } from 'app/shared/model/business-details.model';
import { BusinessDetailsService } from './business-details.service';
import { BusinessDetailsComponent } from './business-details.component';
import { BusinessDetailsDetailComponent } from './business-details-detail.component';
import { BusinessDetailsUpdateComponent } from './business-details-update.component';

@Injectable({ providedIn: 'root' })
export class BusinessDetailsResolve implements Resolve<IBusinessDetails> {
  constructor(private service: BusinessDetailsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBusinessDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((businessDetails: HttpResponse<BusinessDetails>) => {
          if (businessDetails.body) {
            return of(businessDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BusinessDetails());
  }
}

export const businessDetailsRoute: Routes = [
  {
    path: '',
    component: BusinessDetailsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BusinessDetails',
    },
    // canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BusinessDetailsDetailComponent,
    resolve: {
      businessDetails: BusinessDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BusinessDetails',
    },
    // canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BusinessDetailsUpdateComponent,
    resolve: {
      businessDetails: BusinessDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BusinessDetails',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BusinessDetailsUpdateComponent,
    resolve: {
      businessDetails: BusinessDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BusinessDetails',
    },
    canActivate: [UserRouteAccessService],
  },
];
