import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { QuotesService } from 'app/entities/quotes/quotes.service';
import { IQuotes, Quotes } from 'app/shared/model/quotes.model';

describe('Service Tests', () => {
  describe('Quotes Service', () => {
    let injector: TestBed;
    let service: QuotesService;
    let httpMock: HttpTestingController;
    let elemDefault: IQuotes;
    let expectedResult: IQuotes | IQuotes[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(QuotesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Quotes(0, 'AAAAAAA', currentDate, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            quoteDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Quotes', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            quoteDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            quoteDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Quotes()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Quotes', () => {
        const returnedFromService = Object.assign(
          {
            gstin: 'BBBBBB',
            quoteDate: currentDate.format(DATE_TIME_FORMAT),
            terms: 'BBBBBB',
            amount: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            quoteDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Quotes', () => {
        const returnedFromService = Object.assign(
          {
            gstin: 'BBBBBB',
            quoteDate: currentDate.format(DATE_TIME_FORMAT),
            terms: 'BBBBBB',
            amount: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            quoteDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Quotes', () => {
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
