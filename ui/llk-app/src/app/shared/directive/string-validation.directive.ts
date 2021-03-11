import { Directive, HostListener, ElementRef, Input } from '@angular/core';
@Directive({
  selector: '[string-validation]'
})
export class StringValidationDirective {

  regexStr = '^[a-zA-Z0-9,_. -]*$';
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

      this.el.nativeElement.value = this.el.nativeElement.value.replace(/[^a-zA-Z0-9,_. -]/g, '');
      event.preventDefault();

    }, 100)
  }

}