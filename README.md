FakeJavaOS
==========

A Java Operating System that runs on top of your currently running operating system, thus being "fake".

External software used in this project
---------------------------------------

Obviously all external software used in this project will be open source and written in Java.

[minimal-json by ralfstx](https://github.com/ralfstx/minimal-json)
- A fast JSON writer and reader.
- Build used in FakeJavaOS is the build in [`bundles/org.eclipsesource.json`](https://github.com/ralfstx/minimal-json/tree/master/bundles/com.eclipsesource.json).
- Used in FakeJavaOS for JSON reading and writing.

[JackBeePee's fork of ClassEnumerator by ddopson with contributions by HerbertV](https://github.com/JackBeePee/java-class-enumerator)
- Allows getting all classes in the same directory via reflection.
- Used in FakeJavaOS for command and argument matching.
