import { Moment } from 'moment';
import { ICustomers } from 'app/shared/model/customers.model';
import { IShipping } from 'app/shared/model/shipping.model';
import { IOrderDetails } from 'app/shared/model/order-details.model';
import { IPayments } from 'app/shared/model/payments.model';

export interface IOrders {
  id?: number;
  orderDate?: Moment;
  isPaid?: boolean;
  amount?: number;
  customers?: ICustomers;
  shippings?: IShipping[];
  orderDetails?: IOrderDetails[];
  payments?: IPayments;
}

export class Orders implements IOrders {
  constructor(
    public id?: number,
    public orderDate?: Moment,
    public isPaid?: boolean,
    public amount?: number,
    public customers?: ICustomers,
    public shippings?: IShipping[],
    public orderDetails?: IOrderDetails[],
    public payments?: IPayments
  ) {
    this.isPaid = this.isPaid || false;
  }
}
