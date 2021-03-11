import { Directive, HostListener, ElementRef, Input } from '@angular/core';
@Directive({
  selector: '[email-validation]'
})
export class EmailValidationDirective {

  regexStr = '^[a-zA-Z0-9@.]*$';
  @Input() isAlphaNumeric: boolean;

  constructor(private el: ElementRef) { }


  @HostListener('keypress', ['$event']) onKeyPress(event) {
    return new RegExp(this.regexStr).test(event.key);
  }

  @HostListener('paste', ['$event']) blockPaste(event: KeyboardEvent) {
    this.validateFields(event);
  }

  validateFields(event) {
    setTimeout(() => {
      this.el.nativeElement.value = this.el.nativeElement.value.replace(/[^a-zA-Z0-9@.]/g, '').replace(/\s/g, '');
      event.preventDefault();
    }, 100)
  }

}