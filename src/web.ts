import { WebPlugin } from '@capacitor/core';

import type { PhonepePlugin } from './definitions';

export class PhonepeWeb extends WebPlugin implements PhonepePlugin {
  async init(options: { environment: string; merchantId: string; appId: string; enableLogging: boolean; }): Promise<Record<string, any>> {
    console.log('ECHO', options);
    return options;
  }
  async startPGTransaction(options: { body: string; checksum: string; apiEndPoint: string; headers: any; packageName: string | null; callBackURL: string | null; }): Promise<Record<string, any>> {
    console.log('ECHO', options);
    return options;
  }
  async startContainerTransaction(options: { body: string; checksum: string; apiEndPoint: string; headers: any; callBackURL: string | null; }): Promise<Record<string, any>> {
    console.log('ECHO', options);
    return options;
  }
  async isPhonePeInstalled(): Promise<Record<string, boolean>> {
    throw new Error('Method not implemented.');
  }
  async isPaytmInstalled(): Promise<Record<string, boolean>> {
    throw new Error('Method not implemented.');
  }
  async isGpayInstalled(): Promise<Record<string, boolean>> {
    throw new Error('Method not implemented.');
  }
  async getPackageSignatureForAndroid(): Promise<Record<string, string>> {
    throw new Error('Method not implemented.');
  }
  async getUpiAppsForAndroid(): Promise<Record<string, string>> {
    throw new Error('Method not implemented.');
  }

}
