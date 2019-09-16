# PhotoGalleryApp
COMP 7082 Assignment

| Sprint 1           |  open |
|:-------------:| -----:|
| Task 1 | ![in progress](https://placehold.it/15/FFB226/000000?text=+) `in progress` |
| Task 2      | ![completed](https://placehold.it/15/01EB4C/000000?text=+) `completed` |
| Task 3      | ![completed](https://placehold.it/15/01EB4C/000000?text=+) `completed` |
| Task 4      | ![completed](https://placehold.it/15/01EB4C/000000?text=+) `completed` |
| Task 5      | ![incomplete](https://placehold.it/15/f03c15/000000?text=+) `incomplete` |


| Sprint 2           | pending |
|:-------------:| -----:|
| Task 6 | ![](https://placehold.it/15/E6EBFE/000000?text=+) `pending` |
| Task 7      | ![](https://placehold.it/15/E6EBFE/000000?text=+) `pending` |
| Task 8      | ![](https://placehold.it/15/E6EBFE/000000?text=+) `pending` |
| Task 9      | ![](https://placehold.it/15/E6EBFE/000000?text=+) `pending` |
| Task 10      | ![](https://placehold.it/15/E6EBFE/000000?text=+) `pending` |

## Git
### commit message
Write the summary line and description of what you have done in the imperative mood, that is as if you were commanding someone. Start the line with __"Fix"__, __"Add"__, __"Change"__ \
eg: __ADD - Toolbar and hamburgur menu__
### branching

Every time you start develop a feature (whether is a sidebar or carousel slider), branch off from "Development" branch and start from there. One you are done, issue a pull request to "Development" branch. Also, because its important I am saying this 3 times--\
__Don't touch the master branch__\
__Don't touch the master branch__\
__Don't touch the master branch__


## Imports
Fully qualify imports\
This is bad: __import foo.\*;__  
This is good: __import foo.Bar__;

## Naming convention

### Class files
Class names are written in __UpperCamelCase__.

For classes that extend an Android component, the name of the class should end with the name of the component;
for example: `MainActivity`, `GalleryActivity`.

### Layout files

Layout files should match the name of the Android components that they are intended for but moving the top level component name to the beginning. For example, if we are creating a layout for the `SignInActivity`, the name of the layout file should be `activity_sign_in.xml`.

| Component        | Class Name             | Layout Name                   |
| ---------------- | ---------------------- | ----------------------------- |
| Activity         | `MainActivity`  | `activity_main.xml`   |
| Fragment         | `SignUpFragment`       | `fragment_sign_up.xml`        |


### ID naming

IDs should be prefixed with the name of the element in lowercase underscore. For example:

| Element            | Prefix            |
| -----------------  | ----------------- |
| `TextView`           | `text_`             |
| `ImageView`          | `image_`            |
| `Button`             | `button_`           |
| `Menu`               | `menu_`             |

### selector states

| State	       | Suffix          | Example                     |
|--------------|-----------------|-----------------------------|
| Normal       | `_normal`       | `btn_normal.png`    |
| Pressed      | `_pressed`      | `btn_pressed.png`   |
| Focused      | `_focused`      | `btn__focused.png`   |
| Disabled     | `_disabled`     | `btn_disabled.png`  |
| Selected     | `_selected`     | `btn_selected.png`  |

### Use standard brace style
Braces go on the same line as the code before them.

```java
class MyClass {
    int func() {
        if (something) {
            // ...
        } else if (somethingElse) {
            // ...
        } else {
            // ...
        }
    }
}
```
If the condition and the body fit on one line and that line is shorter than the max line length, then braces are not required, e.g.

```java
if (condition) body();
```

### Test

Every Espresso test class usually targets an Activity, therefore the name should match the name of the targeted Activity followed by `Test`, e.g. `SignInActivityTest`

When using the Espresso API it is a common practice to place chained methods in new lines.

```java
onView(withId(R.id.view))
        .perform(scrollTo())
        .check(matches(isDisplayed()))
```
