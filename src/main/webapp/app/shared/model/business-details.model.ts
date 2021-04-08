import { Moment } from 'moment';
import { IProducts } from 'app/shared/model/products.model';
import { IReviews } from 'app/shared/model/reviews.model';
import { IQuotes } from 'app/shared/model/quotes.model';
import { IInvoices } from 'app/shared/model/invoices.model';
import { BusinessType } from 'app/shared/model/enumerations/business-type.model';

export interface IBusinessDetails {
  id?: number;
  businessName?: string;
  registeredAddress?: string;
  description?: string;
  inceptionDate?: Moment;
  gstin?: string;
  category?: string;
  subCategory?: string;
  email?: string;
  contactNumber?: string;
  managingPersonName?: string;
  logoContentType?: string;
  logo?: any;
  managingPersonImageContentType?: string;
  managingPersonImage?: any;
  businessType?: BusinessType;
  upi?: string;
  bankName?: string;
  ifscCode?: string;
  branchName?: string;
  products?: IProducts[];
  reviews?: IReviews[];
  quotes?: IQuotes;
  invoices?: IInvoices;
}

export class BusinessDetails implements IBusinessDetails {
  constructor(
    public id?: number,
    public businessName?: string,
    public registeredAddress?: string,
    public description?: string,
    public inceptionDate?: Moment,
    public gstin?: string,
    public category?: string,
    public subCategory?: string,
    public email?: string,
    public contactNumber?: string,
    public managingPersonName?: string,
    public logoContentType?: string,
    public logo?: any,
    public managingPersonImageContentType?: string,
    public managingPersonImage?: any,
    public businessType?: BusinessType,
    public upi?: string,
    public bankName?: string,
    public ifscCode?: string,
    public branchName?: string,
    public products?: IProducts[],
    public reviews?: IReviews[],
    public quotes?: IQuotes,
    public invoices?: IInvoices
  ) {}
}
