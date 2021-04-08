import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { BusinessOpportunityUpdateComponent } from 'app/entities/business-opportunity/business-opportunity-update.component';
import { BusinessOpportunityService } from 'app/entities/business-opportunity/business-opportunity.service';
import { BusinessOpportunity } from 'app/shared/model/business-opportunity.model';

describe('Component Tests', () => {
  describe('BusinessOpportunity Management Update Component', () => {
    let comp: BusinessOpportunityUpdateComponent;
    let fixture: ComponentFixture<BusinessOpportunityUpdateComponent>;
    let service: BusinessOpportunityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [BusinessOpportunityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BusinessOpportunityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BusinessOpportunityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessOpportunityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BusinessOpportunity(123);
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
        const entity = new BusinessOpportunity();
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
