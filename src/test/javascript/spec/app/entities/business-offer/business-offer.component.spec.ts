import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AwesomeimTestModule } from '../../../test.module';
import { BusinessOfferComponent } from 'app/entities/business-offer/business-offer.component';
import { BusinessOfferService } from 'app/entities/business-offer/business-offer.service';
import { BusinessOffer } from 'app/shared/model/business-offer.model';

describe('Component Tests', () => {
  describe('BusinessOffer Management Component', () => {
    let comp: BusinessOfferComponent;
    let fixture: ComponentFixture<BusinessOfferComponent>;
    let service: BusinessOfferService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [BusinessOfferComponent],
      })
        .overrideTemplate(BusinessOfferComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BusinessOfferComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessOfferService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BusinessOffer(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.businessOffers && comp.businessOffers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
