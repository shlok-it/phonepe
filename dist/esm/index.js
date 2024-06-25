import { registerPlugin } from '@capacitor/core';
const Phonepe = registerPlugin('Phonepe', {
    web: () => import('./web').then(m => new m.PhonepeWeb()),
});
export * from './definitions';
export { Phonepe };
//# sourceMappingURL=index.js.map