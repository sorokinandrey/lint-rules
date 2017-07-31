### README 

This repository has lint rules that are used by upday's day to day development work.



#### MissingNullabilityAnnotation
Checks for missing Android support @NonNull or @Nullable annotations in:
* Class fields and properties.
* Return types in methods.
* Methods parameters



#### Usage

In your build.gradle file
```

 repositories {
    maven { 
        url 'https://dl.bintray.com/upday/upday-maven'
    }
 }
 

 dependencies {
    compile 'de.axelspringer.yana:lint-rules:0.1@aar'
 }
```



#### License

```
/*
 *    Copyright 2017 upday
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
```

