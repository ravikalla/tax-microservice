import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Wfexecution } from 'app/shared/model/wftfv/wfexecution.model';
import { WfexecutionService } from './wfexecution.service';
import { WfexecutionComponent } from './wfexecution.component';
import { WfexecutionDetailComponent } from './wfexecution-detail.component';
import { WfexecutionUpdateComponent } from './wfexecution-update.component';
import { WfexecutionDeletePopupComponent } from './wfexecution-delete-dialog.component';
import { IWfexecution } from 'app/shared/model/wftfv/wfexecution.model';

@Injectable({ providedIn: 'root' })
export class WfexecutionResolve implements Resolve<IWfexecution> {
    constructor(private service: WfexecutionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((wfexecution: HttpResponse<Wfexecution>) => wfexecution.body);
        }
        return Observable.of(new Wfexecution());
    }
}

export const wfexecutionRoute: Routes = [
    {
        path: 'wfexecution',
        component: WfexecutionComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Wfexecutions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wfexecution/:id/view',
        component: WfexecutionDetailComponent,
        resolve: {
            wfexecution: WfexecutionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Wfexecutions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wfexecution/new',
        component: WfexecutionUpdateComponent,
        resolve: {
            wfexecution: WfexecutionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Wfexecutions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wfexecution/:id/edit',
        component: WfexecutionUpdateComponent,
        resolve: {
            wfexecution: WfexecutionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Wfexecutions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const wfexecutionPopupRoute: Routes = [
    {
        path: 'wfexecution/:id/delete',
        component: WfexecutionDeletePopupComponent,
        resolve: {
            wfexecution: WfexecutionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Wfexecutions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
