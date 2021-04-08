import { IProducts } from 'app/shared/model/products.model';

export interface ITaxes {
  id?: number;
  name?: string;
  isPercent?: boolean;
  figure?: number;
  description?: string;
  products?: IProducts;
}

export class Taxes implements ITaxes {
  constructor(
    public id?: number,
    public name?: string,
    public isPercent?: boolean,
    public figure?: number,
    public description?: string,
    public products?: IProducts
  ) {
    this.isPercent = this.isPercent || false;
  }
}
