import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ReviewsService } from 'app/entities/reviews/reviews.service';
import { IReviews, Reviews } from 'app/shared/model/reviews.model';

describe('Service Tests', () => {
  describe('Reviews Service', () => {
    let injector: TestBed;
    let service: ReviewsService;
    let httpMock: HttpTestingController;
    let elemDefault: IReviews;
    let expectedResult: IReviews | IReviews[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ReviewsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Reviews(0, 'AAAAAAA', 'AAAAAAA', currentDate, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            reviewDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Reviews', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            reviewDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            reviewDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Reviews()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Reviews', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            message: 'BBBBBB',
            reviewDate: currentDate.format(DATE_TIME_FORMAT),
            rating: 1,
            designation: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            reviewDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Reviews', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            message: 'BBBBBB',
            reviewDate: currentDate.format(DATE_TIME_FORMAT),
            rating: 1,
            designation: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            reviewDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Reviews', () => {
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
