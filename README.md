### Auto kick?
If a player builds a reactor too close to the core. That reactor gets destroyed and the player gets a warning in chat. 
Auto kick is by default disabled but you can enable it in the server console using `antinuke-kick <on/off>`.
If this option is enabled the player gets kicked and a servermessage gets send to all the players on the server.

### nuke spam...
No panic sometimes when a player is to stubborn and keeps spamming nuclear reactors a Nullpoint exception occurs. No panic your server is fine.  
But it is wise to kick/ban that player.

### Building a Jar 

`gradlew jar` / `./gradlew jar`

Output jar should be in `build/libs`.


### Installing

Simply place the output jar from the step above in your server's `config/plugins` directory and restart the server.
List your currently installed plugins by running the `plugins` command.
