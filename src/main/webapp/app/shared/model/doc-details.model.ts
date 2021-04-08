import { IProducts } from 'app/shared/model/products.model';
import { IQuotes } from 'app/shared/model/quotes.model';
import { IInvoices } from 'app/shared/model/invoices.model';

export interface IDocDetails {
  id?: number;
  description?: string;
  qty?: number;
  price?: number;
  discount?: number;
  taxes?: number;
  isTaxPercent?: number;
  products?: IProducts;
  quotes?: IQuotes;
  invoices?: IInvoices;
}

export class DocDetails implements IDocDetails {
  constructor(
    public id?: number,
    public description?: string,
    public qty?: number,
    public price?: number,
    public discount?: number,
    public taxes?: number,
    public isTaxPercent?: number,
    public products?: IProducts,
    public quotes?: IQuotes,
    public invoices?: IInvoices
  ) {}
}
