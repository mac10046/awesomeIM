import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { PaymentsUpdateComponent } from 'app/entities/payments/payments-update.component';
import { PaymentsService } from 'app/entities/payments/payments.service';
import { Payments } from 'app/shared/model/payments.model';

describe('Component Tests', () => {
  describe('Payments Management Update Component', () => {
    let comp: PaymentsUpdateComponent;
    let fixture: ComponentFixture<PaymentsUpdateComponent>;
    let service: PaymentsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [PaymentsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PaymentsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Payments(123);
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
        const entity = new Payments();
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
