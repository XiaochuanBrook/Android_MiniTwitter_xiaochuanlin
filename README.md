

Introduction
-------------
This project **MyTwitter** mostly is for practicing MVP and Dragger2.  This project stimulates the process of showing / posting feeds in Recyclerview.

Usage
-------------

**LoginScreen**: screen to sign in, only show when no account sign in
account1: user, password: psw
account2: user2, password: psw2
Actions: login

**MainScreen** : screen to show the feed list, and launch the post screen or logout
Actions: loading data, loading more data when user clicks "load more", item on click action

**PostScreen**: screen to post a feed
Actions: add test, add picture, post

Structure 
-------------

The project is based on MVP pattern. 
For example the main screen's structure will be:

```sequence
MainScreen->Presenter: UI actions
Presenter-->MainScreen: UI updates
Presenter->DataModel: Data request
DataModel-->Presenter: Data update
Note right of DataModel: DataPool
```

Framework
-------------
Dagger 2 is deployed for this project for fast dependency injection.
Butterknife is used for view binding.

Folders
-------------
**com.lin.app** -->
	 **Bean**: for the feedItem bean
	 **di**: setup classes for dagger2
	 **Models**: data models
	 **Presenters**: screen presenters
	 **Ui**: activities, view and adapters
	 **Utils**: utilities
	 **Twitter.java**: main application class

Dependencies
-------------
```
dependencies {
    apt 'com.google.dagger:dagger-compiler:2.0.2'
    compile 'com.google.dagger:dagger:2.0.2'
    compile 'com.android.support:design:25.3.1'
    compile 'com.android.support:recyclerview-v7:25.3.1'
    compile 'com.android.support:cardview-v7:25.3.1'
    compile 'com.squareup.picasso:picasso:2.5.2'
    compile 'com.android.support:appcompat-v7:25.3.1'
    compile 'com.google.code.gson:gson:2.7'
    compile 'com.jakewharton:butterknife:8.5.1'
    apt 'com.jakewharton:butterknife-compiler:8.5.1'
    provided 'org.glassfish:javax.annotation:10.0-b28'
    testCompile 'junit:junit:4.12'
    testCompile 'org.mockito:mockito-core:2.0.90-beta'
}
```
Data
-------------
This project will only handle the data locally to stimulate the feeds loading, rendering and update. The data flow should be handled by **REAL** network process (by retrofit or even just using Okhttp in async) when the data is from remote. 

The data exchange flow is defined in DataPoolUtil. When first time starting the app, the raw data in assets will be loaded to have some feeds data, and then the data will be read and write on sharepreference. On each time new post is being sent, the new item will be attached at the top of the current list and save in sharepreference, so that next time when data is being fetched the new item will be included and shown at the top.

Feeds loading flow:
```flow
st=>start: Start
e=>end
op=>operation: load raw data from res/raw/data.text
cond=>condition: First time loading?
op2=>operation: load sharePreferences data
op3=>operation: update current version cache

st->cond
cond(yes)->op
cond(no)->op2
op->op3
op2->op3
op3->e
```

Feeds posting flow:
```flow
st=>start: Start
e=>end
op=>operation: prepare post item
op2=>operation: attache the post item to the current version list
op3=>operation: save sharePreferences data

st->op
op->op2
op2->op3
op3->e
```

A small trick is made for the partially loading logic, so on each the screen will only load 5 items at the beginning as default, and if user wants to load more every time there will be n + 2 (n  equals the current items count) items will be loaded. 

Unit Test
-------------

Mockito and Junit are used for unit testing, mostly just for making sure presenters handle logics correctly by communicate between view and models. 

Utilities are also tested by Junit. 


TODO & Regrets
-------------
(1) Mocking a data pool actually does't make the project easier because lots useful tools are not used.
(2) Observer pattern and data binding could be used for data model.
(3) Drawer's layout is not hooked up with real actions.
(4) Posting and loading feeds should have time out error callback
(5) Add Ui flows testing by using espresso 