import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AwesomeimTestModule } from '../../../test.module';
import { OrderDetailsComponent } from 'app/entities/order-details/order-details.component';
import { OrderDetailsService } from 'app/entities/order-details/order-details.service';
import { OrderDetails } from 'app/shared/model/order-details.model';

describe('Component Tests', () => {
  describe('OrderDetails Management Component', () => {
    let comp: OrderDetailsComponent;
    let fixture: ComponentFixture<OrderDetailsComponent>;
    let service: OrderDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [OrderDetailsComponent],
      })
        .overrideTemplate(OrderDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OrderDetails(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.orderDetails && comp.orderDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
