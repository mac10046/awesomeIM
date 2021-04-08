import { Moment } from 'moment';
import { IBusinessOpportunity } from 'app/shared/model/business-opportunity.model';

export interface IBusinessOffer {
  id?: number;
  time?: Moment;
  message?: string;
  name?: string;
  email?: string;
  businessOpportunity?: IBusinessOpportunity;
}

export class BusinessOffer implements IBusinessOffer {
  constructor(
    public id?: number,
    public time?: Moment,
    public message?: string,
    public name?: string,
    public email?: string,
    public businessOpportunity?: IBusinessOpportunity
  ) {}
}
