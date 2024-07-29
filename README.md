# XPreference

![Static Badge](https://img.shields.io/badge/platform-android-green)
![Static Badge](https://img.shields.io/badge/author-marco-blue)
![Static Badge](https://img.shields.io/badge/language-java-red)
![Static Badge](https://img.shields.io/badge/compileSdkVersion-34-yellow)
![Static Badge](https://img.shields.io/badge/minSdkVersion-24-yellow)
![Static Badge](https://img.shields.io/badge/license-Apache--2.0-red)

![](https://s21.ax1x.com/2024/07/29/pkL17Os.png)

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

## Use

✅ 项目中实现 XPreference 卡片效果，只需要「 配置主题 」及「 替换包名 」即可！

1. 配置主题

```xml
<!-- 你的 APP 主题. -->
<style name="Base.Theme.XPreference" parent="Theme.AppCompat.DayNight">
    <!-- 全局替换 preferenceTheme 主题 -->
    <item name="preferenceTheme">@style/Gorgeous.PreferenceThemeOverlay.DayNight</item>
</style>
```

2. 替换 PreferenceFragmentCompat

当你使用 Fragment 时，所有配置的 XML 布局，最终是通过 「PreferenceFragmentCompat」 来加载的，而 XPreference SDK 内部对所有的 「Preference」 项做了卡片效果，所以在代码中需要将: 

"androidx.preference.PreferenceFragmentCompat" 替换为 "gorgeous.preference.PreferenceFragmentCompat"。

```java
// import androidx.preference.PreferenceFragmentCompat;
   import gorgeous.preference.PreferenceFragmentCompat;

public class DisplayFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_display, rootKey);
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
