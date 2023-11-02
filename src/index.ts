import { registerPlugin } from '@capacitor/core';

import type { PhonepePlugin } from './definitions';

const Phonepe = registerPlugin<PhonepePlugin>('Phonepe', {
  web: () => import('./web').then(m => new m.PhonepeWeb()),
});

export * from './definitions';
export { Phonepe };
