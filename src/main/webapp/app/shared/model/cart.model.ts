import { ICoupons } from 'app/shared/model/coupons.model';
import { IProducts } from 'app/shared/model/products.model';

export interface ICart {
  id?: number;
  coupons?: ICoupons;
  products?: IProducts[];
}

export class Cart implements ICart {
  constructor(public id?: number, public coupons?: ICoupons, public products?: IProducts[]) {}
}
