import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AwesomeimTestModule } from '../../../test.module';
import { BusinessDetailsComponent } from 'app/entities/business-details/business-details.component';
import { BusinessDetailsService } from 'app/entities/business-details/business-details.service';
import { BusinessDetails } from 'app/shared/model/business-details.model';

describe('Component Tests', () => {
  describe('BusinessDetails Management Component', () => {
    let comp: BusinessDetailsComponent;
    let fixture: ComponentFixture<BusinessDetailsComponent>;
    let service: BusinessDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [BusinessDetailsComponent],
      })
        .overrideTemplate(BusinessDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BusinessDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BusinessDetails(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.businessDetails && comp.businessDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
