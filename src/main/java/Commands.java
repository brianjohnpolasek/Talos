import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Commands extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase("!ping")){
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("!pong").queue();
        }
    }
}
