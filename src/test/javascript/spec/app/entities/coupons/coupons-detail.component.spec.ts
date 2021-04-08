import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { CouponsDetailComponent } from 'app/entities/coupons/coupons-detail.component';
import { Coupons } from 'app/shared/model/coupons.model';

describe('Component Tests', () => {
  describe('Coupons Management Detail Component', () => {
    let comp: CouponsDetailComponent;
    let fixture: ComponentFixture<CouponsDetailComponent>;
    const route = ({ data: of({ coupons: new Coupons(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [CouponsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CouponsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CouponsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load coupons on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.coupons).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
