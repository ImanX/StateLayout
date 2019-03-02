# StateLayout
A ViewGroup for Simple Handle Diff State on Android
This help you show and change View bassed your app state


`StateLayout` has four state for handle:

* Failure : when failure happen
* Empty: when empty happen
* Loading : when loading happen
* Normal : when you will show content.


# Shots of diff state

![screenshot](https://github.com/ImanX/StateLayout/blob/master/s_load.jpg?raw=true)
![screenshot](https://github.com/ImanX/StateLayout/blob/master/s_empty.jpg?raw=true)
![screenshot](https://github.com/ImanX/StateLayout/blob/master/s_failure.jpg?raw=true)
![screenshot](https://github.com/ImanX/StateLayout/blob/master/s_normal.jpg?raw=true)


# Getting start
You can import this library by `Gradle`
```
implementation 'com.github.imanx:statelayout:0.0.6'
```

# Usage

You should create your desire layouts for `empty`, `failure`, `loading` statuses and put them all in to `StateLayout` xml file.

```xml

<com.github.imanx.StateLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:id="@+id/state_view"
        app:statelayout_empty_view="@layout/empty"  <
        app:statelayout_loading_view="@layout/loading"
        app:statelayout_failure_view="@layout/failure"
        app:statelayout_default_view="loading_state"
        app:statelayout_has_fade="true"
        app:statelayout_fade_duration="500"
        android:layout_height="match_parent"
        tools:context=".MainActivity">


    <LinearLayout android:layout_width="match_parent"
                  android:orientation="vertical"
                  android:gravity="center"
                  android:layout_height="match_parent">

        <ImageView android:layout_width="200dp" android:src="@drawable/ic_info_black_48dp"
                   android:layout_height="200dp"/>


        <ListView android:layout_width="match_parent"
                  android:id="@+id/list"
                  android:divider="@color/colorAccent"
                  android:dividerHeight="1dp"
                  android:layout_height="match_parent">

        </ListView>

    </LinearLayout>


</com.github.imanx.StateLayout>

```

## State enums:
-  Loading
-  Failure
-  Empty
-  Normal

return state view
```Kotlin
getStateView(State state)
```

change state view 
```Kotlin
setState(State state)
``` 

