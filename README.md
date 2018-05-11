# jxmlfmt

This is a Java library to format XML using regex. Library is inspired from [xmlfmt](https://github.com/go-xmlfmt/xmlfmt).

Originally developed for [RESTClient](https://github.com/wiztools/rest-client/), where we wanted formatting support without modifying XML content.

## Usage

```java
import org.wiztools.jxmlfmt.XMLFmt;
...
String formatted = XMLFmt.fmt("<root><this><is>a</is><test /><message><org><cn>Some org-or-other</cn><ph>Wouldnt you like to know</ph></org><contact><fn>Pat</fn><ln>Califia</ln></contact></message></this></root>");
```

Result would be:

```xml
<root>
  <this>
    <is>
    a
    </is>
    <test />
    <message>
      <org>
        <cn>
        Some org-or-other
        </cn>
        <ph>
        Wouldnt you like to know
        </ph>
      </org>
      <contact>
        <fn>
        Pat
        </fn>
        <ln>
        Califia
        </ln>
      </contact>
    </message>
  </this>
</root>
```
