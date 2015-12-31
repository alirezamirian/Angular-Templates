#Angular Templates

An IntelliJ Plugin (for all IDEs based in IntelliJ IDEA platform) which adds a bunch of live templates for different angular snippets.

Currently templates are limited to module component registration inside IIFEs. But other common things in angular will be added in future. Improvement to currently supported snippets is also in plans (e.g. adding angular docs which is in sync with component while you are adding it).

All templates are placed in 
**Angular Template** group within <kbd>Settings</kbd> > <kbd>Editor</kbd> > 
<kbd>[Live Templates](https://www.jetbrains.com/idea/help/live-templates-2.html)</kbd> and are available under abbreviations
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

Installation
------------

- Using IDE built-in plugin system:
  - <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Browse repositories...</kbd> > <kbd>Search for "Angular templates"</kbd> > <kbd>Install Plugin</kbd>
- Manually:
  - Download the [latest release][latest-release] and install it manually using <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Install plugin from disk...</kbd>
  
Restart IDE.

##Usage examples

####`@directive iife`:
![@directive iife](https://cloud.githubusercontent.com/assets/3150694/11534433/fe77cbc4-9923-11e5-9faf-20de6f6f2042.gif)

#TODO:
- Take controll over order of module names in auto-completion (like the order in `moduleNameSuggestion` macro).
- Add/remove module dependencies array based on module name (add it when defining new module and remove it when pointing to a previously defined module)
- Add more options for directive template
- Add more useful templates
