import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { TaxesUpdateComponent } from 'app/entities/taxes/taxes-update.component';
import { TaxesService } from 'app/entities/taxes/taxes.service';
import { Taxes } from 'app/shared/model/taxes.model';

describe('Component Tests', () => {
  describe('Taxes Management Update Component', () => {
    let comp: TaxesUpdateComponent;
    let fixture: ComponentFixture<TaxesUpdateComponent>;
    let service: TaxesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [TaxesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TaxesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaxesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaxesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Taxes(123);
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
        const entity = new Taxes();
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
