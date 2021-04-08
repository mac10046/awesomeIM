import { IOrders } from 'app/shared/model/orders.model';

export interface IOrderDetails {
  id?: number;
  description?: string;
  qty?: number;
  price?: number;
  discount?: number;
  taxes?: number;
  isTaxPercent?: number;
  orders?: IOrders;
}

export class OrderDetails implements IOrderDetails {
  constructor(
    public id?: number,
    public description?: string,
    public qty?: number,
    public price?: number,
    public discount?: number,
    public taxes?: number,
    public isTaxPercent?: number,
    public orders?: IOrders
  ) {}
}
