import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { AwesomeimTestModule } from '../../../test.module';
import { BusinessDetailsDetailComponent } from 'app/entities/business-details/business-details-detail.component';
import { BusinessDetails } from 'app/shared/model/business-details.model';

describe('Component Tests', () => {
  describe('BusinessDetails Management Detail Component', () => {
    let comp: BusinessDetailsDetailComponent;
    let fixture: ComponentFixture<BusinessDetailsDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ businessDetails: new BusinessDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AwesomeimTestModule],
        declarations: [BusinessDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BusinessDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BusinessDetailsDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load businessDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.businessDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
