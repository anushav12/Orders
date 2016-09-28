import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.SerializationUtils;
	/* ****************************************************************************************
	 
	Please remove all bugs from the code below to get the following output:

	<pre>

	*******Order 1*******
	1 book: 13.74
	1 music CD: 16.49
	1 chocolate bar: 0.94
	Sales Tax: 2.84
	Total: 28.33
	*******Order 2*******
	1 imported box of chocolate: 11.5
	1 imported bottle of perfume: 54.62
	Sales Tax: 8.62
	Total: 57.5
	*******Order 3*******
	1 Imported bottle of perfume: 32.19
	1 bottle of perfume: 20.89
	1 packet of headache pills: 10.73
	1 box of imported chocolates: 12.94
	Sales Tax: 8.77
	Total: 67.98
	Sum of orders: 153.81
	 
	</pre>
	 
	******************************************************************************************** */

	/*
	 * represents an item, contains a price and a description.
	 *
	 */
class Item implements Serializable{
	private static final long serialVersionUID = 12345;
	private String description;
	private float price;
	public Item(String description, float price) {
		//Commented by Anusha to avoid initializing description and price with the default values
		//super();
		this.description = description;
		this.price = price;
	}
	
	public String getDescription() {
		return description;
	}
	
	public float getPrice() {
		return price;
	}
}

	/*
	 * represents an order line which contains the @link Item and the quantity.
	 *
	 */
class OrderLine implements Serializable {
	private static final long serialVersionUID = 1234;
	private int quantity;
	private Item item;
		/*
		 * @param item Item of the order
		 * 
		 * @param quantity Quantity of the item
		 */
	public OrderLine(Item item, int quantity) throws Exception {
		if (item == null) {
			System.err.println("ERROR - Item is NULL");
			throw new Exception("Item is NULL");
		}
		assert quantity > 0;
		//added by Anusha to initialize item and quantity with the current class values
		this.item = item;
		this.quantity = quantity;
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getQuantity() {
		return quantity;
	}
}

class Order implements Serializable {
		//Added by Anusha to set default serial version Id to Order class
	private static final long serialVersionUID = 123;
	//Added by Anusha to initialize orderLines with ArrayList
	private List<OrderLine> orderLines=new ArrayList<OrderLine>();
		public void add(OrderLine o){
			if (o == null) {
				System.err.println("ERROR - Order is NULL");
				throw new IllegalArgumentException("Order is NULL");
			}
			orderLines.add(o);
		}
		public int size() {
			return orderLines.size();
		}
		
		public OrderLine get(int i) {
			return orderLines.get(i);
		}
		
		public void clear() {
				this.orderLines.clear();
		}
}

class calculator {
		public static double rounding(double value) {
			return ( (int) (value * 100)) / 100;
	}

		/**
		 * receives a collection of orders. For each order, iterates on the order lines and calculate the total price which
		 * is the item's price * quantity * taxes.
		 * 
		 * For each order, print the total Sales Tax paid and Total price without taxes for this order
		 */
public void calculate(Map<String, Order> o) {

	double grandtotal = 0;

		// Iterate through the orders
		for (Map.Entry<String, Order> entry : o.entrySet())
		{
			System.out.println("*******" + entry.getKey() + "*******");
			//Commented by Anusha to retain grand total of each order
			//grndtotal = 0;
			Order r = entry.getValue();
			double totalTax = 0;
			double total = 0;
			// Iterate through the items in the order
			//changed the condition in for loop so that it access the orderdetails in the hashmap wihtout throwing any error.
			for (int i = 0; i <r.size(); i++) {
				// Calculate the taxes
				double tax = 0;
				if (r.get(i).getItem().getDescription().contains("imported")) {
					tax = rounding(r.get(i).getItem().getPrice() * 0.15); // Extra 5% tax on
					// imported items
					} 
				else{
					tax = rounding(r.get(i).getItem().getPrice() * 0.10);
					}
					// Calculate the total price
				double totalprice = r.get(i).getItem().getPrice() + Math.floor(tax);					
				// Print out the item's total price
				//Added getQuantity() by Anusha to display the quantity of the orders
				System.out.println(r.get(i).getQuantity()+ " " +r.get(i).getItem().getDescription() + ": " + Math.floor(totalprice));
				// Keep a running total
				totalTax += tax;
				total += r.get(i).getItem().getPrice();
			}
			// Print out the total taxes
			System.out.println("Sales Tax: " + Math.floor(totalTax));
			total = total + totalTax;
			// Print out the total amount
			System.out.println("Total: " + Math.floor(total * 100) / 100);
			grandtotal += total;
		}
		System.out.println("Sum of orders: " + Math.floor(grandtotal * 100) / 100);
		}
 }

public class CalculateOrders {

	public static void main(String[] args) throws Exception {

		//Modified HashMap to LinkedHashMap by Anusha to retain the insertion order
		Map<String, Order> o = new LinkedHashMap<String, Order>();

		Order c = new Order();
			
		//Commented by Anusha as grandTotal variable is not used in this class
		//double grandTotal = 0;

		c.add(new OrderLine(new Item("book", (float) 12.49), 1));
		c.add(new OrderLine(new Item("music CD", (float) 14.99), 1));
		c.add(new OrderLine(new Item("chocolate bar", (float) 0.85), 1));
			
		//Added by Anusha to create deep clone of order and adding order to the LinkedHashMap 
		Order cCopy=(Order) SerializationUtils.clone(c);
		o.put("Order 1", cCopy);
			
		// Reuse cart for an other order
		c.clear();
			
		c.add(new OrderLine(new Item("imported box of chocolate", 10), 1));
		c.add(new OrderLine(new Item("imported bottle of perfume", (float) 47.50), 1));
			
		//Added by Anusha to create deep clone of order and adding order to the LinkedHashMap 
		Order cCopy1=(Order) SerializationUtils.clone(c);
		o.put("Order 2", cCopy1);
			
		// Reuse cart for an other order
		c.clear();

		c.add(new OrderLine(new Item("Imported bottle of perfume", (float) 27.99), 1));
		c.add(new OrderLine(new Item("bottle of perfume", (float) 18.99), 1));
		c.add(new OrderLine(new Item("packet of headache pills", (float) 9.75), 1));
		c.add(new OrderLine(new Item("box of importd chocolates", (float) 11.25), 1));
			
		//Added by Anusha to create deep clone of order and adding order to the LinkedHashMap 
		Order cCopy2=(Order) SerializationUtils.clone(c);
		o.put("Order 3", cCopy2);
			
		new calculator().calculate(o);
	}
}