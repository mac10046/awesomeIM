import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AwesomeimTestModule } from '../../../test.module';
import { BusinessOpportunityComponent } from 'app/entities/business-opportunity/business-opportunity.component';
import { BusinessOpportunityService } from 'app/entities/business-opportunity/business-opportunity.service';
import { BusinessOpportunity } from 'app/shared/model/business-opportunity.model';

describe('Component Tests', () => {
  describe('BusinessOpportunity Management Component', () => {
    let comp: BusinessOpportunityComponent;
    let fixture: ComponentFixture<BusinessOpportunityComponent>;
    let service: BusinessOpportunityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [BusinessOpportunityComponent],
      })
        .overrideTemplate(BusinessOpportunityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BusinessOpportunityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessOpportunityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BusinessOpportunity(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.businessOpportunities && comp.businessOpportunities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
