# AwesomeTodoListApp

A simple todo list android app written in Java. It is an offline app which uses sqlite using Room ORM for accessing local data.
RxJava is used for making asynchronous calls and data passing. The complete app is based on MVP architecture to make it customisable and scalable in the long run.

## Getting Started

The easiest way to get the app is downloading the APK. 
https://github.com/sahilpatel14/AwesomeTodoListApp/blob/master/project_assets/todo_list_v1_1.apk

or you can clone the repo and build your own apk.

### Prerequisites
We support all common android versions right from APK level 16 to level 27

```
refer the app/build.gradle file for libraryVerisons
https://github.com/sahilpatel14/AwesomeTodoListApp/blob/master/app/build.gradle
```

## Screenshot

![some text](https://github.com/sahilpatel14/AwesomeTodoListApp/blob/master/project_assets/s1.png)
![some text](https://github.com/sahilpatel14/AwesomeTodoListApp/blob/master/project_assets/s2.png)
![some text](https://github.com/sahilpatel14/AwesomeTodoListApp/blob/master/project_assets/s3.png)
![some text](https://github.com/sahilpatel14/AwesomeTodoListApp/blob/master/project_assets/s4.png)


## The architecture

We have used [MVP](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) architecture for the app. There are many great articles
on MVP and they would do a better job explaining about MVP. 
[Here](https://github.com/googlesamples/android-architecture/tree/todo-mvp) is one of the reference to MVP architecture present in google samples repo.
![architecture diagram](https://raw.githubusercontent.com/wiki/googlesamples/android-architecture/images/mvp.png)

The codebase is sufficiently documented, especially at places where we went rogue with the architecture. The architecture is implemented
similar to [googles mvp sample app](https://github.com/googlesamples/android-architecture/tree/todo-mvp)

## Testing

We have testing the app on APK 27, APK 22 and APK 16 (Emulators).
We haven't written any tests yet. But unit tests and UI testing is in our pipeline for next version of the app.
