import { Moment } from 'moment';
import { ICustomers } from 'app/shared/model/customers.model';
import { IBusinessDetails } from 'app/shared/model/business-details.model';
import { IDocDetails } from 'app/shared/model/doc-details.model';

export interface IInvoices {
  id?: number;
  gstin?: string;
  invoiceDate?: Moment;
  terms?: string;
  isPaid?: boolean;
  amount?: number;
  customers?: ICustomers;
  businessDetails?: IBusinessDetails;
  docDetails?: IDocDetails[];
}

export class Invoices implements IInvoices {
  constructor(
    public id?: number,
    public gstin?: string,
    public invoiceDate?: Moment,
    public terms?: string,
    public isPaid?: boolean,
    public amount?: number,
    public customers?: ICustomers,
    public businessDetails?: IBusinessDetails,
    public docDetails?: IDocDetails[]
  ) {
    this.isPaid = this.isPaid || false;
  }
}
