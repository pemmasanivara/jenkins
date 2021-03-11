import { Directive, HostListener, ElementRef, Input } from '@angular/core';
@Directive({
  selector: '[name-validation]'
})
export class NameValidationDirective {

  regexStr = '^[a-zA-Z_,. -]*$';
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

      this.el.nativeElement.value = this.el.nativeElement.value.replace(/[^a-zA-Z_,. -]/g, '');
      event.preventDefault();

    }, 100)
  }

}