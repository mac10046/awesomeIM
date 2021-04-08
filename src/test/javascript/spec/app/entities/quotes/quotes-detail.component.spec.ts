import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { QuotesDetailComponent } from 'app/entities/quotes/quotes-detail.component';
import { Quotes } from 'app/shared/model/quotes.model';

describe('Component Tests', () => {
  describe('Quotes Management Detail Component', () => {
    let comp: QuotesDetailComponent;
    let fixture: ComponentFixture<QuotesDetailComponent>;
    const route = ({ data: of({ quotes: new Quotes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [QuotesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QuotesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuotesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load quotes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.quotes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
