import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { OrdersService } from 'app/entities/orders/orders.service';
import { IOrders, Orders } from 'app/shared/model/orders.model';

describe('Service Tests', () => {
  describe('Orders Service', () => {
    let injector: TestBed;
    let service: OrdersService;
    let httpMock: HttpTestingController;
    let elemDefault: IOrders;
    let expectedResult: IOrders | IOrders[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(OrdersService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Orders(0, currentDate, false, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            orderDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Orders', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            orderDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            orderDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Orders()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Orders', () => {
        const returnedFromService = Object.assign(
          {
            orderDate: currentDate.format(DATE_TIME_FORMAT),
            isPaid: true,
            amount: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            orderDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Orders', () => {
        const returnedFromService = Object.assign(
          {
            orderDate: currentDate.format(DATE_TIME_FORMAT),
            isPaid: true,
            amount: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            orderDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Orders', () => {
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
