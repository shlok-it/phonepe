var capacitorPhonepe = (function (exports, core) {
    'use strict';

    const Phonepe = core.registerPlugin('Phonepe', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.PhonepeWeb()),
    });

    class PhonepeWeb extends core.WebPlugin {
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

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        PhonepeWeb: PhonepeWeb
    });

    exports.Phonepe = Phonepe;

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
