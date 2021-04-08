import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { DocDetailsDetailComponent } from 'app/entities/doc-details/doc-details-detail.component';
import { DocDetails } from 'app/shared/model/doc-details.model';

describe('Component Tests', () => {
  describe('DocDetails Management Detail Component', () => {
    let comp: DocDetailsDetailComponent;
    let fixture: ComponentFixture<DocDetailsDetailComponent>;
    const route = ({ data: of({ docDetails: new DocDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [DocDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DocDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DocDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load docDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.docDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
