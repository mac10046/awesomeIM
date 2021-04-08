import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { TaxesDetailComponent } from 'app/entities/taxes/taxes-detail.component';
import { Taxes } from 'app/shared/model/taxes.model';

describe('Component Tests', () => {
  describe('Taxes Management Detail Component', () => {
    let comp: TaxesDetailComponent;
    let fixture: ComponentFixture<TaxesDetailComponent>;
    const route = ({ data: of({ taxes: new Taxes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [TaxesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TaxesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaxesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taxes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taxes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
