import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { BusinessDetailsService } from 'app/entities/business-details/business-details.service';
import { IBusinessDetails, BusinessDetails } from 'app/shared/model/business-details.model';
import { BusinessType } from 'app/shared/model/enumerations/business-type.model';

describe('Service Tests', () => {
  describe('BusinessDetails Service', () => {
    let injector: TestBed;
    let service: BusinessDetailsService;
    let httpMock: HttpTestingController;
    let elemDefault: IBusinessDetails;
    let expectedResult: IBusinessDetails | IBusinessDetails[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BusinessDetailsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new BusinessDetails(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        BusinessType.Service,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            inceptionDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a BusinessDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            inceptionDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inceptionDate: currentDate,
          },
          returnedFromService
        );

        service.create(new BusinessDetails()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BusinessDetails', () => {
        const returnedFromService = Object.assign(
          {
            businessName: 'BBBBBB',
            registeredAddress: 'BBBBBB',
            description: 'BBBBBB',
            inceptionDate: currentDate.format(DATE_FORMAT),
            gstin: 'BBBBBB',
            category: 'BBBBBB',
            subCategory: 'BBBBBB',
            email: 'BBBBBB',
            contactNumber: 'BBBBBB',
            managingPersonName: 'BBBBBB',
            logo: 'BBBBBB',
            managingPersonImage: 'BBBBBB',
            businessType: 'BBBBBB',
            upi: 'BBBBBB',
            bankName: 'BBBBBB',
            ifscCode: 'BBBBBB',
            branchName: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inceptionDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BusinessDetails', () => {
        const returnedFromService = Object.assign(
          {
            businessName: 'BBBBBB',
            registeredAddress: 'BBBBBB',
            description: 'BBBBBB',
            inceptionDate: currentDate.format(DATE_FORMAT),
            gstin: 'BBBBBB',
            category: 'BBBBBB',
            subCategory: 'BBBBBB',
            email: 'BBBBBB',
            contactNumber: 'BBBBBB',
            managingPersonName: 'BBBBBB',
            logo: 'BBBBBB',
            managingPersonImage: 'BBBBBB',
            businessType: 'BBBBBB',
            upi: 'BBBBBB',
            bankName: 'BBBBBB',
            ifscCode: 'BBBBBB',
            branchName: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inceptionDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a BusinessDetails', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
