package phonepe.shlokitsolution.com;

import static phonepe.shlokitsolution.com.GlobalConstants.Argument.API_END_POINT;
import static phonepe.shlokitsolution.com.GlobalConstants.Argument.APP_ID;
import static phonepe.shlokitsolution.com.GlobalConstants.Argument.BODY;
import static phonepe.shlokitsolution.com.GlobalConstants.Argument.CHECKSUM;
import static phonepe.shlokitsolution.com.GlobalConstants.Argument.ENABLE_LOGS;
import static phonepe.shlokitsolution.com.GlobalConstants.Argument.ENVIRONMENT;
import static phonepe.shlokitsolution.com.GlobalConstants.Argument.MERCHANT_ID;
import static phonepe.shlokitsolution.com.GlobalConstants.Environment.UAT;
import static phonepe.shlokitsolution.com.GlobalConstants.Environment.UAT_SIMULATOR;
import static phonepe.shlokitsolution.com.GlobalConstants.PHONEPE_PAYMENT_SDK;
import static phonepe.shlokitsolution.com.GlobalConstants.Response.SUCCESS;
import static phonepe.shlokitsolution.com.GlobalConstants.Response.FAILURE;
import static phonepe.shlokitsolution.com.GlobalConstants.Response.ERROR;
import static phonepe.shlokitsolution.com.GlobalConstants.Response.TRANSACTION_INTERRUPTED;
import static phonepe.shlokitsolution.com.GlobalConstants.RequestCode.B2B_PG;
import static phonepe.shlokitsolution.com.GlobalConstants.RequestCode.CONTAINER;
import static phonepe.shlokitsolution.com.GlobalConstants.Response.APPLICATION_NAME;
import static phonepe.shlokitsolution.com.GlobalConstants.Response.PACKAGE_NAME;
import static phonepe.shlokitsolution.com.GlobalConstants.Response.STATUS;
import static phonepe.shlokitsolution.com.GlobalConstants.Response.VERSION;
import static phonepe.shlokitsolution.com.LogUtil.logInfo;
import static phonepe.shlokitsolution.com.LogUtil.enableLogs;
import static phonepe.shlokitsolution.com.DataUtil.handleException;
import static phonepe.shlokitsolution.com.DataUtil.convertResultToString;

import android.app.Activity;
import android.content.Intent;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.phonepe.intent.sdk.api.B2BPGRequest;
import com.phonepe.intent.sdk.api.B2BPGRequestBuilder;
import com.phonepe.intent.sdk.api.PhonePe;
import com.phonepe.intent.sdk.api.UPIApplicationInfo;
import com.phonepe.intent.sdk.api.models.SDKType;
import com.phonepe.intent.sdk.api.models.PhonePeEnvironment;
import org.json.JSONArray;
import com.phonepe.intent.sdk.api.TransactionRequest;
import com.phonepe.intent.sdk.api.TransactionRequestBuilder;
import java.util.Objects;

@CapacitorPlugin(name = PHONEPE_PAYMENT_SDK, requestCodes = {B2B_PG, CONTAINER})
public class PhonepePlugin extends Plugin {

    public PhonepePlugin() {
        PhonePe.setAdditionalInfo(SDKType.IONIC);
    }

    @PluginMethod
    public void isPhonePeInstalled(PluginCall call) {
        logInfo("started isPhonePeInstalled");
        try {
            JSObject result = new JSObject();
            result.put(STATUS, PhonePe.isPhonePeAppInstalled(true));
            call.resolve(result);
        } catch (Exception ex) {
            handleException(ex, call);
        }
    }

    @PluginMethod
    public void isPaytmInstalled(PluginCall call) {
        logInfo("started isPaytmInstalled");
        try {
            JSObject result = new JSObject();
            result.put(STATUS, PhonePe.isPayTMAppInstalled(true));
            call.resolve(result);
        } catch (Exception ex) {
            handleException(ex, call);
        }
    }

    @PluginMethod
    public void isGpayInstalled(PluginCall call) {
        logInfo("started isGpayInstalled");
        try {
            JSObject result = new JSObject();
            result.put(STATUS, PhonePe.isGooglePayAppInstalled(true));
            call.resolve(result);
        } catch (Exception ex) {
            handleException(ex, call);
        }
    }

    @PluginMethod
    public void getPackageSignatureForAndroid(PluginCall call) {
        logInfo("started getPackageSignatureForAndroid");
        try {
            JSObject result = new JSObject();
            result.put(STATUS, PhonePe.getPackageSignature());
            call.resolve(result);
        } catch (Exception ex) {
            handleException(ex, call);
        }
    }

