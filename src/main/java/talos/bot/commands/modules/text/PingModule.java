package talos.bot.commands.modules.text;

import net.dv8tion.jda.api.JDA;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class PingModule implements ICommands {

    @Override
    public void handle(CommandsContext commandsContext) {
        JDA jda = commandsContext.getJDA();

        commandsContext.getChannel().sendTyping().queue();
        jda.getRestPing().queue((ping) ->
                commandsContext.getChannel().sendMessageFormat("Ping: %sms", ping).queue());

        commandsContext.getChannel().sendMessageFormat("Local Address: %s", getIPAddress()).queue();
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getHelp() {
        return "Returns the round-time trip from bot to Discord server in milliseconds.";
    }

    public String getIPAddress() {

        String ip = null;

        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }

        return ip;
    }
}
