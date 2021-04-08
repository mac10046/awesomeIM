import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IReviews, Reviews } from 'app/shared/model/reviews.model';
import { ReviewsService } from './reviews.service';
import { ReviewsComponent } from './reviews.component';
import { ReviewsDetailComponent } from './reviews-detail.component';
import { ReviewsUpdateComponent } from './reviews-update.component';

@Injectable({ providedIn: 'root' })
export class ReviewsResolve implements Resolve<IReviews> {
  constructor(private service: ReviewsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReviews> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((reviews: HttpResponse<Reviews>) => {
          if (reviews.body) {
            return of(reviews.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Reviews());
  }
}

export const reviewsRoute: Routes = [
  {
    path: '',
    component: ReviewsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Reviews',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReviewsDetailComponent,
    resolve: {
      reviews: ReviewsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Reviews',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReviewsUpdateComponent,
    resolve: {
      reviews: ReviewsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Reviews',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReviewsUpdateComponent,
    resolve: {
      reviews: ReviewsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Reviews',
    },
    canActivate: [UserRouteAccessService],
  },
];
