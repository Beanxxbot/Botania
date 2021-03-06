/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [Apr 11, 2014, 2:53:41 PM (GMT)]
 */
package vazkii.botania.common.item.rod;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.Botania;
import vazkii.botania.common.item.ItemMod;
import vazkii.botania.common.lib.LibItemNames;

public class ItemDirtRod extends ItemMod implements IManaUsingItem {

	static final int COST = 75;

	public ItemDirtRod() {
		this(LibItemNames.DIRT_ROD);
	}

	public ItemDirtRod(String name) {
		super();
		setMaxStackSize(1);
		setUnlocalizedName(name);
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		return place(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10, Blocks.dirt, COST, 0.35F, 0.2F, 0.05F);
	}
	
	public static boolean place(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10, Block block, int cost, float r, float g, float b) {
		if(ManaItemHandler.requestManaExact(par1ItemStack, par2EntityPlayer, cost, false)) {
			ForgeDirection dir = ForgeDirection.getOrientation(par7);
			int entities = par3World.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(par4 + dir.offsetX, par5 + dir.offsetY, par6 + dir.offsetZ, par4 + dir.offsetX + 1, par5 + dir.offsetY + 1, par6 + dir.offsetZ + 1)).size();

			if(entities == 0) {
				ItemStack stackToPlace = new ItemStack(block);
				stackToPlace.tryPlaceItemIntoWorld(par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);

				if(stackToPlace.stackSize == 0) {
					ManaItemHandler.requestManaExact(par1ItemStack, par2EntityPlayer, cost, true);
					for(int i = 0; i < 6; i++)
						Botania.proxy.sparkleFX(par3World, par4 + dir.offsetX + Math.random(), par5 + dir.offsetY + Math.random(), par6 + dir.offsetZ + Math.random(), r, g, b, 1F, 5);
				}
			}
		}

		return true;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public boolean usesMana(ItemStack stack) {
		return true;
	}

}