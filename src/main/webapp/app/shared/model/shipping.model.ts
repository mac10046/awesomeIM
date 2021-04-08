import { Moment } from 'moment';
import { IShippers } from 'app/shared/model/shippers.model';
import { IAddress } from 'app/shared/model/address.model';
import { IProducts } from 'app/shared/model/products.model';
import { IOrders } from 'app/shared/model/orders.model';

export interface IShipping {
  id?: number;
  shipDate?: Moment;
  trackingId?: string;
  shippers?: IShippers;
  address?: IAddress;
  products?: IProducts[];
  orders?: IOrders;
}

export class Shipping implements IShipping {
  constructor(
    public id?: number,
    public shipDate?: Moment,
    public trackingId?: string,
    public shippers?: IShippers,
    public address?: IAddress,
    public products?: IProducts[],
    public orders?: IOrders
  ) {}
}
