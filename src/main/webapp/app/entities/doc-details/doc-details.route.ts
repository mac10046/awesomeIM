import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDocDetails, DocDetails } from 'app/shared/model/doc-details.model';
import { DocDetailsService } from './doc-details.service';
import { DocDetailsComponent } from './doc-details.component';
import { DocDetailsDetailComponent } from './doc-details-detail.component';
import { DocDetailsUpdateComponent } from './doc-details-update.component';

@Injectable({ providedIn: 'root' })
export class DocDetailsResolve implements Resolve<IDocDetails> {
  constructor(private service: DocDetailsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((docDetails: HttpResponse<DocDetails>) => {
          if (docDetails.body) {
            return of(docDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DocDetails());
  }
}

export const docDetailsRoute: Routes = [
  {
    path: '',
    component: DocDetailsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DocDetails',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DocDetailsDetailComponent,
    resolve: {
      docDetails: DocDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DocDetails',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DocDetailsUpdateComponent,
    resolve: {
      docDetails: DocDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DocDetails',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DocDetailsUpdateComponent,
    resolve: {
      docDetails: DocDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DocDetails',
    },
    canActivate: [UserRouteAccessService],
  },
];
