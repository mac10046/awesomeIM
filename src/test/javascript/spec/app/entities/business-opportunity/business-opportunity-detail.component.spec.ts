import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { BusinessOpportunityDetailComponent } from 'app/entities/business-opportunity/business-opportunity-detail.component';
import { BusinessOpportunity } from 'app/shared/model/business-opportunity.model';

describe('Component Tests', () => {
  describe('BusinessOpportunity Management Detail Component', () => {
    let comp: BusinessOpportunityDetailComponent;
    let fixture: ComponentFixture<BusinessOpportunityDetailComponent>;
    const route = ({ data: of({ businessOpportunity: new BusinessOpportunity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [BusinessOpportunityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BusinessOpportunityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BusinessOpportunityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load businessOpportunity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.businessOpportunity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
