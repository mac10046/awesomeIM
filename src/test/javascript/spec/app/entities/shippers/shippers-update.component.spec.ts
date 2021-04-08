import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AwesomeimTestModule } from '../../../test.module';
import { ShippersUpdateComponent } from 'app/entities/shippers/shippers-update.component';
import { ShippersService } from 'app/entities/shippers/shippers.service';
import { Shippers } from 'app/shared/model/shippers.model';

describe('Component Tests', () => {
  describe('Shippers Management Update Component', () => {
    let comp: ShippersUpdateComponent;
    let fixture: ComponentFixture<ShippersUpdateComponent>;
    let service: ShippersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [ShippersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ShippersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShippersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShippersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Shippers(123);
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
        const entity = new Shippers();
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
