import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AwesomeimTestModule } from '../../../test.module';
import { DocDetailsComponent } from 'app/entities/doc-details/doc-details.component';
import { DocDetailsService } from 'app/entities/doc-details/doc-details.service';
import { DocDetails } from 'app/shared/model/doc-details.model';

describe('Component Tests', () => {
  describe('DocDetails Management Component', () => {
    let comp: DocDetailsComponent;
    let fixture: ComponentFixture<DocDetailsComponent>;
    let service: DocDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [DocDetailsComponent],
      })
        .overrideTemplate(DocDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DocDetails(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.docDetails && comp.docDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
