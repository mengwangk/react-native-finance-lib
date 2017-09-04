## React Native Android Finance Library
React Native Android native module used by myInvestor.

## Installation
You can install it as a library in your react native project.

1. Do `npm install --save git+https://github.com/mengwangk/react-native-finance-lib.git` in your main project.
2. Link the library:
    * Add the following to `android/settings.gradle`:
        ```
        include ':react-native-finance-lib'
        project(':react-native-finance-lib').projectDir = new File(settingsDir, '../node_modules/react-native-finance-lib/android')
        ```

    * Add the following to `android/app/build.gradle`:
        ```xml
        ...

        dependencies {
            ...
            compile project(':react-native-finance-lib')
        }
        ```
    * Add the following to `android/app/src/main/java/**/MainApplication.java`:
        ```java
        package com.myinvestor;

        import com.myinvestor.finance.MyInvestorFinancePackage;  // add this for react-native-finance-lib

        public class MainApplication extends Application implements ReactApplication {

            @Override
            protected List<ReactPackage> getPackages() {
                return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    new MyInvestorFinancePackage()     // add this for react-native-finance-lib
                );
            }
        }
        ```
3. Simply `import/require` it by the name defined in your library's `package.json`:

    ```javascript
    import MyInvestorFinance from 'react-native-finance-lib'
    var response = await MyInvestorFinance.getStockPrice("6742.KL")
    ```

4. Using redux-saga:

    ```javascript
    import MyInvestorFinance from 'react-native-finance-lib'
    const response = yield call(MyInvestorFinance.getStockPrice, "6742.KL");
    ```


