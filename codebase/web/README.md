# Make Apps Simple

---

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A repo for handling website hosting for projects

## Start dev server

```
./gradlew jsBrowserRun --continuous
```

## To Deploy the changes

1. Generate production files

   ```
   ./gradlew jsBrowserProductionWebpack
   ```

2. Copy files from
   ```
   codebase/web/composeApp/build/dist/wasmJs/productionExecutable
   ```
   to
   ```
   /public
   ```
3. Deploy using firebase
   ```
   firebase deploy --only hosting -m "message"
   ```

### Compose Web & Compose HTML References

1. [Initial Setup](https://kotlinlang.org/docs/wasm-get-started.html#what-s-next)
2. [Hot reloading](https://blaszcz.uk/live-reload-with-compose-web/)
3. [Basic Setup](https://youtu.be/F5B-CxJTKlg?si=rBUb2A_vHAmnSSGL)

# Brand Guidelines

- [LinkedIn](https://brand.linkedin.com/downloads)
- [Google Play Store]()
- [GitHub](https://github.com/logos)
- [StackOverflow](https://stackoverflow.design/brand/logo/)
- [Medium]()
- [LeetCode]()
- [Resume]()
- [HashNode](https://hashnode.com/brand-resources)
- [Threads](https://about.meta.com/brand/resources/instagram/threads/)
- [X](https://about.x.com/en/who-we-are/brand-toolkit)
- [LinkTree]()

# License

```
Copyright 2024 Abhimanyu

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
