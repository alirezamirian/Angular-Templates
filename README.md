#Angular Templates

An IntelliJ Plugin (for all IDEs based in IntelliJ IDEA platform) bunch of live templates for different angular snippets.

All templates are placed in 
**Angular Template** group within <kbd>Settings</kbd> > <kbd>Editor</kbd> > 
<kbd>[Live Templates](https://www.jetbrains.com/idea/help/live-templates-2.html)</kbd> and are available under abbreviations
starting with `@` (Abbreviation of **A**ngular **T**emplates). 

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

Installation
------------

- Using IDE built-in plugin system:
  - <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Browse repositories...</kbd> > <kbd>Search for "Angular templates"</kbd> > <kbd>Install Plugin</kbd>
- Manually:
  - Download the [latest release][latest-release] and install it manually using <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Install plugin from disk...</kbd>
  
Restart IDE.

