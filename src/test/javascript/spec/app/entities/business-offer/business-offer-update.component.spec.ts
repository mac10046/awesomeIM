import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { BusinessOfferUpdateComponent } from 'app/entities/business-offer/business-offer-update.component';
import { BusinessOfferService } from 'app/entities/business-offer/business-offer.service';
import { BusinessOffer } from 'app/shared/model/business-offer.model';

describe('Component Tests', () => {
  describe('BusinessOffer Management Update Component', () => {
    let comp: BusinessOfferUpdateComponent;
    let fixture: ComponentFixture<BusinessOfferUpdateComponent>;
    let service: BusinessOfferService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [BusinessOfferUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BusinessOfferUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BusinessOfferUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessOfferService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BusinessOffer(123);
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
        const entity = new BusinessOffer();
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
