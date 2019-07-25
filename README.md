# React Native eshiply-ipay88

## Getting started

`$ npm install eshiply-ipay88 --save`

### Mostly automatic installation

`$ react-native link eshiply-ipay88`

### Manual installation

#### iOS

1.  In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2.  Go to `node_modules` ➜ `eshiply-ipay88` and add `RNIpay88Sdk.xcodeproj`
3.  In XCode, in the project navigator, select your project. Add `libRNIpay88Sdk.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4.  Run your project (`Cmd+R`)<

#### Android

1.  Open up `android/app/src/main/java/[...]/MainActivity.java`

* Add `import com.ipay88.IPay88Package;` to the imports at the top of the file
* Add `new IPay88Package()` to the list returned by the `getPackages()` method

2.  Append the following lines to `android/settings.gradle`:
    ```
    include ':eshiply-ipay88'
    project(':eshiply-ipay88').projectDir = new File(rootProject.projectDir, 	'../node_modules/eshiply-ipay88/android')
    ```
3.  Insert the following lines inside the dependencies block in `android/app/build.gradle`:
    ```
      compile project(':eshiply-ipay88')
    ```

## Usage

```javascript
import React, { Component } from "react";
import {
  Platform,
  StyleSheet,
  Button,
  Text,
  View,
  Alert,
  ToastAndroid
} from "react-native";
import IPay88, { Pay } from "eshiply-ipay88";

export default class App extends Component {
  successNotify = data => {
    if (Platform.OS === "ios") {
      const {
        transactionId,
        referenceNo,
        amount,
        remark,
        authorizationCode
      } = data;

      Alert.alert("Message", `Payment authcode is ${authorizationCode}`, {
        cancelable: true
      });
    } else {
      ToastAndroid.show(
        `Message: Payment authcode is ${authorizationCode}`,
        ToastAndroid.LONG
      );
    }
  };

  cancelNotify = data => {
    const { transactionId, referenceNo, amount, remark, error } = data;

    if (Platform.OS === "ios") {
      Alert.alert("Message", `${error}`, { cancelable: true });
    } else {
      ToastAndroid.show(`Message: ${error}`, ToastAndroid.LONG);
    }
  };

  failedNotify = data => {
    const { transactionId, referenceNo, amount, remark, error } = data;

    if (Platform.OS === "ios") {
      Alert.alert("Message", `${error}`, { cancelable: true });
    } else {
      ToastAndroid.show(`Message: ${error}`, ToastAndroid.LONG);
    }
  };

  pay = () => {
    try {
      const data = {};
      data.paymentId = "2"; // refer to ipay88 docs
      data.merchantKey = "{{ merchantKey }}";
      data.merchantCode = "{{ merchantCode }}";
      data.referenceNo = "1234565";
      data.amount = "1.00";
      data.currency = "MYR";
      data.productDescription = "Payment";
      data.userName = "test";
      data.userEmail = "test@gmail.com";
      data.userContact = "0123456789";
      data.remark = "me";
      data.utfLang = "UTF-8";
      data.country = "MY";
      data.backendUrl = "http://sample.com";
      const errs = Pay(data);
      if (Object.keys(errs).length > 0) {
        console.log(errs);
      }
    } catch (e) {
      console.log(e);
    }
  };

  render() {
    return (
      <View
        style={{
          flex: 1,
          justifyContent: "center",
          alignItems: "center"
        }}
      >
        <IPay88
          successNotify={this.successNotify}
          failedNotify={this.failedNotify}
          cancelNotify={this.cancelNotify}
        />
        <Button title="Pay" onPress={this.pay} />
      </View>
    );
  }
}
```

### Make Payment

// Refer to ipay88 docs for more info

* paymentId // optional
* merchantKey // required
* merchantCode // required
* referenceNo // required
* amount // required
* currency // required
* productDescription // required
* userName // required
* userEmail // required
* userContact // required
* remark // optional
* utfLang // optional
* country // required
* backendUrl // required

### Success Notify

* transactionId
* referenceNo
* amount
* remark
* authorizationCode

### Failed Notify

* transactionId
* referenceNo
* amount
* remark
* error

### Cancel Notify

* transactionId
* referenceNo
* amount
* remark
* error
