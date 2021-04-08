import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { OrdersDetailComponent } from 'app/entities/orders/orders-detail.component';
import { Orders } from 'app/shared/model/orders.model';

describe('Component Tests', () => {
  describe('Orders Management Detail Component', () => {
    let comp: OrdersDetailComponent;
    let fixture: ComponentFixture<OrdersDetailComponent>;
    const route = ({ data: of({ orders: new Orders(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [OrdersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OrdersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrdersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load orders on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orders).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
