import { Moment } from 'moment';
import { IOrders } from 'app/shared/model/orders.model';

export interface IPayments {
  id?: number;
  gatewayId?: string;
  paymentDate?: Moment;
  bankTxn?: string;
  txnToken?: string;
  responseTimestamp?: Moment;
  checksum?: string;
  txnAmount?: number;
  bankName?: string;
  responseCode?: string;
  responseMessage?: string;
  result?: string;
  email?: string;
  orders?: IOrders;
}

export class Payments implements IPayments {
  constructor(
    public id?: number,
    public gatewayId?: string,
    public paymentDate?: Moment,
    public bankTxn?: string,
    public txnToken?: string,
    public responseTimestamp?: Moment,
    public checksum?: string,
    public txnAmount?: number,
    public bankName?: string,
    public responseCode?: string,
    public responseMessage?: string,
    public result?: string,
    public email?: string,
    public orders?: IOrders
  ) {}
}
