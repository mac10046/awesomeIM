import { IDocDetails } from 'app/shared/model/doc-details.model';
import { ITaxes } from 'app/shared/model/taxes.model';
import { IBusinessDetails } from 'app/shared/model/business-details.model';
import { ICart } from 'app/shared/model/cart.model';
import { IShipping } from 'app/shared/model/shipping.model';

export interface IProducts {
  id?: number;
  name?: string;
  description?: string;
  price?: number;
  weight?: number;
  dimension?: string;
  photo1ContentType?: string;
  photo1?: any;
  photo2ContentType?: string;
  photo2?: any;
  photo3ContentType?: string;
  photo3?: any;
  isPhysicalProduct?: boolean;
  maintainInventory?: boolean;
  docDetails?: IDocDetails;
  taxes?: ITaxes;
  businessDetails?: IBusinessDetails;
  cart?: ICart;
  shipping?: IShipping;
}

export class Products implements IProducts {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public price?: number,
    public weight?: number,
    public dimension?: string,
    public photo1ContentType?: string,
    public photo1?: any,
    public photo2ContentType?: string,
    public photo2?: any,
    public photo3ContentType?: string,
    public photo3?: any,
    public isPhysicalProduct?: boolean,
    public maintainInventory?: boolean,
    public docDetails?: IDocDetails,
    public taxes?: ITaxes,
    public businessDetails?: IBusinessDetails,
    public cart?: ICart,
    public shipping?: IShipping
  ) {
    this.isPhysicalProduct = this.isPhysicalProduct || false;
    this.maintainInventory = this.maintainInventory || false;
  }
}
