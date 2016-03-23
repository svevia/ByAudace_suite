# How to use RESTAndroid
1. Download the repository
2. Put the package in your project's source directory
3. Make sure to add `<uses-permission android:name="android.permission.INTERNET" />` to your AndroidManifest.xml (Just inside the `manifest` tag)

# How to see if it works
1. To see if it works: In your Android activity, create an instance of PostRequest with the url http://httpbin.org/post and the parameter "Test" with a value of "This is a test".
2. Display the value of the following code: `request.getResponse().getJSONObject().getJSONObject("headers").getString("Test")`
2. Run your activity, it should show "This is a test" where you displayed it.
