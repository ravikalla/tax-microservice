import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Wfrequest } from 'app/shared/model/wftfv/wfrequest.model';
import { WfrequestService } from './wfrequest.service';
import { WfrequestComponent } from './wfrequest.component';
import { WfrequestDetailComponent } from './wfrequest-detail.component';
import { WfrequestUpdateComponent } from './wfrequest-update.component';
import { WfrequestDeletePopupComponent } from './wfrequest-delete-dialog.component';
import { IWfrequest } from 'app/shared/model/wftfv/wfrequest.model';

@Injectable({ providedIn: 'root' })
export class WfrequestResolve implements Resolve<IWfrequest> {
    constructor(private service: WfrequestService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((wfrequest: HttpResponse<Wfrequest>) => wfrequest.body);
        }
        return Observable.of(new Wfrequest());
    }
}

export const wfrequestRoute: Routes = [
    {
        path: 'wfrequest',
        component: WfrequestComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Wfrequests'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wfrequest/:id/view',
        component: WfrequestDetailComponent,
        resolve: {
            wfrequest: WfrequestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Wfrequests'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wfrequest/new',
        component: WfrequestUpdateComponent,
        resolve: {
            wfrequest: WfrequestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Wfrequests'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wfrequest/:id/edit',
        component: WfrequestUpdateComponent,
        resolve: {
            wfrequest: WfrequestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Wfrequests'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const wfrequestPopupRoute: Routes = [
    {
        path: 'wfrequest/:id/delete',
        component: WfrequestDeletePopupComponent,
        resolve: {
            wfrequest: WfrequestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Wfrequests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
