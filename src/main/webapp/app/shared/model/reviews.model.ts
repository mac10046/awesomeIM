import { Moment } from 'moment';
import { IBusinessDetails } from 'app/shared/model/business-details.model';

export interface IReviews {
  id?: number;
  name?: string;
  message?: string;
  reviewDate?: Moment;
  rating?: number;
  designation?: string;
  businessDetails?: IBusinessDetails;
}

export class Reviews implements IReviews {
  constructor(
    public id?: number,
    public name?: string,
    public message?: string,
    public reviewDate?: Moment,
    public rating?: number,
    public designation?: string,
    public businessDetails?: IBusinessDetails
  ) {}
}
