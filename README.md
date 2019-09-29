# OnsetJava
[![Maintainability](https://api.codeclimate.com/v1/badges/d783e4c561d7b5596253/maintainability)](https://codeclimate.com/github/OnfireNetwork/OnsetJava/maintainability)

A java api and simple implementation for Onset

## Creating a plugin
To create a plugin you need to implement the "Plugin" interface.
```java
class ExamplePlugin implements Plugin {
  public PluginInfo info(){
    return new PluginInfo("ExamplePlugin", "1.0", "Onfire Network");
  }
}
```
Then you can override the onEnable function to register all your stuff.
```java
class ExamplePlugin implements Plugin {
  public void onEnable(){
    OnsetJava.getServer().registerCommand("echo", (command, player, args) -> {
      player.sendMessage("Echo: " + String.join(" ", args));
    });
    OnsetJava.getServer().getEventBus().listen(PlayerJoinEvent.class, e -> {
      e.getPlayer().sendMessage("Welcome, "+e.getPlayer().getName()+"!");
    });
  }
  public PluginInfo info(){
    return new PluginInfo("ExamplePlugin", "1.0", "Onfire Network");
  }
}
```

## Running the server
Everytime you change or add a plugin you need to reinstall resources first. Just run the jar for that (java -jar OnsetJava-Simple-1.0.jar).
Then add "java" to your package list in the server_config.json and start up the server like you normally do. The server automatically generates the plugin directory "java_plugins" on the first start. You can put all your plugins in there.
