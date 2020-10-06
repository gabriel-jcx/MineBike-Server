/*
 * Copyright (c) 2014-2016, Dries007 & DoubleDoorDevelopment
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.doubledoordev.d3commands.commands;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import net.doubledoordev.d3commands.ModConfig;

public class CommandHighlight extends CommandBase
{
    @Override
    public String getName()
    {
        return "highlight";
    }

    @Override
    public String getUsage(ICommandSender icommandsender)
    {
        return "d3.cmd.highlight.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length == 0)
        {
            getCommandSenderAsPlayer(sender).setGlowing(getCommandSenderAsPlayer(sender).isGlowing());
            sender.sendMessage(new TextComponentTranslation("d3.cmd.highlight.success", sender.getDisplayName()));
            return;
        }
        if (args.length == 1)
        {
            try
            {
                getCommandSenderAsPlayer(sender).setGlowing(parseBoolean(args[0]));
                sender.sendMessage(new TextComponentTranslation("d3.cmd.highlight.success", sender.getDisplayName()));
            }
            catch (CommandException ignored)
            {
                for (Entity e : getEntityList(server, sender, args[0]))
                {
                    e.setGlowing(!e.isGlowing());
                }
            }
            return;
        }
        boolean b = parseBoolean(args[1]);
        for (Entity e : getEntityList(server, sender, args[0]))
        {
            e.setGlowing(b);
        }
//        target.setGlowing(!target.isGlowing());
//
//        DataParameter<Byte> FLAGS = new DataParameter<>(0, DataSerializers.BYTE);
//        EntityDataManager dm = target.getDataManager();
//        EntityDataManager fake = new EntityDataManager(target);
//        fake.register(FLAGS, (byte) (dm.get(FLAGS) & (1 << 6)));
//
//        EntityPlayerMP senderMP = getCommandSenderAsPlayer(sender);
//
//        SPacketEntityMetadata p = new SPacketEntityMetadata(target.getEntityId(), dm, false);
//        senderMP.connection.sendPacket(p);
//
//        sender.addChatMessage(new TextComponentTranslation("d3.cmd.highlight.success", target.getDisplayName()));
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return ModConfig.highlightPermissionLevel;
    }

    @Override
    public boolean isUsernameIndex(final String[] args, final int userIndex)
    {
        return userIndex == 0;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos)
    {
        if (args.length == 1) return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        if (args.length == 2) return getListOfStringsMatchingLastWord(args, "true", "false");
        return super.getTabCompletions(server, sender, args, pos);
    }
}
