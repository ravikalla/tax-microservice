import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Recipient } from 'app/shared/model/recipienthelper/recipient.model';
import { RecipientService } from './recipient.service';
import { RecipientComponent } from './recipient.component';
import { RecipientDetailComponent } from './recipient-detail.component';
import { RecipientUpdateComponent } from './recipient-update.component';
import { RecipientDeletePopupComponent } from './recipient-delete-dialog.component';
import { IRecipient } from 'app/shared/model/recipienthelper/recipient.model';

@Injectable({ providedIn: 'root' })
export class RecipientResolve implements Resolve<IRecipient> {
    constructor(private service: RecipientService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((recipient: HttpResponse<Recipient>) => recipient.body);
        }
        return Observable.of(new Recipient());
    }
}

export const recipientRoute: Routes = [
    {
        path: 'recipient',
        component: RecipientComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipients'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recipient/:id/view',
        component: RecipientDetailComponent,
        resolve: {
            recipient: RecipientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipients'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recipient/new',
        component: RecipientUpdateComponent,
        resolve: {
            recipient: RecipientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipients'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recipient/:id/edit',
        component: RecipientUpdateComponent,
        resolve: {
            recipient: RecipientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipients'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recipientPopupRoute: Routes = [
    {
        path: 'recipient/:id/delete',
        component: RecipientDeletePopupComponent,
        resolve: {
            recipient: RecipientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipients'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
