import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { ShippersDetailComponent } from 'app/entities/shippers/shippers-detail.component';
import { Shippers } from 'app/shared/model/shippers.model';

describe('Component Tests', () => {
  describe('Shippers Management Detail Component', () => {
    let comp: ShippersDetailComponent;
    let fixture: ComponentFixture<ShippersDetailComponent>;
    const route = ({ data: of({ shippers: new Shippers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [ShippersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ShippersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShippersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load shippers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.shippers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
