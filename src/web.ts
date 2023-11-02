import { WebPlugin } from '@capacitor/core';

import type { PhonepePlugin } from './definitions';

export class PhonepeWeb extends WebPlugin implements PhonepePlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
