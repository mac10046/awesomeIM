import { Moment } from 'moment';
import { ICustomers } from 'app/shared/model/customers.model';
import { IBusinessDetails } from 'app/shared/model/business-details.model';
import { IDocDetails } from 'app/shared/model/doc-details.model';

export interface IQuotes {
  id?: number;
  gstin?: string;
  quoteDate?: Moment;
  terms?: string;
  amount?: number;
  customers?: ICustomers;
  businessDetails?: IBusinessDetails;
  docDetails?: IDocDetails[];
}

export class Quotes implements IQuotes {
  constructor(
    public id?: number,
    public gstin?: string,
    public quoteDate?: Moment,
    public terms?: string,
    public amount?: number,
    public customers?: ICustomers,
    public businessDetails?: IBusinessDetails,
    public docDetails?: IDocDetails[]
  ) {}
}
