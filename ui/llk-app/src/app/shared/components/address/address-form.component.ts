import { Component, forwardRef, OnDestroy, ChangeDetectionStrategy, Inject, HostListener, OnInit } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, FormBuilder, FormGroup, Validators, FormControl, NG_VALIDATORS } from '@angular/forms';
import { Subscription } from 'rxjs';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface AddressFormValues {
  siteId: number;
  type: string;
  addressOne: string;
  addressTwo: string;
  city: string;
  state: string;
  country: string;
  zip: string;
}

@Component({
  selector: 'address-form',
  templateUrl: './address-form.component.html',
  styleUrls: ['./address-form.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => AddressFormComponent),
      multi: true
    },
    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => AddressFormComponent),
      multi: true,
    }
  ],
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class AddressFormComponent implements ControlValueAccessor, OnDestroy, OnInit {
  addressTypeList = [{ value: 1, display: "Home" }, { value: 1, display: "Work" }];
  form: FormGroup;
  subscriptions: Subscription[] = [];

  get value(): AddressFormValues {
    return this.form.value;
  }

  set value(value: AddressFormValues) {
    this.form.setValue(value);
    this.onChange(value);
    this.onTouched();
  }

  constructor(private formBuilder: FormBuilder, public dialogRef: MatDialogRef<AddressFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data) {
    this.form = this.formBuilder.group({
      siteId:[],
      type: [],
      addressOne: [],
      addressTwo: [],
      city: [],
      state: [],
      country: [],
      zip: []
    });
    this.subscriptions.push(
      this.form.valueChanges.subscribe(value => {
        this.onChange(value);
        this.onTouched();
      })
    );
  }
  ngOnInit() {
    this.form.setValue(this.data);
  }
  ngOnDestroy() {
    this.subscriptions.forEach(s => s.unsubscribe());
  }

  onChange: any = () => { };
  onTouched: any = () => { };

  registerOnChange(fn) {
    this.onChange = fn;
  }

  writeValue(value) {
    if (value) {
      this.value = value;
    }

    if (value === null) {
      this.form.reset();
    }
  }

  registerOnTouched(fn) {
    this.onTouched = fn;
  }

  validate(_: FormControl) {
    return this.form.valid ? null : { profile: { valid: false, }, };
  }
}
