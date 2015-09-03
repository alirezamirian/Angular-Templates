#Angular Templates

An IntelliJ Plugin (for all IDEs based in IntelliJ IDEA platform) which adds a bunch of live templates for different angular snippets.

Currently templates are limited to module component registration inside IIFEs. But other common things in angular will be added in future. Improvement to currently supported snippets is also in plans (e.g. adding angular docs which is in sync with component while you are adding it).

All templates are placed in 
**Angular Template** group within **Settings** > **Editor** > 
**[Live Templates](https://www.jetbrains.com/idea/help/live-templates-2.html)** and are available under abbreviations
starting with `@` (**@** => **A**ngular **T**emplates). 

Below is the list of currently supported templates:

[comment]: # (templateDocs)

Abbreviation | Description
----------- | ------------
@iife | Add a module component (directive, controller, etc) wrapped in an IIFE
@controller iife | Add a controller to a module wrapped in an IIFE
@directive iife | Add a directive to a module wrapped in an IIFE
@factory iife | Add a factory to a module wrapped in an IIFE
@service iife | Add a service to a module wrapped in an IIFE
@provider iife | Add a provider to a module wrapped in  an IIFE

[comment]: # (/templateDocs)


