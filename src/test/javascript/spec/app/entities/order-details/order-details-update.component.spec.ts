import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { OrderDetailsUpdateComponent } from 'app/entities/order-details/order-details-update.component';
import { OrderDetailsService } from 'app/entities/order-details/order-details.service';
import { OrderDetails } from 'app/shared/model/order-details.model';

describe('Component Tests', () => {
  describe('OrderDetails Management Update Component', () => {
    let comp: OrderDetailsUpdateComponent;
    let fixture: ComponentFixture<OrderDetailsUpdateComponent>;
    let service: OrderDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [OrderDetailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OrderDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderDetails(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderDetails();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
