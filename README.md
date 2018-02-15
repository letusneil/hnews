# hnews

### Overview

hnews is a Hackernews client powered by [Hackernews official API](https://github.com/HackerNews/API).

### Screenshots

<img src="https://github.com/neilvinas/hnews/blob/master/art/top_stories.png" width="200" height="415"> <img src="https://github.com/neilvinas/hnews/blob/master/art/comments.png" width="200" height="415"> 

### Features

* Top stories, pagination
* Comments (replies, all levels)
* Webview article viewing by clicking on the url

### Technical Approach

The project uses Model-View-Presenter pattern to separate responsibilities among classes. The code is strucuted in a package feature way for Stories and Comments. It also uses a repository class to retrieve data from remote data source. In this way it would be easier to introduce offline handling by adding a local data source. 

### Dependencies

* [Retrofit](http://square.github.io/retrofit/), [OkHttp](https://github.com/square/okhttp)
* [Dagger2](https://github.com/google/dagger)
* [RxJava2](https://github.com/google/dagger)
* [LeakCanary](https://github.com/square/leakcanary)

**Testing**

    ./gradlew testDebugUnitTest
    ./gradlew test
    
### Further improvements

* better pagination strategy on comments screen, reply expand/collapse
* test improvements both for unit and instrumentation tests 
