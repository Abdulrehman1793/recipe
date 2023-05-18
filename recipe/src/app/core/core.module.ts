import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { HeroComponent } from './hero/hero.component';
import { BodyComponent } from './body/body.component';

@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    HeroComponent,
    BodyComponent,
  ],
  imports: [CommonModule],
  exports: [HeaderComponent, BodyComponent],
})
export class CoreModule {}
