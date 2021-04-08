import { Moment } from 'moment';
import { ICart } from 'app/shared/model/cart.model';

export interface ICoupons {
  id?: number;
  name?: string;
  code?: string;
  startDate?: Moment;
  endDate?: Moment;
  cart?: ICart;
}

export class Coupons implements ICoupons {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public cart?: ICart
  ) {}
}
