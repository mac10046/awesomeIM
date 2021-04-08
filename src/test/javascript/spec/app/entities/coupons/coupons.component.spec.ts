import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AwesomeimTestModule } from '../../../test.module';
import { CouponsComponent } from 'app/entities/coupons/coupons.component';
import { CouponsService } from 'app/entities/coupons/coupons.service';
import { Coupons } from 'app/shared/model/coupons.model';

describe('Component Tests', () => {
  describe('Coupons Management Component', () => {
    let comp: CouponsComponent;
    let fixture: ComponentFixture<CouponsComponent>;
    let service: CouponsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [CouponsComponent],
      })
        .overrideTemplate(CouponsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CouponsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CouponsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Coupons(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.coupons && comp.coupons[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
