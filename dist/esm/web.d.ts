import { WebPlugin } from '@capacitor/core';
import type { PhonepePlugin } from './definitions';
export declare class PhonepeWeb extends WebPlugin implements PhonepePlugin {
    init(options: {
        environment: string;
        merchantId: string;
        appId: string;
        enableLogging: boolean;
    }): Promise<Record<string, any>>;
    startPGTransaction(options: {
        body: string;
        checksum: string;
        apiEndPoint: string;
        headers: any;
        packageName: string | null;
        callBackURL: string | null;
    }): Promise<Record<string, any>>;
    startContainerTransaction(options: {
        body: string;
        checksum: string;
        apiEndPoint: string;
        headers: any;
        callBackURL: string | null;
    }): Promise<Record<string, any>>;
    isPhonePeInstalled(): Promise<Record<string, boolean>>;
    isPaytmInstalled(): Promise<Record<string, boolean>>;
    isGpayInstalled(): Promise<Record<string, boolean>>;
    getPackageSignatureForAndroid(): Promise<Record<string, string>>;
    getUpiAppsForAndroid(): Promise<Record<string, string>>;
}
