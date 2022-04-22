## Design Rationale
# Singleton Design Pattern
Global Config files only have and need a single instances each,
this ensures things like API Key access, settings specified in them, and the methods used
will only be accessed from that one instance.
# Facade Design Pattern
Interactions with the system is converged behind CLIs, 
# Observer
Observers notify any changes made to the observed entity to Listeners,
For classes with Singleton Design Pattern Instances, this is especially important as
there will be only one instance and any changes may affect the whole programme.