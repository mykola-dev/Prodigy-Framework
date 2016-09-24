# Prodigy Framework
Prodigy is MVVM framework for Android powered by Data Binding Library.

The project is written in Kotlin, but you can use Java in your own apps.

The main concept of the framework is ```Presenter``` (aka ```ViewModel``` ;)) which survives during Activity/Fragment config changes.

## Quick start
Clone the git project or use https://jitpack.io/ to resolve gradle dependency

```kotlin
@Config(component = MainActivity::class, layout = R.layout.activity_main)
class MainPresenter : Presenter<IComponent>() {

    override fun onCreate(bundle: Bundle?) {
        // do init stuff here
    }

    fun onDetailsButtonClick() {
        val presenter = UserDetailsPresenter(user = "Marty McFly")
        navigator.run(presenter)
    }

    fun onShowDialogClick() {
        // presenter with callback
        val presenter = DialogPresenter()
        presenter.setCallback<String> {
            toast("dialog result: $it")
        }
        navigator.run(presenter)
    }

}
```

As you can see, no ```Bundle``` extras, no ```onActivityResult```, no lifecycle pain. Just simple class creation via constructors.


## Development

The project is still in deep developing phase. Do not use it in production!

## Credits
Inspired by https://github.com/jakubkinst/Android-ViewModelBinding

## License
```
Copyright 2016 Deviant Studio

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
