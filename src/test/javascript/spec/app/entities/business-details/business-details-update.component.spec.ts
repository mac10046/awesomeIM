import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { BusinessDetailsUpdateComponent } from 'app/entities/business-details/business-details-update.component';
import { BusinessDetailsService } from 'app/entities/business-details/business-details.service';
import { BusinessDetails } from 'app/shared/model/business-details.model';

describe('Component Tests', () => {
  describe('BusinessDetails Management Update Component', () => {
    let comp: BusinessDetailsUpdateComponent;
    let fixture: ComponentFixture<BusinessDetailsUpdateComponent>;
    let service: BusinessDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [BusinessDetailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BusinessDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BusinessDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BusinessDetails(123);
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
        const entity = new BusinessDetails();
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
