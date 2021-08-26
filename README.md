# DMED Test App

This App has one job:
- Display the Title, Description, and Cover Image for Marvel's comic ID "79809", a.k.a. "Strange Academy (2020) #12"

## Loading your Marvel API Keys

This App needs Keys to authenticate with the Marvel API.

1. Set up your Public and Secret keys at https://developer.marvel.com/
2. Create a file called `marvel.properties` at the root of this project
3. Populate the file like so (note the lack of quotes and spaces):
```
marvel.publickey=abc123def456abc123def456abc123def456abc1
marvel.secretkey=def456abc1def456abc1def456abc156abc1de12
```

## Test Examples

[Unit Test, for the OkHttp Interceptor in MarvelService library](src/test/java/com/brianmartone/service/marvel/MarvelHttpInterceptorTest.kt)
[UI Test, for the app's Main Activity](src/androidTest/java/com/brianmartone/comicdisplay/ExampleInstrumentedTest.kt)

## Libraries used

- `ktlint`: For formatting Kotlin files
- `retrofit`: HTTP client used to call Marvel API
- `moshi`: Used to deserialize the above API's json response to Kotlin data classes.
    - `converter-moshi`: Converter factory, to ensure Retrofit uses Moshi for all serialization/deserialization needs
- `picasso`: Used as a 'plug n play' option for downloading images and placing them in ImageViews
- `hilt`: For dependency injection. The big reason I jumped to this: so that I can swap out my HttpClient for UI tests to point to a local webserver
- `mockwebserver`: For UI tests, to host a local webserver on the Android device (so I didn't need to use the real Marvel API)
- `mockk`: For Unit tests, my mocking framework of choice
- `junit`: For testing running/management. Didn't mess with the classic.

## Areas for Improvement

**Marvel API keys are easy to get if you unzip the .apk**
Hooray, the keys are not being stored in source control!
Boo, the keys are easy to get for anyone with the .apk.
Potential solution: host the keys in the cloud, and allow users to access them after passing some authentication gate. Once the keys are downloaded, use KeyStore to keep them safe/encrypted.

**Tweak ProGuard**
This project is using basic/default ProGuard configurations (including the copypasta ProGuard recommended by Retrofit)
I have not audited that config to see how much/how little this app is being protected.

**Flakey UI Test**
Occasionally (too often) I get an error during UI testing, where the MockWebServer seems to be terminated too early and a Request is interrupted.

**Logging/Telemetry**
Currently this is using `android.util.Log` for all logging. It'd be nice to have more control via a tool like Timber. Additionally, I'm not sending any usage statistics/crashlytics anywhere.

**Localization**
This App only includes English text