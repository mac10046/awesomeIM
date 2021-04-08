import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { DocDetailsUpdateComponent } from 'app/entities/doc-details/doc-details-update.component';
import { DocDetailsService } from 'app/entities/doc-details/doc-details.service';
import { DocDetails } from 'app/shared/model/doc-details.model';

describe('Component Tests', () => {
  describe('DocDetails Management Update Component', () => {
    let comp: DocDetailsUpdateComponent;
    let fixture: ComponentFixture<DocDetailsUpdateComponent>;
    let service: DocDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [DocDetailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DocDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DocDetails(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DocDetails();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
