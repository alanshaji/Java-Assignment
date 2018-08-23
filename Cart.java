package servlet;

public class Cart {

	String item_id,
		   name;
	int quantity;


	public String getItem_id()
	{
		return this.item_id;
	}
	public int getQuantity()
	{
		return this.quantity;
	}
	public String getName()
	{
		return this.name;
	}


	public void setItem_id(String item_id)
	{
		this.item_id = item_id;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	
}
