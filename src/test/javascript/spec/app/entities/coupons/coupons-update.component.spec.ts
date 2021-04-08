import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { CouponsUpdateComponent } from 'app/entities/coupons/coupons-update.component';
import { CouponsService } from 'app/entities/coupons/coupons.service';
import { Coupons } from 'app/shared/model/coupons.model';

describe('Component Tests', () => {
  describe('Coupons Management Update Component', () => {
    let comp: CouponsUpdateComponent;
    let fixture: ComponentFixture<CouponsUpdateComponent>;
    let service: CouponsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [CouponsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CouponsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CouponsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CouponsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Coupons(123);
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
        const entity = new Coupons();
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
