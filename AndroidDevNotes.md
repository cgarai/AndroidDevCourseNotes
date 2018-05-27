
**Android Developer class**



# ***Lesson 2***


An activity is a single focused thing that the user can do. Activities are responsible for creating the window that your application uses to draw and receive events from the system. Activities are written in Java, extending from the Activity class.

An activity creates views to show the user information, and to let the user interact with the activity. Views are a class in the Android UI framework. They occupy a rectangular area on the screen and are responsible for drawing and handling events. An activity determines what views to create (and where to put them), by reading an XML layout file. These XML files are stored in the res folder inside the folder labeled layouts.

Type of View :  UI components
                Container Views

## Questions:

1.  When are the views in the emulator supposed to change?  XML layout seems immediate, not so  sure about Java
2.  Diff between the layout emulator and the simulator?


# ***Lesson 3  Connect to the Internet***

**Exercise T02.03**

## **Menus**
   1. Create a string in the strings.xml file in resources dir
   2. Create a menu resource:  Rt Clk "res" > New > And. Res Dir.> resource type > Menus
   3. Create menu file main.xml:  RtClk "menu" > New > Menu Res. File > File Name = "main"
   4. Create menu item:

### Code snippet
```XML
<item
    android:title="@string/search"
    android:orderInCategory="1"
    android:id="@+id/action_search"
    app:showAsAction="ifRoom" />
```       
app:showAsAction required Alt-Enter to convert it to something??!!


   4. Two @Override sections are added to MainActivity java file

```java
@Override   /*  Displays the Menu */
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return super.onCreateOptionsMenu(menu);
}



@Override  /* Creates a Toast popup message */
public boolean onOptionsItemSelected(MenuItem item) {
    int menuItemThatWasSelected = item.getItemId();
    if(menuItemThatWasSelected == R.id.action_search){
        Context context = MainActivity.this;
        String message = "Search Clicked";
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    return super.onOptionsItemSelected(item);
}
```

## Connecting to the Internet

The first step is to set permissions to the AndroidManifest.xml file:
```xml
<uses-permission android:name="android.permission.INTERNET" />
```


# ***Lesson 4 RecyclerView***

# ***Lesson 5 Intents***
**Exercise T02.05**
**Questions:**

   1.  The (URL... urls) syntax = Variable length arguments.

```Java
// TODO (4) Create a new GithubQueryTask and call its execute method, passing in the url to query
new GithubQueryTask().execute(githubSearchUrl);

   // TODO STARTED(1) Create a class called GithubQueryTask that extends AsyncTask<URL, Void, String>
public class GithubQueryTask extends AsyncTask<URL, Void, String>{
   // TODO STARTED(2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
     @Override
      protected String doInBackground(URL... urls) {
         URL searchUrl = urls[0];
         String githubSearchResults = null;
         try {
             githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);

         } catch (IOException e) {
             e.printStackTrace();
         }
         return githubSearchResults;
     }
// TODO STARTED(3) Override onPostExecute to display the results in the TextView
     @Override
     protected void onPostExecute(String githubSearchResults){
         if(githubSearchResults != null && !githubSearchResults.equals("")){
             mSearchResultsTextView.setText(githubSearchResults);
         }
     }
}
```
# ***Lesson T03.07  Recycler View.***
 See this link in forum:  https://discussions.udacity.com/t/lesson-4-recycler-view-notes/533863/9?u=27ed476db8ea6909f270

# ***Lesson 6 Lifecycle***


# ***Lesson 7 Preferences***

Fragments allow you to have multiple activities on a screen.  Since only one Activity is allowed, you have to use Fragments t make it happen.

# ***Lesson 8 Databases***

Databases start with a contract class which lays out the database:

```java
package com.example.android.waitlist.data;
import android.provider.BaseColumns;
    public class WaitlistContract {
        public static final class WaitlistEntry implements BaseColumns {
            public static final String TABLE_NAME = "waitlist";
            public static final String COLUMN_GUEST_NAME = "guestName";
            public static final String COLUMN_PARTY_SIZE = "partySize";
            public static final String COLUMN_TIMESTAMP = "timestamp";
        }
}
```
The Waitlist app scrolls through a list of data, so a RecyclerView is used.


