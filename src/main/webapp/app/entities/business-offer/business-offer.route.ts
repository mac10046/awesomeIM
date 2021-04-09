import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBusinessOffer, BusinessOffer } from 'app/shared/model/business-offer.model';
import { BusinessOfferService } from './business-offer.service';
import { BusinessOfferComponent } from './business-offer.component';
import { BusinessOfferDetailComponent } from './business-offer-detail.component';
import { BusinessOfferUpdateComponent } from './business-offer-update.component';

@Injectable({ providedIn: 'root' })
export class BusinessOfferResolve implements Resolve<IBusinessOffer> {
  constructor(private service: BusinessOfferService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBusinessOffer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((businessOffer: HttpResponse<BusinessOffer>) => {
          if (businessOffer.body) {
            return of(businessOffer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BusinessOffer());
  }
}

export const businessOfferRoute: Routes = [
  {
    path: '',
    component: BusinessOfferComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BusinessOffers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BusinessOfferDetailComponent,
    resolve: {
      businessOffer: BusinessOfferResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BusinessOffers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BusinessOfferUpdateComponent,
    resolve: {
      businessOffer: BusinessOfferResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BusinessOffers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new/:id',
    component: BusinessOfferUpdateComponent,
    resolve: {
      businessOffer: BusinessOfferResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BusinessOffers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BusinessOfferUpdateComponent,
    resolve: {
      businessOffer: BusinessOfferResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BusinessOffers',
    },
    canActivate: [UserRouteAccessService],
  },
];
