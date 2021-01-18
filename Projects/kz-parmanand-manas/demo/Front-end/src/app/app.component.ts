import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title1: string;
  title2: string;

  constructor() {
    this.title1 = 'Kwanzoo-Demo-Project';
    this.title2 = '- Parmanand and Manas'
  }
}