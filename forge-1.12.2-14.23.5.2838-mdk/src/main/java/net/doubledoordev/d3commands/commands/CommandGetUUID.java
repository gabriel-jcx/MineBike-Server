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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import com.mojang.authlib.GameProfile;
import net.doubledoordev.d3commands.ModConfig;

/**
 * @author Dries007
 */
public class CommandGetUUID extends CommandBase
{
    @Override
    public String getName()
    {
        return "getuuid";
    }

    @Override
    public String getUsage(ICommandSender p_71518_1_)
    {
        return "d3.cmd.uuid.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length == 0)
        {
            sender.sendMessage(new TextComponentTranslation("d3.cmd.uuid.online").setStyle(new Style().setColor(TextFormatting.GOLD)));
            for (GameProfile gp : server.getPlayerList().getOnlinePlayerProfiles())
            {
                sender.sendMessage(new TextComponentString(gp.getName() + " -> " + gp.getId()));
            }
        }
        else
        {
            sender.sendMessage(new TextComponentTranslation("d3.cmd.uuid.all").setStyle(new Style().setColor(TextFormatting.GOLD)));
            for (String name : args)
            {
                EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(name);
                if (player == null)
                    sender.sendMessage(new TextComponentTranslation("d3.cmd.uuid.fail", name).setStyle(new Style().setColor(TextFormatting.RED)));
                else sender.sendMessage(new TextComponentString(player.getDisplayNameString() + " -> " + player.getUniqueID()));
            }
        }
    }

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_)
    {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos)
    {
        return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return ModConfig.getUUIDPermissionLevel;
    }
}
