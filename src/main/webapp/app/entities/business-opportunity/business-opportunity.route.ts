import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBusinessOpportunity, BusinessOpportunity } from 'app/shared/model/business-opportunity.model';
import { BusinessOpportunityService } from './business-opportunity.service';
import { BusinessOpportunityComponent } from './business-opportunity.component';
import { BusinessOpportunityDetailComponent } from './business-opportunity-detail.component';
import { BusinessOpportunityUpdateComponent } from './business-opportunity-update.component';

@Injectable({ providedIn: 'root' })
export class BusinessOpportunityResolve implements Resolve<IBusinessOpportunity> {
  constructor(private service: BusinessOpportunityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBusinessOpportunity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((businessOpportunity: HttpResponse<BusinessOpportunity>) => {
          if (businessOpportunity.body) {
            return of(businessOpportunity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BusinessOpportunity());
  }
}

export const businessOpportunityRoute: Routes = [
  {
    path: '',
    component: BusinessOpportunityComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BusinessOpportunities',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BusinessOpportunityDetailComponent,
    resolve: {
      businessOpportunity: BusinessOpportunityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BusinessOpportunities',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BusinessOpportunityUpdateComponent,
    resolve: {
      businessOpportunity: BusinessOpportunityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BusinessOpportunities',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BusinessOpportunityUpdateComponent,
    resolve: {
      businessOpportunity: BusinessOpportunityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BusinessOpportunities',
    },
    canActivate: [UserRouteAccessService],
  },
];
