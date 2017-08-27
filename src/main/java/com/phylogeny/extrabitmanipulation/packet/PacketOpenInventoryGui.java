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

public class PacketOpenInventoryGui implements IMessage
{
	private boolean openVanilla;
	
	public PacketOpenInventoryGui() {}
	
	public PacketOpenInventoryGui(boolean openVanilla)
	{
		this.openVanilla = openVanilla;
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeBoolean(openVanilla);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		openVanilla = buffer.readBoolean();
	}
	
	public static class Handler implements IMessageHandler<PacketOpenInventoryGui, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketOpenInventoryGui message, final MessageContext ctx)
		{
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.world;
			mainThread.addScheduledTask(new Runnable()
			{
				@Override
				public void run()
				{
					EntityPlayer player = ctx.getServerHandler().player;
					player.openContainer.onContainerClosed(player);
					if (message.openVanilla)
						player.openContainer = player.inventoryContainer;
					else
						player.openGui(ExtraBitManipulation.instance, GuiIDs.CHISELED_ARMOR_SLOTS.getID(), player.world, 0, 0, 0);
				}
			});
			return null;
		}
		
	}
	
}