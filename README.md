# Make Apps Simple

A repo for handling website hosting for projects

## Start dev server

```
./gradlew wasmJsBrowserRun --continuous
```

## To Deploy the changes

1. Generate production files

   ```
   ./gradlew wasmJsBrowserDistribution
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

### Compose Web References

1. [Initial Setup](https://kotlinlang.org/docs/wasm-get-started.html#what-s-next)
2. [Hot reloading](https://blaszcz.uk/live-reload-with-compose-web/)
3. [Basic Setup](https://youtu.be/F5B-CxJTKlg?si=rBUb2A_vHAmnSSGL)
