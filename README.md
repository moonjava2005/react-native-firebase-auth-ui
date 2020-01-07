
# react-native-firebase-auth-ui

## Getting started

`$ npm install react-native-firebase-auth-ui --save`

### Mostly automatic installation

`$ react-native link react-native-firebase-auth-ui`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-firebase-auth-ui` and add `RNFirebaseAuthUi.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNFirebaseAuthUi.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import info.moonjava.RNFirebaseAuthUiPackage;` to the imports at the top of the file
  - Add `new RNFirebaseAuthUiPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-firebase-auth-ui'
  	project(':react-native-firebase-auth-ui').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-firebase-auth-ui/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-firebase-auth-ui')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNFirebaseAuthUi.sln` in `node_modules/react-native-firebase-auth-ui/windows/RNFirebaseAuthUi.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Firebase.Auth.Ui.RNFirebaseAuthUi;` to the usings at the top of the file
  - Add `new RNFirebaseAuthUiPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNFirebaseAuthUi from 'react-native-firebase-auth-ui';

// TODO: What to do with the module?
RNFirebaseAuthUi;
```
  