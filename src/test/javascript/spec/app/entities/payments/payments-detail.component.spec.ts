import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { PaymentsDetailComponent } from 'app/entities/payments/payments-detail.component';
import { Payments } from 'app/shared/model/payments.model';

describe('Component Tests', () => {
  describe('Payments Management Detail Component', () => {
    let comp: PaymentsDetailComponent;
    let fixture: ComponentFixture<PaymentsDetailComponent>;
    const route = ({ data: of({ payments: new Payments(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [PaymentsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PaymentsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load payments on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.payments).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
