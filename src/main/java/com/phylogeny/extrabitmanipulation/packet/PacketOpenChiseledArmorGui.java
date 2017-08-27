package com.phylogeny.extrabitmanipulation.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.phylogeny.extrabitmanipulation.ExtraBitManipulation;
import com.phylogeny.extrabitmanipulation.reference.GuiIDs;

public class PacketOpenChiseledArmorGui implements IMessage
{
	protected boolean mainArmor;
	
	public PacketOpenChiseledArmorGui() {}
	
	public PacketOpenChiseledArmorGui(boolean mainArmor)
	{
		this.mainArmor = mainArmor;
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeBoolean(mainArmor);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		mainArmor = buffer.readBoolean();
	}
	
	public static class Handler implements IMessageHandler<PacketOpenChiseledArmorGui, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketOpenChiseledArmorGui message, final MessageContext ctx)
		{
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.world;
			mainThread.addScheduledTask(new Runnable()
			{
				@Override
				public void run()
				{
					EntityPlayer player = ctx.getServerHandler().player;
					player.openGui(ExtraBitManipulation.instance, message.mainArmor
							? GuiIDs.CHISELED_ARMOR_MIAN.getID() : GuiIDs.CHISELED_ARMOR_VANITY.getID(), player.world, 0, 0, 0);
				}
			});
			return null;
		}
		
	}
	
}