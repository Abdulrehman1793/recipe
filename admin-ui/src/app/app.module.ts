import { NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CoreModule } from './core/core.module';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { appReducer } from './store/app.state';
import { DefaultDataServiceConfig, EntityDataModule } from '@ngrx/data';
import { HttpClientModule } from '@angular/common/http';
import { entityConfig } from './store';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    CoreModule,
    HttpClientModule,
    StoreModule.forRoot(appReducer, {}),
    EffectsModule.forRoot([]),
    StoreDevtoolsModule.instrument({ maxAge: 25, trace: true, traceLimit: 75 }),
    EntityDataModule.forRoot(entityConfig),
  ],
  providers: [
    // Configure the DefaultDataServiceConfig provider
    {
      provide: DefaultDataServiceConfig,
      useValue: { root: 'http://localhost:8080/api/v2' },
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
