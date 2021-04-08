import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'business-details',
        loadChildren: () => import('./business-details/business-details.module').then(m => m.AwesomeimBusinessDetailsModule),
      },
      {
        path: 'products',
        loadChildren: () => import('./products/products.module').then(m => m.AwesomeimProductsModule),
      },
      {
        path: 'reviews',
        loadChildren: () => import('./reviews/reviews.module').then(m => m.AwesomeimReviewsModule),
      },
      {
        path: 'cart',
        loadChildren: () => import('./cart/cart.module').then(m => m.AwesomeimCartModule),
      },
      {
        path: 'coupons',
        loadChildren: () => import('./coupons/coupons.module').then(m => m.AwesomeimCouponsModule),
      },
      {
        path: 'shipping',
        loadChildren: () => import('./shipping/shipping.module').then(m => m.AwesomeimShippingModule),
      },
      {
        path: 'shippers',
        loadChildren: () => import('./shippers/shippers.module').then(m => m.AwesomeimShippersModule),
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.AwesomeimAddressModule),
      },
      {
        path: 'orders',
        loadChildren: () => import('./orders/orders.module').then(m => m.AwesomeimOrdersModule),
      },
      {
        path: 'order-details',
        loadChildren: () => import('./order-details/order-details.module').then(m => m.AwesomeimOrderDetailsModule),
      },
      {
        path: 'customers',
        loadChildren: () => import('./customers/customers.module').then(m => m.AwesomeimCustomersModule),
      },
      {
        path: 'payments',
        loadChildren: () => import('./payments/payments.module').then(m => m.AwesomeimPaymentsModule),
      },
      {
        path: 'quotes',
        loadChildren: () => import('./quotes/quotes.module').then(m => m.AwesomeimQuotesModule),
      },
      {
        path: 'invoices',
        loadChildren: () => import('./invoices/invoices.module').then(m => m.AwesomeimInvoicesModule),
      },
      {
        path: 'doc-details',
        loadChildren: () => import('./doc-details/doc-details.module').then(m => m.AwesomeimDocDetailsModule),
      },
      {
        path: 'taxes',
        loadChildren: () => import('./taxes/taxes.module').then(m => m.AwesomeimTaxesModule),
      },
      {
        path: 'business-opportunity',
        loadChildren: () => import('./business-opportunity/business-opportunity.module').then(m => m.AwesomeimBusinessOpportunityModule),
      },
      {
        path: 'business-offer',
        loadChildren: () => import('./business-offer/business-offer.module').then(m => m.AwesomeimBusinessOfferModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class AwesomeimEntityModule {}
