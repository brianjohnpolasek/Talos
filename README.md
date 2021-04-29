# Talos

### Summary
Java bot for Discord built using [JDA][JDA].

### Commands
The commands are currently categorized into sections that are either text, image or audio related commands.

##### Text Commands
* __Echo__ - This will have the bot say whatever message you want, or simply reiterate the last message in the channel. 
    * Usage: _%echo {message}_


* __Help__ - If no arguments are given, this will return a list of all commands, otherwise it will send a message describing the use case of the given command.
    * Usage: _%help {commmand_name}_
    

* __Ping__ - The bot will respond with the round-trip delay of the Discord server.
    * Usage: _%ping_
    

* __Regex__ - By default, only the main text chat will listen for specific regex patterns. This command can be used to enable and disable text channels that respond to regex patterns.
    * Usage:
        * Enable current channel: _%regex enable_
        * Enable all channels: _%regex enable all_
        * Disable current channel: _%regex disable_
        * Disable all channels: _%regex disable all_
        * Add new Regex command: _%regex add {name;regex;response}_
        * Reset status to no status: _%regex reset_


* __Status__ - Allow users to modify the status of the Talos.
    * Usage: _%status {watching/playing/listening} {custom_status}_


* __Wide__ - Adds the specified number of spaces to widen the message.
    * Usage: _%wide {size_or_blank}_
    
##### Audio Commands
* __Join__ - Force Talos to join the default voice channel.
    * Usage: _%join_


* __Leave__ - Force Talos to leave the default voice channel.
    * Usage: _%leave_


* __Pause__ - Will pause the music if any is playing.
    * Usage: _%pause_


* __Play__ - Unpauses if possible, otherwise will play music given a specified URL or search name.
    * Usage: 
        * Unpause if possible: _%play_
        * Add song to queue: _%play {url_or_search_name}_


* __PlayNext__ - Skip a specified number of songs in the queue (or 1 by default).
    * Usage: _%playnext {number_or_blank}_


* __Queue__ - Display all current songs in the queue.
    * Usage: _%queue_


* __Stop__ - Talos will empty the queue and leave the voice channel.
    * Usage: _%stop_


* __Volume__ - Control the volume of Talos.
    * Usage: _%volume_ {value (1-100)}
    
### Adding Commands
Creating new commands can be done by adding a new class under the folder "src/java/talos/bot/commands/modules".

This class should follow the naming convention of "{CommandName}Module" and should implement the ICommands interface.
This will allow users access to override the following methods:
* __handle()__ - Where the logic of the command goes.
* __getName()__ - Specify the name of the command so Talos knows when to execute the command.
* __getHelp()__ - List out a help file in the form of a String for use by the help command.
* __getAliases()__ - Create a list of Strings that are aliases for the command name (if you have any).

Before Talos can use the command, a new instance of the module must be created in the default constructor as a new object variable.


### Dependencies
Along with the following dependencies, a few environment variables must be set in order for Talos to run. These can be specified either through system variables, or a file named ".env" that can be created in the root directory.

An example environment file has been provided in the code above to understand the prerequisite information.

##### 3rd Party Dependencies:
* [Gradle][Gradle] - Build automation for Java.
* [JDA][JDA] - Java Discord API.
* [Shadow Jar][Shadow] - For creating fat jar files through Gradle.
* [dotenv java][DotEnv] - Used for simplifying setting up environment variables.
* [LavaPlayer][LavaPlayer] - Java API for handling audio streaming in Discord.
* [AWS SDK][AWS] - Uses Amazon S3 for persistent runtime storage.
* [Stock API][YahooAPI] - Library for retrieving stock data from Yahoo Finance.
* [emoji-Java][EmojiJava] - Used for using emojis in Java.

<!-- Links -->
[Gradle]: https://gradle.org/releases/
[JDA]: https://github.com/DV8FromTheWorld/JDA
[Shadow]: https://github.com/johnrengelman/shadow
[DotEnv]: https://github.com/cdimascio/dotenv-java
[LavaPlayer]: https://github.com/sedmelluq/lavaplayer
[AWS]: https://aws.amazon.com/getting-started/tools-sdks/
[YahooAPI]: https://financequotes-api.com/
[EmojiJava]: https://github.com/vdurmont/emoji-java