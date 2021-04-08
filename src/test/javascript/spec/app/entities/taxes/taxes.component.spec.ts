import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AwesomeimTestModule } from '../../../test.module';
import { TaxesComponent } from 'app/entities/taxes/taxes.component';
import { TaxesService } from 'app/entities/taxes/taxes.service';
import { Taxes } from 'app/shared/model/taxes.model';

describe('Component Tests', () => {
  describe('Taxes Management Component', () => {
    let comp: TaxesComponent;
    let fixture: ComponentFixture<TaxesComponent>;
    let service: TaxesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [TaxesComponent],
      })
        .overrideTemplate(TaxesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaxesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaxesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Taxes(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.taxes && comp.taxes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
