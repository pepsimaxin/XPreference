# XPreference

![Static Badge](https://img.shields.io/badge/platform-android-green)
![Static Badge](https://img.shields.io/badge/author-marco-blue)
![Static Badge](https://img.shields.io/badge/language-java-red)
![Static Badge](https://img.shields.io/badge/compileSdkVersion-34-yellow)
![Static Badge](https://img.shields.io/badge/minSdkVersion-24-yellow)
![Static Badge](https://img.shields.io/badge/license-Apache--2.0-red)

![](https://s21.ax1x.com/2024/07/29/pkL1BWD.png)

## Implementation

集成 XPreference 之前，请先将项目更新到 AndroidX 库，如项目使用库较老，请迁移至 [AndroidX](https://developer.android.google.cn/jetpack/androidx/migrate?hl=zh-cn)。

![Static Badge](https://img.shields.io/badge/jitpack-0.0.1-green)

1. 项目目录下<br>
   build.gradle.kts 添加：

```kts
implementation 'com.github.pepsimaxin:XPreference:0.0.1'
```

2. 工程目录下<br>
   settings.gradle.kts 添加：

```kts
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ...
        maven { url = uri("https://jitpack.io") }
    }
}
```

## License

```
Copyright (C) Marco, 2024, XPreference Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
