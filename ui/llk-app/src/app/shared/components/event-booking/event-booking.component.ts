import { Component, forwardRef, OnDestroy, ChangeDetectionStrategy, Inject, HostListener, OnInit } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, FormBuilder, FormGroup, Validators, FormControl, NG_VALIDATORS } from '@angular/forms';
import { Subscription } from 'rxjs';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';


@Component({
  selector: 'event-booking',
  templateUrl: './event-booking.component.html',
  styleUrls: ['./event-booking.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => EventBookingComponent),
      multi: true
    },
    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => EventBookingComponent),
      multi: true,
    }
  ],
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class EventBookingComponent implements ControlValueAccessor, OnDestroy, OnInit {
  writeValue(obj: any): void {
    throw new Error("Method not implemented.");
  }
  setDisabledState?(isDisabled: boolean): void {
    throw new Error("Method not implemented.");
  }
  form: FormGroup;
  subscriptions: Subscription[] = []; 

 
  constructor(private formBuilder: FormBuilder, public dialogRef: MatDialogRef<EventBookingComponent>,
    @Inject(MAT_DIALOG_DATA) public data) {
  }
  
  ngOnInit() {
   // this.form.setValue(this.data);
    console.log(this.data);
  }
  ngOnDestroy() {
    this.subscriptions.forEach(s => s.unsubscribe());
  }

  onChange: any = () => { };
  onTouched: any = () => { };

  registerOnChange(fn) {
    this.onChange = fn;
  }  

  registerOnTouched(fn) {
    this.onTouched = fn;
  }

  validate(_: FormControl) {
    return this.form.valid ? null : { profile: { valid: false, }, };
  }
}
