import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IDocDetails, DocDetails } from 'app/shared/model/doc-details.model';
import { DocDetailsService } from './doc-details.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products/products.service';
import { IQuotes } from 'app/shared/model/quotes.model';
import { QuotesService } from 'app/entities/quotes/quotes.service';
import { IInvoices } from 'app/shared/model/invoices.model';
import { InvoicesService } from 'app/entities/invoices/invoices.service';

type SelectableEntity = IProducts | IQuotes | IInvoices;

@Component({
  selector: 'jhi-doc-details-update',
  templateUrl: './doc-details-update.component.html',
})
export class DocDetailsUpdateComponent implements OnInit {
  isSaving = false;
  products: IProducts[] = [];
  quotes: IQuotes[] = [];
  invoices: IInvoices[] = [];

  editForm = this.fb.group({
    id: [],
    description: [],
    qty: [null, [Validators.required]],
    price: [null, [Validators.required]],
    discount: [],
    taxes: [],
    isTaxPercent: [],
    products: [],
    quotes: [],
    invoices: [],
  });

  constructor(
    protected docDetailsService: DocDetailsService,
    protected productsService: ProductsService,
    protected quotesService: QuotesService,
    protected invoicesService: InvoicesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ docDetails }) => {
      this.updateForm(docDetails);

      this.productsService
        .query({ filter: 'docdetails-is-null' })
        .pipe(
          map((res: HttpResponse<IProducts[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IProducts[]) => {
          if (!docDetails.products || !docDetails.products.id) {
            this.products = resBody;
          } else {
            this.productsService
              .find(docDetails.products.id)
              .pipe(
                map((subRes: HttpResponse<IProducts>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IProducts[]) => (this.products = concatRes));
          }
        });

      this.quotesService.query().subscribe((res: HttpResponse<IQuotes[]>) => (this.quotes = res.body || []));

      this.invoicesService.query().subscribe((res: HttpResponse<IInvoices[]>) => (this.invoices = res.body || []));
    });
  }

  updateForm(docDetails: IDocDetails): void {
    this.editForm.patchValue({
      id: docDetails.id,
      description: docDetails.description,
      qty: docDetails.qty,
      price: docDetails.price,
      discount: docDetails.discount,
      taxes: docDetails.taxes,
      isTaxPercent: docDetails.isTaxPercent,
      products: docDetails.products,
      quotes: docDetails.quotes,
      invoices: docDetails.invoices,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const docDetails = this.createFromForm();
    if (docDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.docDetailsService.update(docDetails));
    } else {
      this.subscribeToSaveResponse(this.docDetailsService.create(docDetails));
    }
  }

  private createFromForm(): IDocDetails {
    return {
      ...new DocDetails(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      qty: this.editForm.get(['qty'])!.value,
      price: this.editForm.get(['price'])!.value,
      discount: this.editForm.get(['discount'])!.value,
      taxes: this.editForm.get(['taxes'])!.value,
      isTaxPercent: this.editForm.get(['isTaxPercent'])!.value,
      products: this.editForm.get(['products'])!.value,
      quotes: this.editForm.get(['quotes'])!.value,
      invoices: this.editForm.get(['invoices'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocDetails>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
