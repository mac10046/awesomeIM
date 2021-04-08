import { IAddress } from 'app/shared/model/address.model';
import { IShipping } from 'app/shared/model/shipping.model';

export interface IShippers {
  id?: number;
  name?: string;
  contactNumber?: string;
  address?: IAddress;
  shipping?: IShipping;
}

export class Shippers implements IShippers {
  constructor(
    public id?: number,
    public name?: string,
    public contactNumber?: string,
    public address?: IAddress,
    public shipping?: IShipping
  ) {}
}
