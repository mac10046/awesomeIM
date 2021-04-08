import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ShippingService } from 'app/entities/shipping/shipping.service';
import { IShipping, Shipping } from 'app/shared/model/shipping.model';

describe('Service Tests', () => {
  describe('Shipping Service', () => {
    let injector: TestBed;
    let service: ShippingService;
    let httpMock: HttpTestingController;
    let elemDefault: IShipping;
    let expectedResult: IShipping | IShipping[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ShippingService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Shipping(0, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            shipDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Shipping', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            shipDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            shipDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Shipping()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Shipping', () => {
        const returnedFromService = Object.assign(
          {
            shipDate: currentDate.format(DATE_FORMAT),
            trackingId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            shipDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Shipping', () => {
        const returnedFromService = Object.assign(
          {
            shipDate: currentDate.format(DATE_FORMAT),
            trackingId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            shipDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Shipping', () => {
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
