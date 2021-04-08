import { IAddress } from 'app/shared/model/address.model';
import { IInvoices } from 'app/shared/model/invoices.model';
import { IQuotes } from 'app/shared/model/quotes.model';
import { IOrders } from 'app/shared/model/orders.model';

export interface ICustomers {
  id?: number;
  name?: string;
  contactNumber?: string;
  email?: string;
  addresses?: IAddress[];
  invoices?: IInvoices;
  quotes?: IQuotes;
  orders?: IOrders;
}

export class Customers implements ICustomers {
  constructor(
    public id?: number,
    public name?: string,
    public contactNumber?: string,
    public email?: string,
    public addresses?: IAddress[],
    public invoices?: IInvoices,
    public quotes?: IQuotes,
    public orders?: IOrders
  ) {}
}