*ADB tool for exploring data bases*
Adding .../sdk/platform-tools directory to the system path allows using
adb in a gitbash or other shell.  adb will connect with the emulator and then by
cd /data/data databases can be looked at with sqlite3.  To create a database do the following:
mkdir com.example.mydbexample
mkdir databases
cd database
sqlite3 ./mydatabase.db   // this starts sqlite.  Then use sqlite commands:
```sql
create table contacts (_id integer primary key autoincrement, name text, address text,
   phone text);
```

# ***Lesson 9 Content Providers

# ***Lesson 10 Building a Content Provider***
## Questions:
   1. What is being passed in mContext in th following line:

   ```Java
   String  highAndLowTemp = SunshineWeatherUtils.formatHighLows(mContext,hiTemp,lowTemp);
// this line is about 118 in S09.04 in onBindViewHolder


The provider has to be declared in the manifest.

```xml
   <Provider
      android:authorities="@string/content_authority" //the package name from strings.xml
      android:name=".data.TaskContentProvider" // The class location
      android:exported="false"/>  // true if Provider is available to other apps.
```
The Provider code is in the directory .data.  Typically a class called MySomethingProvider
The provider class extends the Content Provider

The public @Override methods in a provider include:
   onCreate()
   query()
   insert()
   update()
   delete()
   getType()

A private method called buildUriMatcher() uses UriMatcher class to create id coded strings to match Uris.  
``` Java
public static final int CODE_WEATHER = 100; //The whole table, starts on even 100s
   public static final int CODE_WEATHER_WITH_DATE = 101; // id code for single record id
   private static final UriMatcher sUriMatcher = buildUriMatcher();
   WeatherDbHelper mOpenHelper; // instantiates sUriMatcher

   public static UriMatcher buildUriMatcher(){
      UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

      uriMatcher.addURI(WeatherContract.CONTENT_AUTHORITY,
               WeatherContract.PATH_WEATHER, CODE_WEATHER);

      uriMatcher.addURI(WeatherContract.CONTENT_AUTHORITY,
               WeatherContract.PATH_WEATHER, CODE_WEATHER_WITH_DATE);
   }
```

# ***Lesson 11 Background Tasks***

# ***Lesson 12 Completing***


## Styles:  values/styles.
```XML
    <style name="folderStyle">
        <item name="android:layout_width">match_parent</item>
        <item name="android:layout_height">wrap_content</item>
        <item name="android:textColor">@android:color/black</item>
    </style>

    <style name="inboxStyle" parent="folderStyle">
        <item name="android:textStyle">bold</item>
    </style>
```
## Selectors
Create an file in res/drawable, make sure root: selector.
So in the file "touch_selector.xml":
```xml   
<selector>
   <item android:drawable="@color/activated"
      android:state_activated="true"
      android:state_focused="false"/>
</selector>
```
Then in the xml layout file set the background to
```XML
<ConstraintLayout
   android:background="@drawable/touch_selector"
   >
```
## Resource Qualifiers
Create a "Smallest Screen Width" qualified dimens.xml by rt-clk values/New/values resource file  and select smallest width qualifier.
The same can be done for landscape layouts as well

Any resource can have a multiple qualifiers that can be triggered by screen width, landscape
mode and many other things I know nothing about!

# ***Lesson 13 Polishing the UI***

## Data Binding, Lesson 13.12
In build.gradle (app) inside android section set
   dataBinding.enabled = true;

Then in the layout.xml file for the activity, make <layout></layout> the root tag

# ***Android Studio***


## Fail to create new Activity using New/Activity.
Solved by deleting C:/Tmp directory

## Favorite AS Shortcuts

## Resoures not updating
Cache is corrupt.  /File/Invalidate Caches-Restart

Ctrl-C copy line
Ctrl-D duplicate line
Ctrl-L  find Context

Ctrl F7 Find usages in file
Alt F7 Find usages in project

Ctrl-Q info on class
Ctrl-W  expand selection

Shft-Enter new line (without having to position at end of line)
