import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { BusinessOfferDetailComponent } from 'app/entities/business-offer/business-offer-detail.component';
import { BusinessOffer } from 'app/shared/model/business-offer.model';

describe('Component Tests', () => {
  describe('BusinessOffer Management Detail Component', () => {
    let comp: BusinessOfferDetailComponent;
    let fixture: ComponentFixture<BusinessOfferDetailComponent>;
    const route = ({ data: of({ businessOffer: new BusinessOffer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [BusinessOfferDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BusinessOfferDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BusinessOfferDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load businessOffer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.businessOffer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
