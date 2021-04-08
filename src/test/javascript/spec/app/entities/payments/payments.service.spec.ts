import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PaymentsService } from 'app/entities/payments/payments.service';
import { IPayments, Payments } from 'app/shared/model/payments.model';

describe('Service Tests', () => {
  describe('Payments Service', () => {
    let injector: TestBed;
    let service: PaymentsService;
    let httpMock: HttpTestingController;
    let elemDefault: IPayments;
    let expectedResult: IPayments | IPayments[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PaymentsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Payments(
        0,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        0,
        'AAAAAAA',
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
            paymentDate: currentDate.format(DATE_FORMAT),
            responseTimestamp: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Payments', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            paymentDate: currentDate.format(DATE_FORMAT),
            responseTimestamp: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            paymentDate: currentDate,
            responseTimestamp: currentDate,
          },
          returnedFromService
        );

        service.create(new Payments()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Payments', () => {
        const returnedFromService = Object.assign(
          {
            gatewayId: 'BBBBBB',
            paymentDate: currentDate.format(DATE_FORMAT),
            bankTxn: 'BBBBBB',
            txnToken: 'BBBBBB',
            responseTimestamp: currentDate.format(DATE_TIME_FORMAT),
            checksum: 'BBBBBB',
            txnAmount: 1,
            bankName: 'BBBBBB',
            responseCode: 'BBBBBB',
            responseMessage: 'BBBBBB',
            result: 'BBBBBB',
            email: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            paymentDate: currentDate,
            responseTimestamp: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Payments', () => {
        const returnedFromService = Object.assign(
          {
            gatewayId: 'BBBBBB',
            paymentDate: currentDate.format(DATE_FORMAT),
            bankTxn: 'BBBBBB',
            txnToken: 'BBBBBB',
            responseTimestamp: currentDate.format(DATE_TIME_FORMAT),
            checksum: 'BBBBBB',
            txnAmount: 1,
            bankName: 'BBBBBB',
            responseCode: 'BBBBBB',
            responseMessage: 'BBBBBB',
            result: 'BBBBBB',
            email: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            paymentDate: currentDate,
            responseTimestamp: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Payments', () => {
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
