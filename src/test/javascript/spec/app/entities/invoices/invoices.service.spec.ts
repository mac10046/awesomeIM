import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { InvoicesService } from 'app/entities/invoices/invoices.service';
import { IInvoices, Invoices } from 'app/shared/model/invoices.model';

describe('Service Tests', () => {
  describe('Invoices Service', () => {
    let injector: TestBed;
    let service: InvoicesService;
    let httpMock: HttpTestingController;
    let elemDefault: IInvoices;
    let expectedResult: IInvoices | IInvoices[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InvoicesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Invoices(0, 'AAAAAAA', currentDate, 'AAAAAAA', false, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            invoiceDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Invoices', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            invoiceDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            invoiceDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Invoices()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Invoices', () => {
        const returnedFromService = Object.assign(
          {
            gstin: 'BBBBBB',
            invoiceDate: currentDate.format(DATE_TIME_FORMAT),
            terms: 'BBBBBB',
            isPaid: true,
            amount: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            invoiceDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Invoices', () => {
        const returnedFromService = Object.assign(
          {
            gstin: 'BBBBBB',
            invoiceDate: currentDate.format(DATE_TIME_FORMAT),
            terms: 'BBBBBB',
            isPaid: true,
            amount: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            invoiceDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Invoices', () => {
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
