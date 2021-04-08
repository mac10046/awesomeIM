import { IShipping } from 'app/shared/model/shipping.model';
import { IShippers } from 'app/shared/model/shippers.model';
import { ICustomers } from 'app/shared/model/customers.model';

export interface IAddress {
  id?: number;
  street?: string;
  city?: string;
  country?: string;
  zipCode?: string;
  shipping?: IShipping;
  shippers?: IShippers;
  customers?: ICustomers;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public street?: string,
    public city?: string,
    public country?: string,
    public zipCode?: string,
    public shipping?: IShipping,
    public shippers?: IShippers,
    public customers?: ICustomers
  ) {}
}
