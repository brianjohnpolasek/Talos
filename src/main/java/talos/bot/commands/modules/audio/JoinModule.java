package talos.bot.commands.modules.audio;

import net.dv8tion.jda.api.entities.VoiceChannel;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;
import talos.bot.helpers.AudioHelper;

import java.util.List;

@SuppressWarnings("ConstantConditions")
public class JoinModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {

        AudioHelper.getINSTANCE().setCommandsContext(commandsContext);

        List<String> args = commandsContext.getArgs();

        //See if voice channel was specified
        if (!args.isEmpty()) {
            String voiceChannelId = args.get(0);

            if (voiceChannelId.matches("<#[0-9]{18}>|[0-9]{18}")) {
                VoiceChannel voiceChannel = commandsContext.getGuild().getVoiceChannelById(
                        voiceChannelId.replaceAll("[^0-9]", "")
                );
                AudioHelper.getINSTANCE().join(voiceChannel);
                return;
            }
        }

        //Join default voice channel
        AudioHelper.getINSTANCE().join(null);
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getHelp() {
        return "Manually have Talos join a voice channel.";
    }
}
