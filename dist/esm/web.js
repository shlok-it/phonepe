import { WebPlugin } from '@capacitor/core';
export class PhonepeWeb extends WebPlugin {
    async init(options) {
        console.log('ECHO', options);
        return options;
    }
    async startPGTransaction(options) {
        console.log('ECHO', options);
        return options;
    }
    async startContainerTransaction(options) {
        console.log('ECHO', options);
        return options;
    }
    async isPhonePeInstalled() {
        throw new Error('Method not implemented.');
    }
    async isPaytmInstalled() {
        throw new Error('Method not implemented.');
    }
    async isGpayInstalled() {
        throw new Error('Method not implemented.');
    }
    async getPackageSignatureForAndroid() {
        throw new Error('Method not implemented.');
    }
    async getUpiAppsForAndroid() {
        throw new Error('Method not implemented.');
    }
}
//# sourceMappingURL=web.js.map