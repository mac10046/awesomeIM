import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { QuotesUpdateComponent } from 'app/entities/quotes/quotes-update.component';
import { QuotesService } from 'app/entities/quotes/quotes.service';
import { Quotes } from 'app/shared/model/quotes.model';

describe('Component Tests', () => {
  describe('Quotes Management Update Component', () => {
    let comp: QuotesUpdateComponent;
    let fixture: ComponentFixture<QuotesUpdateComponent>;
    let service: QuotesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [QuotesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(QuotesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuotesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuotesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Quotes(123);
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
        const entity = new Quotes();
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
