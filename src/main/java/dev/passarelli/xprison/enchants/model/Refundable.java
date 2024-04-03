package dev.passarelli.xprison.enchants.model;

public interface Refundable {

	boolean isRefundEnabled();

	int getRefundGuiSlot();

	double getRefundPercentage();
}
