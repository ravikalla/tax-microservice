import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Taxform } from 'app/shared/model/recipienthelper/taxform.model';
import { TaxformService } from './taxform.service';
import { TaxformComponent } from './taxform.component';
import { TaxformDetailComponent } from './taxform-detail.component';
import { TaxformUpdateComponent } from './taxform-update.component';
import { TaxformDeletePopupComponent } from './taxform-delete-dialog.component';
import { ITaxform } from 'app/shared/model/recipienthelper/taxform.model';

@Injectable({ providedIn: 'root' })
export class TaxformResolve implements Resolve<ITaxform> {
    constructor(private service: TaxformService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((taxform: HttpResponse<Taxform>) => taxform.body);
        }
        return Observable.of(new Taxform());
    }
}

export const taxformRoute: Routes = [
    {
        path: 'taxform',
        component: TaxformComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Taxforms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'taxform/:id/view',
        component: TaxformDetailComponent,
        resolve: {
            taxform: TaxformResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Taxforms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'taxform/new',
        component: TaxformUpdateComponent,
        resolve: {
            taxform: TaxformResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Taxforms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'taxform/:id/edit',
        component: TaxformUpdateComponent,
        resolve: {
            taxform: TaxformResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Taxforms'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const taxformPopupRoute: Routes = [
    {
        path: 'taxform/:id/delete',
        component: TaxformDeletePopupComponent,
        resolve: {
            taxform: TaxformResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Taxforms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