    @PluginMethod
    public void getUpiAppsForAndroid(PluginCall call) {
        logInfo("started getUpiAppsForAndroid");
        try {
            JSONArray jsonArray = new JSONArray();
            for (UPIApplicationInfo app : PhonePe.getUpiApps()) {
                JSObject appJson = new JSObject();
                appJson.put(PACKAGE_NAME, app.getPackageName());
                appJson.put(APPLICATION_NAME, app.getApplicationName());
                appJson.put(VERSION, String.valueOf(app.getVersion()));
                jsonArray.put(appJson);
            }
            JSObject result = new JSObject();
            result.put(STATUS, jsonArray.toString());
            call.resolve(result);
        } catch (Exception ex) {
            handleException(ex, call);
        }
    }

    @PluginMethod
    public void init(PluginCall call) {
        logInfo("started init");
        try {
            enableLogs = call.getData().getBoolean(ENABLE_LOGS);
            String environment = call.getData().getString(ENVIRONMENT);
            String merchantId = call.getData().getString(MERCHANT_ID);
            if (environment == null || environment.isEmpty()
                    || merchantId == null || merchantId.isEmpty()
            )
                throw new IllegalArgumentException("Invalid environment or merchantId!");

            PhonePeEnvironment ppEnvironment;
            if (Objects.equals(environment, UAT))
                ppEnvironment = PhonePeEnvironment.UAT;
            else if (Objects.equals(environment, UAT_SIMULATOR))
                ppEnvironment = PhonePeEnvironment.UAT_SIMULATOR;
            else ppEnvironment = PhonePeEnvironment.RELEASE;

            if (ppEnvironment.equals(PhonePeEnvironment.RELEASE) && Objects.requireNonNull(call.getData().getString(APP_ID)).isEmpty())
                throw new IllegalArgumentException("Invalid appId!");
            JSObject result = new JSObject();
            result.put(STATUS, PhonePe.init(getContext(),
                    ppEnvironment,
                    merchantId,
                    call.getData().getString(APP_ID)
            ));
            call.resolve(result);
        } catch (Exception ex) {
            handleException(ex, call);
        }
    }

    @PluginMethod
    public void startPGTransaction(PluginCall call) {
        logInfo("start startPGTransaction");
        try {
            saveCall(call);
            String apiEndPoint = call.getData().getString(API_END_POINT);
            String checksum = call.getData().getString(CHECKSUM);
            String requestBody = call.getData().getString(BODY);
            if (apiEndPoint == null || apiEndPoint.isEmpty()
                    || checksum == null || checksum.isEmpty()
                    || requestBody == null || requestBody.isEmpty()
            )
                throw new IllegalArgumentException("Invalid checksum or apiEndpoint or body!");

            B2BPGRequestBuilder b2BPGRequestBuilder = new B2BPGRequestBuilder();
            B2BPGRequest b2BPGRequest = b2BPGRequestBuilder.setChecksum(checksum)
                    .setUrl(apiEndPoint)
                    .setData(requestBody)
                    .build();

            startActivityForResult(
                    call,
                    PhonePe.getImplicitIntent(
                            getContext(),
                            b2BPGRequest,
                            call.getData().getString(PACKAGE_NAME)
                    ),
                    B2B_PG
            );
        } catch (Exception ex) {
            handleException(ex, call);
        }
    }

    @PluginMethod
    public void startContainerTransaction(PluginCall call) {
        logInfo("start startContainerTransaction");
        try {
            saveCall(call);
            String apiEndPoint = call.getData().getString(API_END_POINT);
            String checksum = call.getData().getString(CHECKSUM);
            String requestBody = call.getData().getString(BODY);
            if (apiEndPoint == null || apiEndPoint.isEmpty()
                    || checksum == null || checksum.isEmpty()
                    || requestBody == null || requestBody.isEmpty()
            )
                throw new IllegalArgumentException("Invalid checksum or apiEndpoint or body!");

            TransactionRequestBuilder transactionRequestBuilder = new TransactionRequestBuilder();
            TransactionRequest transactionRequest = transactionRequestBuilder.setChecksum(checksum)
                    .setUrl(apiEndPoint)
                    .setData(requestBody)
                    .build();

            startActivityForResult(
                    call, PhonePe.getTransactionIntent(transactionRequest), CONTAINER
            );
        } catch (Exception ex) {
            handleException(ex, call);
        }
    }

    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.handleOnActivityResult(requestCode, resultCode, data);
        try {
            logInfo("handleOnActivityResult: requestCode:" + requestCode + " resultCode:" + resultCode + "data:" + convertResultToString(data));
            if (requestCode == B2B_PG || requestCode == CONTAINER && getSavedCall() != null) {
                JSObject result = new JSObject();
                if (resultCode != Activity.RESULT_CANCELED) {
                    result.put(STATUS, SUCCESS);
                } else {
                    result.put(STATUS, FAILURE);
                    result.put(ERROR, TRANSACTION_INTERRUPTED);
                }
                getSavedCall().resolve(result);
            }
        } catch (Exception ex) {
            logInfo("Exception in handleOnActivityResult:" + ex.getLocalizedMessage());
        }
    }
}
